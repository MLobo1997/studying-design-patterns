package patterns.structural.composite;
import java.util.*;
import java.util.function.Consumer;

interface ValueContainer extends Iterable<Integer> {}

class SingleValue implements ValueContainer
{
    public int value;

    // please leave this constructor as-is
    public SingleValue(int value)
    {
        this.value = value;
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        action.accept(value);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return Collections.singleton(value).spliterator();
    }

    @Override
    public Iterator<Integer> iterator() {
        return Collections.singleton(value).iterator();
    }
}

class ManyValues extends ArrayList<Integer> implements ValueContainer
{
}


class MyList extends ArrayList<ValueContainer>
{
    // please leave this constructor as-is
    public MyList(Collection<? extends ValueContainer> c)
    {
        super(c);
    }

    public int sum()
    {
        Integer total = 0;
        for (ValueContainer values : this){
            for (Integer val : values){
                total += val;
            }
        }
        return total;
    }
}
