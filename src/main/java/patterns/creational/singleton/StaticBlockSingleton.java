package patterns.creational.singleton;

import java.io.File;
import java.io.IOException;

// This is useful for cases where the initialization of the singleton might require special touches e.g. error handling
public class StaticBlockSingleton {
    private static StaticBlockSingleton instance;
    private StaticBlockSingleton() throws IOException {
        System.out.println("Singleton is initializing");
        File.createTempFile(".", ".");
    }

    // This code is executed once the class is loaded into memory
    static {
        try {
            instance = new StaticBlockSingleton();
        } catch (IOException e){
            System.out.println("Failed to create singleton");
        }
    }

    public static StaticBlockSingleton getInstance() {
        return instance;
    }
}
