package patterns.structural.proxy.exercise;

class Person
{
    private int age;

    public Person(int age)
    {
        this.age = age;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String drink() { return "drinking"; }
    public String drive() { return "driving"; }
    public String drinkAndDrive() { return "driving while drunk"; }
}

class ResponsiblePerson extends Person
{
    public ResponsiblePerson(Person person)
    {
        super(person.getAge());
    }

    @Override
    public String drink() {
        if (getAge() < 18){
            return "too young";
        }
        else {
            return super.drink();
        }
    }

    @Override
    public String drive() {
        if (getAge() < 16){
            return "too young";
        }
        else {
            return super.drive();
        }
    }

    @Override
    public String drinkAndDrive() {
        return "dead";
    }
}

