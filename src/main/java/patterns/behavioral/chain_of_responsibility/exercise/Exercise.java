package patterns.behavioral.chain_of_responsibility.exercise;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

//REMOVAL NOT IMPLEMENTED BUT IT SUFFICES
abstract class Creature
{
    protected Game game;
    protected int attack, defense;

    public int getAttack()
    {
        Query query = new Query(this, Statistic.ATTACK, attack);
        game.queries.fire(query);
        return query.result;
    }

    public int getDefense()
    {
        Query query = new Query(this, Statistic.DEFENSE, defense);
        game.queries.fire(query);
        return query.result;
    }
}

class Goblin extends Creature
{
    public Goblin(Game game) {
        this.game = game;
        this.attack = 1;
        this.defense = 1;
        new ExtraDefenseModifier(game, this);
    }
}

class GoblinKing extends Goblin
{
    public GoblinKing(Game game)
    {
        super(game);
        this.attack = 3;
        this.defense = 3;
        new ExtraAttackModifier(game, this);
    }
}

enum Statistic
{
    ATTACK, DEFENSE
}

class Game
{
    public List<Creature> creatures = new ArrayList<>();
    public Event<Query> queries = new Event();
}

class Event<Args>{
    private int index = 0;
    private Map<Integer, Consumer<Args>> handlers = new HashMap<>();

    public void subscribe(Consumer<Args> handler){
        handlers.put(index, handler);
        index++;
    }

    public void unsubscribe(int id){
        handlers.remove(id);
    }
    public void fire(Args arg){
        for (Consumer<Args> handler : handlers.values()){
            handler.accept(arg);
        }
    }
}

class Query{
    public Creature creature;
    public Statistic type;
    public int result;

    public Query(Creature creature, Statistic type, int result) {
        this.creature = creature;
        this.type = type;
        this.result = result;
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

class ExtraDefenseModifier extends CreatureModifier{
    public ExtraDefenseModifier(Game game, Creature creature) {
        super(game, creature);
        game.queries.subscribe(
                query -> {
                    if (query.creature != creature && query.type.equals(Statistic.DEFENSE))
                        query.result += 1;
                }
        );
    }
}

class ExtraAttackModifier extends CreatureModifier{
    public ExtraAttackModifier(Game game, Creature creature) {
        super(game, creature);
        game.queries.subscribe(
                query -> {
                    if (query.creature != creature && query.type.equals(Statistic.ATTACK))
                        query.result += 1;
                }
        );
    }
}


