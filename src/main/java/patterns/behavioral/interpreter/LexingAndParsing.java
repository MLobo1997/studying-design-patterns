package patterns.behavioral.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface Element
{
    int eval();
}

class Integer implements Element
{
    private int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }
}

class BinaryOperation implements Element{
    public enum Type{
        ADDITION,
        SUTRACTION,
    }

    public Type type;
    public Element left, right;

    @Override
    public int eval() {
        switch (type){
            case ADDITION:
                return left.eval() + right.eval();
            case SUTRACTION:
                return left.eval() - right.eval();
            default:
                return 0;
        }
    }
}


class Token{
    public enum Type{
        INTEGER,
        PLUS,
        MINUS,
        LPAREN, //Left parenthesis
        RPAREN
    }
    public Type type;
    public String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "`" + text + "`";
    }
}

class Demo{

    static List<Token> lex(String input){
        ArrayList<Token> tokens = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c){
                case '+':
                    tokens.add(new Token(Token.Type.PLUS, String.valueOf(c)));
                    break;
                case '-':
                    tokens.add(new Token(Token.Type.MINUS, String.valueOf(c)));
                    break;
                case '(':
                    tokens.add(new Token(Token.Type.LPAREN, String.valueOf(c)));
                    break;
                case ')':
                    tokens.add(new Token(Token.Type.RPAREN, String.valueOf(c)));
                    break;
                default:
                    StringBuilder sb = new StringBuilder("" + input.charAt(i));
                    for (int j = i+1; j < input.length(); j++) {
                        if (Character.isDigit(input.charAt(j))){
                            sb.append(input.charAt(j));
                            i++;
                        } else{
                            tokens.add(new Token(Token.Type.INTEGER, sb.toString()));
                            break;
                        }
                    }
                    break;
            }
        }
        return tokens;
    }

    static Element parse(List<Token> tokens){
        BinaryOperation result = new BinaryOperation();
        boolean haveLHS = false;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            switch (token.type){
                case INTEGER:
                    Integer integer = new Integer(java.lang.Integer.parseInt(token.text));
                    if (!haveLHS){
                        result.left = integer;
                        haveLHS = true;
                    } else {
                        result.right = integer;
                    }
                    break;
                case PLUS:
                    result.type = BinaryOperation.Type.ADDITION;
                    break;
                case MINUS:
                    result.type = BinaryOperation.Type.SUTRACTION;
                    break;
                case LPAREN:
                    int j = i;
                    for (; j < tokens.size(); j++) {
                        if (tokens.get(j).type == Token.Type.RPAREN) {
                            break;
                        }
                    }
                    List<Token> subexpression = tokens.stream()
                            .skip(i + 1)
                            .limit(j - i - 1)
                            .collect(Collectors.toList());
                    Element element = parse(subexpression);
                    if (!haveLHS){
                        result.left = element;
                        haveLHS = true;
                    } else{
                        result.right = element;
                    }
                    i = j;
                    break;
                    //case RPAREN:
                    //break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String input = "(13+4)-(12+1)";
        List<Token> tokens = lex(input);
        System.out.println(tokens.stream()
                .map(Token::toString)
                .collect(Collectors.joining(System.lineSeparator()))
        );

        Element parsed = parse(tokens);
        System.out.printf("%s = %s", input, parsed.eval());
    }
}

