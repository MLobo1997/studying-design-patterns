package patterns.creational.builder;

class Person1 {
    public String streetAddress, postcode, city;
    public String companyName, position;
    public int annualincome;

    @Override
    public String toString() {
        return "Person1{" +
                "streetAddress=" + streetAddress +
                ", postcode=" + postcode +
                ", city=" + city +
                ", companyName='" + companyName + '\'' +
                ", positions='" + position + '\'' +
                ", annualincome=" + annualincome +
                '}';
    }
}

//builder facade
class Person1Builder{
    protected Person1 person = new Person1();

    public PersonAddressBuilder lives(){
        return new PersonAddressBuilder(person);
    }

    public PersonJobBuilder works() {
        return new PersonJobBuilder(person);
    }

    public Person1 build(){
        return person;
    }
}

class PersonAddressBuilder extends Person1Builder{
    public PersonAddressBuilder(Person1 person){
        this.person = person;
    }

    public PersonAddressBuilder at(String streetAddress){
        person.streetAddress = streetAddress;
        return this;
    }

    public PersonAddressBuilder withPostCode(String postCode){
        person.postcode = postCode;
        return this;
    }

    public PersonAddressBuilder in(String city){
        person.city = city;
        return this;
    }
}

class PersonJobBuilder extends Person1Builder{
    public PersonJobBuilder(Person1 person){
        this.person = person;
    }

    public PersonJobBuilder atCompany(String companyName){
        person.companyName = companyName;
        return this;
    }

    public PersonJobBuilder asA(String position){
        person.position = position;
        return this;
    }

    public PersonJobBuilder earning(int annualincome){
        person.annualincome = annualincome;
        return this;
    }
}

class Demo2{
    public static void main(String[] args) {
        Person1Builder pb = new Person1Builder();
        Person1 person = pb.lives().at("123 London road").in("London").withPostCode("SW12BC").works().atCompany("Fabrikam").asA("Engineer").earning(1230000).build();
        System.out.println(person);
    }
}
