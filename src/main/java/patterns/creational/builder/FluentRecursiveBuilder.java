package patterns.creational.builder;

class Person{
    public String name;
    public String position;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

class PersonBuilder<A extends PersonBuilder<A>>{ //This is a recursive generic definition
    protected Person person = new Person();
    public A withName(String name){
        person.name = name;
        return this.self();
    }

    public Person build(){
        return person;
    }

    protected A self() {
        A a = (A) this;
        return a;
    }
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder>{
    public EmployeeBuilder worksAt(String position){ ///You cannot call this fluently below!!
        person.position = position;
        return this;
    }
}

class Demo01 {
    public static void main(String[] args) {
        EmployeeBuilder pb = new EmployeeBuilder();
        Person dmitri = pb.withName("Dmitri").worksAt("Developer").build();
        System.out.println(dmitri);
    }


}
