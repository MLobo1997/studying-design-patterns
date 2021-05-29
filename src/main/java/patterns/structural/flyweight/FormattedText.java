package patterns.structural.flyweight;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FormattedText {
    private String plainText;
    private boolean[] capitalize;

    public FormattedText(String plainText) {
        this.plainText = plainText;
        //This allocates way too much than necessary.
        capitalize = new boolean[plainText.length()];
    }

    public void capitalize(int start, int end){
        for (int i = start; i <= end; i++){
            capitalize[i] = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i){
            char c = plainText.charAt(i);
            sb.append(capitalize[i] ? Character.toUpperCase(c) : c);
        }
        return sb.toString();
    }
}

class BetterFormatterText{
    private String plainText;
    private List<TextRange> formatting = new ArrayList<>();

    public BetterFormatterText(String plainText) {
        this.plainText = plainText;
    }

    public TextRange getRange(int start, int end){
        TextRange textRange= new TextRange(start, end);
        formatting.add(textRange);
        return textRange;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            for (TextRange range : formatting) {
                if (range.covers(i) && range.capitalize) {
                    c = Character.toUpperCase(c);
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    class TextRange{
        public int start, end;
        public boolean capitalize=false;
        public boolean bold=false;
        public boolean italic=false;

        public TextRange(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean covers(int position){
            return position >= start && position <= end;
        }
    }

}

class Demo1{
    public static void main(String[] args) {
        FormattedText ft = new FormattedText("This is a brave new world");
        ft.capitalize(10, 15);
        System.out.println(ft);

        BetterFormatterText bft = new BetterFormatterText("Make america great again");
        bft.getRange(13, 18).capitalize = true;
        System.out.println(bft);
    }
}
