package patterns.behavioral.interpreter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class InterpreterException extends Exception{
}

class Token1{
    public enum Type{
        VAR,
        INT,
        PLUS,
        MINUS
    }
    public Type type;
    public String content;

    public Token1(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public static Token1 of(int val){
        return new Token1(Type.INT, String.valueOf(val));
    }

    @Override
    public String toString() {
        return String.format("`%s`", content);
    }
}

interface Expression{
    int eval();
}

class Int implements Expression {
    private int val;

    public Int(int val) {
        this.val = val;
    }

    @Override
    public int eval() {
        return val;
    }
}

class Operation implements Expression{
    enum Type{
        MINUS,
        PLUS
    }
    public Type type;
    public Expression left, right;

    public Operation() {
    }

    public Operation(Type type, Expression left, Expression right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    @Override
    public int eval() {
        switch (type){
            case MINUS:
                return left.eval() - right.eval();
            case PLUS:
                return left.eval() + right.eval();
        }
        return 0;
    }
}

class ExpressionProcessor
{
    public Map<Character, java.lang.Integer> variables = new HashMap<>();

    private List<Token1> lex(String expression) throws InterpreterException {
        List<Token1> tokens = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            switch (c){
                case '+':
                    tokens.add(new Token1(Token1.Type.PLUS, String.valueOf(c)));
                    break;
                case '-':
                    tokens.add(new Token1(Token1.Type.MINUS, String.valueOf(c)));
                    break;
                default:
                    if (Character.isDigit(c)){
                        StringBuilder sb = new StringBuilder().append(c);
                        int j = i + 1;
                        char c2;
                        for (; j < expression.length() && Character.isDigit(expression.charAt(j)); j++) {
                            c2 = expression.charAt(j);
                            sb.append(c2);
                        }
                        tokens.add(new Token1(Token1.Type.INT, sb.toString()));
                        i = j - 1;
                        break;
                    }
                    if (Character.isAlphabetic(c)){
                        tokens.add(new Token1(Token1.Type.VAR, String.valueOf(c)));
                        if (i+1 < expression.length() && Character.isAlphabetic(expression.charAt(i + 1))){
                            throw new InterpreterException();
                        }
                    }
            }
        }
        return tokens;
    }

   private int parse(List<Token1> tokens) {
        boolean leftEmpty = false;
        Operation op = new Operation();
        for(Token1 token : tokens){
            Int val;
            switch (token.type){
                case VAR:
                    val = new Int(variables.get(token.content.charAt(0)));
                    if (!leftEmpty) {
                        op.left = val;
                        leftEmpty = true;
                    }
                    else {
                        op.right = val;
                        op.left = new Int(op.eval());
                    }
                    break;
                case INT:
                    val = new Int(java.lang.Integer.parseInt(token.content));
                    if (!leftEmpty) {
                        op.left = val;
                        leftEmpty = true;
                    }
                    else {
                        op.right = val;
                        op.left = new Int(op.eval());
                    }
                    break;
                case PLUS:
                    op.type = Operation.Type.PLUS;
                    break;
                case MINUS:
                    op.type = Operation.Type.MINUS;
                    break;
            }
        }
        return op.left.eval();
    }

    public int calculate(String expression)
    {
        try {
            List<Token1> tokens = lex(expression);
            //System.out.println(tokens.stream().map(Token1::toString).collect(Collectors.joining(System.lineSeparator())));
            return parse(tokens);
        } catch (InterpreterException e) {
            return 0;
        }
    }
}

class Evaluate
{
    public static void main(String[] args) {
        ExpressionProcessor ep = new ExpressionProcessor();
        ep.variables.put('x', 5);

        System.out.println(ep.calculate("1"));
        System.out.println(ep.calculate("1+2"));
        System.out.println(ep.calculate("1+x"));
        System.out.println(ep.calculate("1+xy"));
    }
}

