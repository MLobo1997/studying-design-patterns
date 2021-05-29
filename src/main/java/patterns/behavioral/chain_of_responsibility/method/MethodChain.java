package patterns.behavioral.chain_of_responsibility.method;

//Method chain... First you build the chain and only then you execute it.
class Creature{
    public String name;
    public int attack, defense;

    public Creature(String name, int attack, int defense) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }
}

//These modifiers work like linked lists.
class CreatureModifier{
    protected Creature creature;
    protected CreatureModifier next;

    public CreatureModifier(Creature creature) {
        this.creature = creature;
    }

    public void add(CreatureModifier cm){
        if (next == null){
            next = cm;
        } else {
            next.add(cm);
        }

    }
    public void handle(){
        if (next != null){
            next.handle();
        }
    }
}

class DoubleAttackModifier extends CreatureModifier{
    public DoubleAttackModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.printf("Doubling %s 's attack%n", creature.name);
        creature.attack *= 2;
        super.handle();
    }
}

class IncreaseDefenseModifier extends CreatureModifier{
    public IncreaseDefenseModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.printf("Increasing %s's defense%n", creature.name);
        creature.defense += 3;
        super.handle();
    }
}

class NoBonusesModifier extends CreatureModifier{
    public NoBonusesModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        //We do nothing
        System.out.println("No more bonuses!");
    }
}

class Demo{
    public static void main(String[] args) {
        Creature goblin = new Creature("Goblin", 2, 2);
        System.out.println(goblin);

        CreatureModifier root = new CreatureModifier(goblin);
        System.out.println("Increasing attack!");
        root.add(new DoubleAttackModifier(goblin));

        root.add(new NoBonusesModifier(goblin));

        System.out.println("Increasing defense!");
        root.add(new IncreaseDefenseModifier(goblin));

        root.handle();
        System.out.println(goblin);
    }
}