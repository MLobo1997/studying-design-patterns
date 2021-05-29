package patterns.creational.builder;

// Builder. Complex constructors with too many arguments are error prone. We must not leave it all to the constructor
public class StringBuilderDemo {
    public static void main(String[] args) {
        String hello = "Hello";
        System.out.println("<p>" + hello + "</p>");

        String [] words = {"hello", "world"};
        //System.out.println( THIS WOULD GET MESSY. THAT'S WHY THERE IS THE STRINGBUILDER
        //"<ul>\n" + "<li>" + words[0]
        //);
        StringBuilder sb = new StringBuilder(); //the construction does not happen in a single constructor but in several pieces
        sb.append("<ul>\n");
        for (String word : words){
            sb.append(String.format("  <li>%s</i>\n", word));
        }
        sb.append("</ul>");
        System.out.println(sb);
    }
}
