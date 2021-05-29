package patterns.creational.factory;

class Person
{
    public int id;
    public String name;

    public Person(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
}

class PersonFactory
{
    private int current_id = 0;
    public Person createPerson(String name)
    {
        return new Person(this.current_id++, name);
    }
}