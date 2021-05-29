package patterns.structural.proxy;

//Proxy - when you have a class with some interface and you want to use that interface with other class.
// A proxy serves as an interface to that particular resource.

interface Drivable{
    void drive();
}

class Driver{
    public int age;

    public Driver(int age) {
        this.age = age;
    }
}

class Car implements Drivable{
    protected Driver driver;

    public Car(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void drive() {
        System.out.println("Car being driven!");
    }
}

//We make a proxy for a car which verifies if the driver is an adult
class CarProxy extends Car{
    public CarProxy(Driver driver) {
        super(driver);
    }

    @Override
    public void drive() {
        if (driver.age >= 18) {
            super.drive();
        }
        else {
            System.out.println("Driver too young");
        }
    }
}

class Demo{
    public static void main(String[] args) {
        Car car = new CarProxy(new Driver(12));
        car.drive();
    }
}
