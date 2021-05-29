package patterns.creational.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// Just a simpler way of implementing a singleton. Cons: has the serialization problem and can't be inherited
// When you serialize and enum you do not serialize the values.
// I dont like it!!
enum EnumBasedSingleton {
    INSTANCE;

    EnumBasedSingleton(){
        value = 42;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

class EnumBasedSingletonDemo
{
    static void saveToFile(EnumBasedSingleton singleton, String filename)
            throws Exception
    {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut))
        {
            out.writeObject(singleton);
        }
    }

    static EnumBasedSingleton readFromFile(String filename)
            throws Exception
    {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn) )
        {
            return (EnumBasedSingleton)in.readObject();
        }
    }

    //
    public static void main(String[] args)
            throws Exception
    {
        String filename = "myfile.bin";

        // run again with next 3 lines commented out. You will see it will load 42, which is not what should have been serialized

        //EnumBasedSingleton singleton = EnumBasedSingleton.INSTANCE;
        //singleton.setValue(111);
        //saveToFile(singleton, filename);

        EnumBasedSingleton singleton2 = readFromFile(filename);
        System.out.println(singleton2.getValue());
    }
}
