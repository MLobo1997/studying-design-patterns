package patterns.structural.flyweight;

import java.util.Arrays;

class Sentence
{
    private String [] words;
    private WordToken [] tokens;

    public Sentence(String plainText) {
        words = plainText.split(" ");
        tokens = new WordToken[words.length];
        for (int i = 0; i < words.length; i++)
            tokens[i] = new WordToken();
    }

    public WordToken getWord(int index)
    {
        return tokens[index];
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++){
            String word = words[i];
            if (i + 1 < words.length){
                word += " ";
            }
            sb.append(tokens[i].capitalize ? word.toUpperCase() : word);
        }
        return sb.toString();
    }

    class WordToken
    {
        public boolean capitalize = false;

        public WordToken() {
        }

    }
}

class Demo2{
    public static void main(String[] args) {
        Sentence sentence = new Sentence("hello world");
        sentence.getWord(1).capitalize=true;
        System.out.printf("\"%s\"", sentence);
    }
}

