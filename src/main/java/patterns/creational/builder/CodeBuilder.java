package patterns.creational.builder;
import java.util.ArrayList;

class FieldCode{
    private String type;
    private String name;

    public FieldCode(String type, String name){
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString(){
        return "public " + this.type + " " + this.name + ";";
    }
}

class ClassCode{
    private String name;
    private ArrayList<FieldCode> fields;

    public ClassCode(String name){
        this.name = name;
        this.fields = new ArrayList<>();
    }

    public void addField(FieldCode field){
        this.fields.add(field);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("public class " + this.name + System.lineSeparator() + "{" + System.lineSeparator());
        for (FieldCode field : fields){
            sb.append(" " + field.toString() + System.lineSeparator());
        }
        sb.append("}");
        return sb.toString();
    }
}

class CodeBuilder
{
    private ClassCode classcode;

    public CodeBuilder(String className)
    {
        this.classcode = new ClassCode(className);
    }

    public CodeBuilder addField(String name, String type)
    {
        this.classcode.addField(new FieldCode(type, name));
        return this;
    }

    @Override
    public String toString()
    {
        return this.classcode.toString();
    }
}


class Demo3{
    public static void main(String[] args) {
        CodeBuilder cb = new CodeBuilder("Person").addField("name", "String").addField("age", "int");
        System.out.println(cb);
    }
}