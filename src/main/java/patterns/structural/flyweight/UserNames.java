package patterns.structural.flyweight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

//Flyweigth - technique to avoid redundant use of memory space.
class User {
    private String fullName;

    public User(String fullName) {
        this.fullName = fullName;
    }
}

class User2{
    static List<String> strings = new ArrayList<>();
    private int [] names;

    public User2(String fullname){
        Function<String, Integer> getOrAdd = (String s) -> {
            int idx= strings.indexOf(s);
            if (idx != -1) return idx;
            else {
                strings.add(s);
                return strings.size() - 1;
            }
        };
        names = Arrays.stream(fullname.split(" ")).mapToInt(getOrAdd::apply).toArray();
    }
}

class Demo{
    public static void main(String[] args) {
        User user1 = new User("John Doe"); //These two share the same surname
        User user2 = new User("Jane Doe");
    }
}