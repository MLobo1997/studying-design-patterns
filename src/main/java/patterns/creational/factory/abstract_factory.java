package patterns.creational.factory;
import org.javatuples.Pair;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

//Abstract factories are very rarely necessary
// // The idea is that if you have a hierarchy of types, you can have a corresponding hierarchy of factories.
interface HotDrink{
    void consume();
}
class Tea implements HotDrink {
    @Override
    public void consume(){
        System.out.println("This tea is delicious");
    }
}

class Coffee implements HotDrink{
    @Override
    public void consume() {
        System.out.println("This hot is delicious");
    }
}

interface HotDrinkFactory{
    HotDrink prepare(int amount);
}

class TeaFactory implements HotDrinkFactory{
    @Override
    public HotDrink prepare(int amount) {
        System.out.println("Put in tea bag, boil, pour " + amount +
                "ml, add lemon, enjoy!");

        return new Tea();
    }
}

class CoffeeFactory implements HotDrinkFactory{
    @Override
    public HotDrink prepare(int amount) {
        System.out.println("Grind beans, boil, pour " + amount +
                "ml, enjoy!");

        return new Coffee();
    }
}

class HotDrinkMachine{
    private List<Pair<String, HotDrinkFactory>> namedFactories = new ArrayList<>();
    public HotDrinkMachine() throws Exception {
        Set<Class<? extends HotDrinkFactory>> types = new Reflections("").getSubTypesOf(HotDrinkFactory.class);
        for (Class<? extends HotDrinkFactory> type: types){
            namedFactories.add(new Pair<>(
                    type.getSimpleName().replace("Factory", ""),
                    type.getDeclaredConstructor().newInstance()
            ));
        }
    }

    public HotDrink makeDrink() throws Exception{
        System.out.println("Available drinks:");
        for (int index = 0; index < namedFactories.size() ; ++index){
            Pair<String, HotDrinkFactory> item = namedFactories.get(index);
            System.out.println("" + index + ": " + item.getValue0());
        }

        BufferedReader reader = new BufferedReader((new InputStreamReader(System.in)));
        while (true)
        {
            String s;
            int i, amount;
            if ((s = reader.readLine()) != null && (i = Integer.parseInt(s)) >= 0 &&  i < namedFactories.size()){
                System.out.println("Specific amount:");
                s = reader.readLine();
                if (s != null && (amount = Integer.parseInt(s)) > 0){
                    return namedFactories.get(i).getValue1().prepare(amount);
                }
            }
            System.out.println("Incorrect input, try again.");
        }
    }
}

class Demo1{
    public static void main(String[] args) throws Exception {
        HotDrinkMachine machine = new HotDrinkMachine();
        HotDrink drink = machine.makeDrink();
        drink.consume();
    }
}