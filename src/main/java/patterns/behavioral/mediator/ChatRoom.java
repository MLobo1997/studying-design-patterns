package patterns.behavioral.mediator;

import java.util.*;

//In a chat room it would be stupid for each actor to have a reference for the others. Hence the mediator
class Person{
    public String name;
    public ChatRoom room;
    public List<String> chatLog = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public void receive(String sender, String message){
        String s = sender + ": '" + message + "'";
        System.out.printf("[%s's chat session] %s%n", name, message);
    }

    public void say(String message){
        room.broadcast(name, message);
    }

    public void pm(String who, String message){
        room.message(name, who, message);
    }
}

public class ChatRoom {
    private Map<String, Person> people = new HashMap<>();

    public void join(Person p)
    {
        String joinmsg = p.name + " joins the room";
        broadcast("room", joinmsg);
        p.room = this;
        people.put(p.name, p);
    }


    public void broadcast(String source, String message){
        for (Person person : people.values()){
            if (!person.name.equals(source))
                person.receive(source, message);
        }
    }

    public void message(String source, String destination, String message){
        people.get(destination).receive(source, message);
    }
}

class Demo{
    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();

        Person john = new Person("John");
        Person jane = new Person("Jane");

        room.join(john);
        room.join(jane);

        john.say("Hey!");
        jane.say("Hey John!");

        Person simon = new Person("Simon");
        room.join(simon);
        simon.say("Hey!!!");
        jane.pm("Simon", "Hey simon!");
    }
}
