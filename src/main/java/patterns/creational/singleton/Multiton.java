package patterns.creational.singleton;

import java.util.HashMap;

//Variation of Singleton. Instead of there being just one instance, it allows a restricted finite number of instances.
enum Subsytem{
    PRIMARY,
    AUXILIARY,
    FALLBACK
}

class Printer{
    private static int instanceCount = 0;

    private Printer(){
        instanceCount++;
        System.out.printf("%s instances created so far%n", instanceCount);
    }

    private static HashMap<Subsytem, Printer> instances = new HashMap<>();

    public static Printer get(Subsytem ss){
        if (instances.containsKey(ss))
            return instances.get(ss);

        Printer instance = new Printer();
        instances.put(ss, instance);
        return instance;
    }
}

public class Multiton {
    public static void main(String[] args) {
        Printer main = Printer.get(Subsytem.PRIMARY);
        Printer aux = Printer.get(Subsytem.AUXILIARY);
        aux = Printer.get(Subsytem.AUXILIARY);
    }
}
