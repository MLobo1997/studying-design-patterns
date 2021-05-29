package patterns.behavioral.mediator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Participant
{
    public int value = 0;
    private Mediator mediator;
    public Participant(Mediator mediator)
    {
        this.mediator = mediator;
        mediator.addParticipant(this);
    }

    public void say(int n)
    {
        mediator.broadcast(this, n);
    }

    public void sum(int n){
        value += n;
    }
}

class Mediator
{
    private final List<Participant> participants = new ArrayList<>();

    public void addParticipant(Participant participant){
        participants.add(participant);
    }

    public void broadcast(Participant sender, int n){
        participants.stream().filter(p -> p != sender).forEach(p -> p.sum(n));
    }
}

class Evaluate
{
    @Test
    public void test()
    {
        Mediator mediator = new Mediator();
        Participant p1 = new Participant(mediator);
        Participant p2 = new Participant(mediator);

        assertEquals(0, p1.value);
        assertEquals(0, p2.value);

        p1.say(2);

        assertEquals(0, p1.value);
        assertEquals(2, p2.value);

        p2.say(4);

        assertEquals(4, p1.value);
        assertEquals(2, p2.value);

    }
}
