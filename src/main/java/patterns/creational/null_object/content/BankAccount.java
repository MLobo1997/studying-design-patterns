package patterns.creational.null_object.content;

import java.lang.reflect.Proxy;

interface Log{
    void info(String msg);
    void warn(String msg);
}

class ConsoleLog implements Log{
    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void warn(String msg) {
        System.out.printf("WARNING: %s%n", msg);
    }
}

final class NullLog implements Log{
    @Override
    public void info(String msg) {

    }

    @Override
    public void warn(String msg) {

    }
}

class BankAccount {
    private Log log;
    private int balance;
    public BankAccount(Log log){
        this.log = log;
    }

    public void deposit(int amount)
    {
        balance+=amount;
        log.info("Deposited " + amount);
    }

}

class Demo{

    @SuppressWarnings("unchecked")
    static <T> T noOp(Class<T> intrfc){
        return (T) Proxy.newProxyInstance(
                intrfc.getClassLoader(),
                new Class<?>[]{intrfc},
                ((proxy, method, args) -> {
                    if (method.getReturnType().equals(Void.TYPE))
                        return null;
                    else
                        return method.getReturnType().getConstructor().newInstance();
                })

        );
    }

    public static void main(String[] args) {
        //ConsoleLog log = new ConsoleLog();
        //Log log = new NullLog();
        //BankAccount ba = new BankAccount(null); //this would crash!!
        Log log = noOp(Log.class);
        BankAccount ba = new BankAccount(log);

        ba.deposit(100);
    }
}
