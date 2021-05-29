package patterns.behavioral.iterator.content;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

class SimpleCreature{
    private int strength, agility, intelligence;

    //If we added an additional property we would have to change all of these:
    public int max(){
        return Math.max(strength, Math.max(intelligence, agility));
    }

    public int sum(){
        return strength + intelligence + agility;
    }

    public double average(){
        return sum() / 3.0;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
}

class Creature implements Iterable<Integer>{
    private int [] stats = new int[3];
    

    public int getStrength(){
        return stats[0];
    }
    public void setStrength(int value){
        stats[0] = value;
    }

    public int getAgility(){
        return stats[1];
    }
    public void setAgility(int value){
        stats[1] = value;
    }

    public int getIntelligence(){
        return stats[2];
    }
    public void setIntelligence(int value){
        stats[2] = value;
    }



    public int sum(){
        return Arrays.stream(stats).sum();
    }

    public int max(){
        return IntStream.of(stats).max().getAsInt();
    }

    public double average(){
        return IntStream.of(stats).average().getAsDouble();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        for (int x : stats)
            action.accept(x);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return IntStream.of(stats).spliterator();
    }

    @Override
    public Iterator<Integer> iterator() {
        return IntStream.of(stats).iterator();
    }
}

class Demo1{
    public static void main(String[] args) {
        Creature creature = new Creature();
        creature.setStrength(12);
        creature.setAgility(10);
        creature.setIntelligence(23);
        System.out.printf("Creature max: %s; total: %s; avg: %s%n", creature.max(), creature.sum(), creature.average());
    }
}