package patterns.structural.composite;

import java.util.*;
import java.util.function.Consumer;

interface SomeNeurons extends Iterable<Neuron>{
    default void connectTo(SomeNeurons other){
        if (this == other){
            return;
        }
        for (Neuron from : this){
            for (Neuron to : other){
                from.out.add(to);
                to.in.add(from);
            }
        }
    }
}

class Neuron implements SomeNeurons{
    public ArrayList<SomeNeurons> in = new ArrayList<>();
    public ArrayList<SomeNeurons> out = new ArrayList<>();

    @Override
    public Iterator<Neuron> iterator() {
        return Collections.singleton(this).iterator();
    }

    @Override
    public void forEach(Consumer<? super Neuron> action) {
        action.accept(this);
    }

    @Override
    public Spliterator<Neuron> spliterator() {
        return Collections.singleton(this).spliterator();
    }
//public void connectTo(Neuron other){
        //out.add(other);
        //other.in.add(this);
    //}
}

class NeuronLayer extends ArrayList<Neuron> implements SomeNeurons{
    //Since ArrayList already implements the Iterable methods we do not need to override them
}

class Demo2{
    public static void main(String[] args) {
        Neuron neuron = new Neuron();
        Neuron neuron2 = new Neuron();
        NeuronLayer layer = new NeuronLayer();
        NeuronLayer layer2 = new NeuronLayer();

        neuron.connectTo(neuron2);
        neuron.connectTo(layer);
        layer.connectTo(neuron);
        layer.connectTo(layer2);
    }
}