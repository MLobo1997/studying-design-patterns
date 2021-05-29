package patterns.creational.prototype;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

//What if you have a huge type hierarchy? Do you create copies for each? No! You use copy serialization.
class Foo implements Serializable {
    public int stuff;
    public String whatever;

    public Foo(int stuff, String whatever) {
        this.stuff = stuff;
        this.whatever = whatever;
    }

    @Override
    public String toString() {
        return "foo{" +
                "stuff=" + stuff +
                ", whatever='" + whatever + '\'' +
                '}';
    }
}


class Demo1{
    public static void main(String[] args) {
        Foo foo = new Foo(42, "life");
        Foo foo2 = SerializationUtils.roundtrip(foo);

        foo2.whatever = "xyz";

        System.out.println(foo);
        System.out.println(foo2);
    }
}