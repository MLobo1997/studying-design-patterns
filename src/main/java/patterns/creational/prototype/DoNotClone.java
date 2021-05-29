package patterns.creational.prototype;

import java.util.Arrays;

//THIS IS NOT A RECOMMENDABLE APPROACH! Because Cloneable does not state exactly how the cloning is happening, i.e.,
// if it is a deep copy etc etc.
class Address implements Cloneable{
    public String streetName;
    public int houseNumber;

    public Address(String streetName, int houseNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }

    @Override
    public Address clone() {
        return new Address(this.streetName, this.houseNumber);
    }
}

class Person implements Cloneable{
    public String[] names;
    public Address address;

    public Person(String[] names, Address address) {
        this.names = names;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "names=" + Arrays.toString(names) +
                ", address=" + address +
                '}';
    }

    @Override
    public Person clone() {
        return new Person(this.names.clone(), this.address.clone());
    }
}

class Demo{
    public static void main(String[] args) {
        Person john = new Person(new String[]{"John", "Smith"}, new Address("London road", 123));

        //Person jane = john; // If we just do it like this we are copying only the reference
        Person jane = john.clone();
        jane.names[0] = "Jane";
        jane.address.houseNumber = 124;

        System.out.println(john);
        System.out.println(jane);
    }
}
