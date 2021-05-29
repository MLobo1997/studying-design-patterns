package patterns.creational.singleton;

import com.google.common.collect.Iterables;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// INSTEAD OF DIRECTLY USING A SINGLETON; YOU SHOULD MAKE IT DEPEND ON A INTERFACE SO THAT YOU CAN TEST IT
interface Database{
    int getPopulation(String name);
}

class SingletonDatabase implements Database{
    private Dictionary<String, Integer> capitals = new Hashtable<>();

    private static int instanceCount = 0;

    public static int getInstanceCount() {
        return instanceCount;
    }

    private SingletonDatabase() {
        instanceCount++;
        System.out.println("Initializing database");

        try{
            Path fullPath = Paths.get("capitals.txt").toAbsolutePath();
            List<String> lines = Files.readAllLines(fullPath);
            Iterables.partition(lines, 2).forEach(kv -> capitals.put(kv.get(0).trim(), Integer.parseInt(kv.get(1))));


        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static final SingletonDatabase INSTANCE = new SingletonDatabase();

    public static SingletonDatabase getInstance(){
        return INSTANCE;
    }

    public int getPopulation(String name){
        return capitals.get(name);
    }
}

//BAD
class SingletonRecordFinder{
    public  int getTotalPopulation(List <String> names){
        int result = 0;
        for (String name : names)
            result += SingletonDatabase.getInstance().getPopulation(name);
        return result;
    }
}

//GOOOOOD but you need to create the database interface
class ConfigurableRecordFinder{
    private  Database database;

    public ConfigurableRecordFinder(Database database) {
        this.database = database;
    }

    public  int getTotalPopulation(List <String> names){
        int result = 0;
        for (String name : names)
            result += database.getPopulation(name);
        return result;
    }

}

class DummyDatabase implements Database{
    private Dictionary<String, Integer> data = new Hashtable<>();

    public DummyDatabase() {
        data.put("alpha", 1);
        data.put("b", 2);
        data.put("g", 3);
    }

    @Override
    public int getPopulation(String name) {
        return data.get(name);
    }
}

class Tests{
    //The problem is: how do you make a unit test? You are making integration test by accessing the DB
    @Test
    public void singleTotalPopulationTest(){
        SingletonRecordFinder rf = new SingletonRecordFinder();
        List<String> names = List.of("Lisbon", "New York");
        int tp = rf.getTotalPopulation(names);
        assertEquals(123124 + 6881254, tp);
    }

    //This is a unit test
    @Test
    public void dependentPopulationTest(){
       DummyDatabase db = new DummyDatabase();
       ConfigurableRecordFinder rf = new ConfigurableRecordFinder(db);
       assertEquals(4, rf.getTotalPopulation(List.of("alpha", "g")));
    }

}