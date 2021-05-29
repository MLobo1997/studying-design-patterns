package patterns.structural.proxy;


//This is not exactly a proxy DP... this is on the attribute level

/*
//imagine you want to log everytime you set the Ability of creature
class Creature{
    private int agility;

    //but here it wouldnt be logged... :-1:
    public Creature(int agility) {
        this.agility = agility;
    }

    public int getAgility() {
        return agility;
    }

    //You could use the setter!
    public void setAgility(int agility) {
        this.agility = agility;
    }
}

*/

class Property<T>{
    private T value;

    public Property(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        //YOU PERFORM ALL THE LOGGING HERE
        System.out.printf("Changing to %s%n", value);
        this.value = value;
    }
}

class Creature{
    private Property<Integer> agility = new Property<>(10);

    public void setAgility(int value) {
        agility.setValue(value);
    }

    public int getAgility(){
        return agility.getValue();
    }
}

class Demo1{
    public static void main(String[] args) {
        Creature creature = new Creature();
        creature.setAgility(15);
    }
}

