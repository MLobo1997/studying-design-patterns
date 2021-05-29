package patterns.behavioral.chain_of_responsibility.broker;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

//CQS -- Command and Query separation... Things in the chain that set values and that get values should be completely separated. Basically we don't modify the actual object, but we modify the getter no manipulate the query.

//In this example there is:
//chain of responsibility + observer + mediator + memento
//Amazing!
class Event<Args> {
    private int index = 0;
    private final Map<Integer, Consumer<Args>> handlers = new HashMap<>();

    public int subscribe(Consumer<Args> handler){
        int i = index;
        handlers.put(index++, handler);
        return i;
    }

    public void unsubscribe(int key){
        handlers.remove(key);
    }

    public void fire(Args args){
        for (Consumer<Args> handler : handlers.values()){
            handler.accept(args);
        }
    }
}

class Query{
    public String creatureName;
    enum Argument{
        ATTACK, DEFENSE
    }

    public Argument argument;
    public int result;

    public Query(String creatureName, Argument argument, int result) {
        this.creatureName = creatureName;
        this.argument = argument;
        this.result = result;
    }
}

class Game
{
    public Event<Query> queries = new Event<>();
}

class Creature{
    private Game game;
    public String name;
    private int baseAttack, baseDefense;

    public Creature(Game game, String name, int baseAttack, int baseDefense) {
        this.game = game;
        this.name = name;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
    }

    public int getAttack() {
        Query query = new Query(name, Query.Argument.ATTACK, baseAttack);
        game.queries.fire(query);
        return query.result;
    }

    public int getDefense() {
        Query query = new Query(name, Query.Argument.DEFENSE, baseDefense);
        game.queries.fire(query);
        return query.result;
    }

    @Override
    public String toString() {
        return "Creature{" +
                ", name='" + name + '\'' +
                ", baseAttack=" + getAttack() +
                ", baseDefense=" + getDefense() +
                '}';
    }
}

class CreatureModifier{
    protected Game game;
    protected Creature creature;

    public CreatureModifier(Game game, Creature creature) {
        this.game = game;
        this.creature = creature;
    }
}

class DoubleAttackModifier extends CreatureModifier implements AutoCloseable{
    private final int token;
    public DoubleAttackModifier(Game game, Creature creature) {
        super(game, creature);

        token = game.queries.subscribe(query -> {
            if (query.creatureName.equals(creature.name) && query.argument == Query.Argument.ATTACK){
                query.result *= 2;
            }
        });
    }

    @Override
    public void close() /*throws Exception*/ {
        game.queries.unsubscribe(token);
    }
}

class IncreaseDefenseModifier extends CreatureModifier{
    public IncreaseDefenseModifier(Game game, Creature creature) {
        super(game, creature);
        game.queries.subscribe(query -> {
            if (query.creatureName.equals(creature.name) && query.argument == Query.Argument.DEFENSE){
                query.result += 3;
            }
        });
    }
}

class Demo{
    public static void main(String[] args) {
        Game game = new Game();
        Creature strong_goblin = new Creature(game, "Strong goblin", 2,2);
        System.out.println(strong_goblin);

        IncreaseDefenseModifier icm = new IncreaseDefenseModifier(game, strong_goblin);
        DoubleAttackModifier dam = new DoubleAttackModifier(game, strong_goblin);
        try (dam){
            System.out.println(strong_goblin);
        }
        System.out.println(strong_goblin);
    }
}