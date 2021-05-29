package patterns.creational.singleton;

import java.io.*;

public class BasicSingleton implements Serializable{
    //Private constructor!
    private BasicSingleton()
    {

    }

    public static final BasicSingleton INSTANCE = new BasicSingleton();

    public static BasicSingleton getInstance(){
        return INSTANCE;
    }

    private int value = 0;

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BasicSingleton{" +
                "value=" + value +
                '}';
    }

    //This fixes the serialization issue
    protected Object readResolve(){
        return INSTANCE;
    }
}

class Demo{

    static void saveToFile(BasicSingleton singleton, String filename) throws Exception{
        try (
                FileOutputStream fileout = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(fileout)
            )
        {
            out.writeObject(singleton);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    static BasicSingleton readFromFile(String filename) throws Exception{
        try(
                FileInputStream fileIn = new FileInputStream(filename);
                ObjectInputStream in  = new ObjectInputStream(fileIn)
        ){
            return (BasicSingleton) in.readObject();
        }

    }

    public static void main(String[] args) throws Exception {
        BasicSingleton singleton = BasicSingleton.getInstance();
        singleton.setValue(123);
        System.out.println(singleton);
        singleton = BasicSingleton.getInstance();
        System.out.println(singleton);

        // 1. With this implementation, you can use reflection to access the constructor.
        // 2. Serialization - JVM does not care if the constructor is private.
        singleton.setValue(111);
        String filename = "singleton.bin";
        saveToFile(singleton, filename);

        singleton.setValue(222);

        BasicSingleton singleton2 = readFromFile(filename);
        //You can check that it returns false (before readResolve was implemented). This should be impossible to happen with a singleton
        System.out.println(singleton == singleton2);
        System.out.println(singleton);
        System.out.println(singleton2);
    }
}