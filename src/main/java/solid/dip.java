package solid;
import org.javatuples.Triplet;

// DIP Dependency inversion principle. Split into two parts
// High level modules should not depend on low-level modules - both should depend on abstractions
// Use abstractions instead! But abstractions should not depend on details - details should depend on abstractions

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum Relationship{
    PARENT,
    CHILD,
    SIBLING
}
class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }
}


interface RelationshipBrowser{
    List<Person> findAllChildrenOf(String name);
}

class Relationships implements RelationshipBrowser{ // low level. Because it is just for storage, no business logic
    // we are exposing this too much through the getter. If we happen to want to change how it works it will be too much trouble later on
    private final List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public void addParentAndChild(Person parent, Person child){
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    // This is good! The search is happening in the low level module.
    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations.stream()
                .filter(rel -> rel.getValue0().name.equals(name) && rel.getValue1().equals(Relationship.PARENT))
                .map(Triplet::getValue2)
                .collect(Collectors.toList());
    }
}

class Research{ // high level. Has business logic, makes operations on the lower level.
    /*
    // We have a dependency here! not good
    public Research(Relationships relationships){
        // BAD! we need to go to much in detail on the low level class implementation.
        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
        relations.stream()
            .filter(rel -> rel.getValue0().name.equals("John") && rel.getValue1().equals(Relationship.PARENT))
            .forEach(ch -> System.out.println("John has a child called " + ch.getValue2().name));
    }
    */

    public Research(RelationshipBrowser browser){
        List<Person> children = browser.findAllChildrenOf("John");
        for (Person child : children){
            System.out.println("John has a child called " + child.name);
        }
    }
}

class Demo03{

    public static void main(String[] args){
        Person parent = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(parent, child1);
        relationships.addParentAndChild(parent, child2);

        new Research((relationships));
    }

}