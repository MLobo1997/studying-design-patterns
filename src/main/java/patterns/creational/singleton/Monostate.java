package patterns.creational.singleton;


// You can easily convert any class to a hackish singleton by making attributes static
// This sucks because it is not an intuitive singleton. You can initialize it several time even though it isnt working
class ChiefExecutiveOfficer {
    private static String name;
    private static int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ChiefExecutiveOfficer.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        ChiefExecutiveOfficer.age = age;
    }

    @Override
    public String toString() {
        return "ChiefExecutiveOfficer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class Demo1{
    public static void main(String[] args) {
        ChiefExecutiveOfficer ceo = new ChiefExecutiveOfficer();
        ceo.setName("Adam Smith");
        ceo.setAge(45);

        ChiefExecutiveOfficer ceo2 = new ChiefExecutiveOfficer();
        ceo.setName("John doe");
        ceo.setAge(71);
        System.out.println(ceo);
        System.out.println(ceo2);
    }
}