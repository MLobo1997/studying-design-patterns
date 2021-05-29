package patterns.creational.singleton;

//A singleton that is thread safe (will not be initialized twice)
public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
        System.out.println("Initializing");
    }

    // Good approach, but with performance limitations
    //public static synchronized LazySingleton getInstance() {
        //if (instance != null){
            //instance = new LazySingleton();
        //}
        //return instance;
    //}

    // double-checked locking - outdated approach!!! but much faster.
    public static LazySingleton getInstance() {
        if (instance != null){
            synchronized (LazySingleton.class){
                if (instance == null){
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
