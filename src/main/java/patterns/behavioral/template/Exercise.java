package patterns.behavioral.template;

class Creature
{
    public int attack, health;
    public final int defaultHealth;

    public Creature(int attack, int health)
    {
        this.attack = attack;
        this.health = health;
        this.defaultHealth = health;
    }

}

abstract class CardGame
{
    public Creature [] creatures;

    public CardGame(Creature[] creatures)
    {
        this.creatures = creatures;
    }

    // returns -1 if no clear winner (both alive or both dead)
    public int combat(int creature1, int creature2)
    {
        Creature first = creatures[creature1];
        Creature second = creatures[creature2];
        hit(first, second);
        hit(second, first);

        boolean alive1, alive2;
        alive1 = alive2 = true;
        if (first.health <= 0){
            alive1 = false;
        }
        if (second.health <= 0){
            alive2 = false;
        }
        if ((alive1 && alive2) || (!alive1 && !alive2)){
            return -1;
        } else if (!alive1) {
            return creature2;
        } else {
            return creature1;
        }
    }

    // attacker hits other creature
    protected abstract void hit(Creature attacker, Creature other);
}

class TemporaryCardDamageGame extends CardGame
{
    public TemporaryCardDamageGame(Creature[] creatures) {
        super(creatures);
    }

    @Override
    protected void hit(Creature attacker, Creature other) {
        other.health -= attacker.attack;
    }
}

class PermanentCardDamageGame extends CardGame
{
    public PermanentCardDamageGame(Creature[] creatures) {
        super(creatures);
        restoreCreatures();
    }

    private void restoreCreatures(){
        for (Creature creature : creatures){
            creature.health = creature.defaultHealth;
        }
    }

    @Override
    protected void hit(Creature attacker, Creature other) {
        other.health -= attacker.attack;
    }
}

