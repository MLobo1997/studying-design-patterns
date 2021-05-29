package solid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// Single responsibility principle avoids god objects that are very difficult to refactor
class Journal {
    private final List<String> entries = new ArrayList<String>();
    private static int count  = 0;

    public void addEntry(String text){
        entries.add("" + (++count) + ": " + text);
    }

    public void removeEntry(int index)
    {
        entries.remove(index);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }

    //public void save(String filename) throws FileNotFoundException // This is violating SRP because Journal is not handling only entries, but also persistence
    //{
        //try (PrintStream out = new PrintStream(filename))
        //{
            //out.println(toString());
        //}
    //}

    //public void load(String filename) {}
    //public void load(URL url){}
}

class Persistence // This is better because you can then change the way all classes are serialized
{
    public void saveToFile(Journal journal, String filename, boolean overwrite) throws FileNotFoundException
    {
        if (overwrite || new File(filename).exists())
        {
            try (PrintStream out = new PrintStream((filename)))
            {
                out.println(journal.toString());
            }
        }
    }
    //public Journal load(String filename){}
    //public Journal load(URL filename){}
}

class Demo0{
   public static void main(String[] args) throws Exception {
       Journal j = new Journal();
       j.addEntry(("Entry 1"));
       j.addEntry(("Another entry"));
       System.out.println(j);

       Persistence p = new Persistence();
       String filename = "journal.txt";
       p.saveToFile(j, filename, true);
   }
}