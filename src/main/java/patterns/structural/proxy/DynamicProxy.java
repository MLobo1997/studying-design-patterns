package patterns.structural.proxy;

//Very used, but computationally expensive... Used for logging.

//Idea of building a proxy around an object to capture and log everything.

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

interface Human
{
    void walk();
    void talk();

}

class Person implements Human{
    @Override
    public void walk() {
        System.out.println("I am walking");
    }

    @Override
    public void talk() {
        System.out.println("I am talking");
    }
}

class LoggingHandler implements InvocationHandler{
    private final Object target;
    private Map<String, Integer> calls = new HashMap<>();

    public LoggingHandler(Object target) {
        this.target = target;
    }

    //THIS IS  INTERCEPTING EVERY SINGLE CALL TO THE OBJECT USED
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();

        //HERE YOU INTERCEPT THE toString
        if (name.contains("toString")){
            return calls.toString();
        }

        calls.merge(name, 1, Integer::sum);
        //HERE THE METHOD IS FINALLY INVOKED
        return method.invoke(target, args);
    }
}

class Demo2{
    @SuppressWarnings("unchecked")
    public static <T> T withLogging(T target, Class<T> intrfc){
        return (T) Proxy.newProxyInstance(
                intrfc.getClassLoader(),
                new Class<?>[]{intrfc},
                new LoggingHandler(target)
        );
    }
    public static void main(String[] args) {
        Person person = new Person();
        Human logged = withLogging(person, Human.class);
        logged.talk();
        logged.walk();
        logged.talk();
        System.out.println(logged);
    }
}
