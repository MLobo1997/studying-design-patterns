package patterns.creational.singleton;

//Address the same problem as the LazySingleton but in another way
public class InnerStaticSingleton {


    // This avoid the need for thread safety.
    private static class Impl{
        private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
    }

    public InnerStaticSingleton getInstance(){
        return Impl.INSTANCE;
    }
}
