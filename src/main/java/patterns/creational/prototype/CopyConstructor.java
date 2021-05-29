package patterns.creational.prototype;

//This is the right way to do it.
class Address1 {
    public String streetAddress, city, country;

    public Address1(String streetAddress, String city, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
    }

    public Address1(Address1 other){
        this(other.streetAddress, other.city, other.country);
    }

    @Override
    public String toString() {
        return "Address1{" +
                "streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

class Employee
{
    public String name;
    public Address1 address;

    public Employee(String name, Address1 address) {
        this.name = name;
        this.address = address;
    }

    public Employee(Employee other){
        name = other.name;
        address = new Address1(other.address);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}

class CopyConstructorDemo{
    public static void main(String[] args) {
        Employee john = new Employee("John", new Address1("123 London Road", "London", "UK"));

        Employee chris = new Employee(john);
        chris.name = "Chris";
        System.out.println(john);
        System.out.println(chris);
    }
}
