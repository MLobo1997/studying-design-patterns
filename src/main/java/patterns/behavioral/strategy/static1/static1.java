package patterns.behavioral.strategy.static1;

// Also known as policy

import java.util.List;
import java.util.function.Supplier;

enum OutputFormat{
    MARKDOWN, HTML
}

interface ListStrategy{
    default void start(StringBuilder sb){}

    void addListItem(StringBuilder sb, String item);

    default void end(StringBuilder sb){}
}

class MarkDownListStrategy implements ListStrategy {
    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append(" * ")
                .append(item)
                .append(System.lineSeparator());
    }
}

class HTMLListStrategy implements ListStrategy {
    @Override
    public void start(StringBuilder sb) {
        sb.append("<ul>").append(System.lineSeparator());
    }

    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append("  <li>")
                .append(item)
                .append("</li>")
                .append(System.lineSeparator());
    }

    @Override
    public void end(StringBuilder sb) {
        sb.append("</ul>").append(System.lineSeparator());
    }
}

class TextProcessor<LS extends ListStrategy>{
    private StringBuilder sb = new StringBuilder();
    private final LS listStrategy;

    public TextProcessor(Supplier<? extends LS> supplier) {
        listStrategy = supplier.get();
    }

    public void appendList(List<String> items){
        listStrategy.start(sb);
        for (String item : items){
            listStrategy.addListItem(sb, item);
        }
        listStrategy.end(sb);
    }

    public void clear(){
        sb.setLength(0);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}

class Demo{
    public static void main(String[] args) {
        TextProcessor<MarkDownListStrategy> tp = new TextProcessor<>(MarkDownListStrategy::new);
        tp.appendList(List.of("a", "b", "f"));
        System.out.println(tp);

        tp.clear();
        TextProcessor<HTMLListStrategy> tp2 = new TextProcessor<>(HTMLListStrategy::new);
        tp2.appendList(List.of("1", "  2 2 ", "ok"));
        System.out.println(tp2);
    }
}