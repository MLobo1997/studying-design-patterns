package patterns.behavioral.memento.exercise;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Token
{
    public int value = 0;

    public Token(int value)
    {
        this.value = value;
    }

    public Token(Token token)
    {
        this.value = token.value;
    }
}

class Memento
{
    public List<Token> tokens;

    public Memento(List<Token> tokens) {
        this.tokens = tokens.stream().map(Token::new).collect(Collectors.toList());
    }
}

class TokenMachine
{
    public List<Token> tokens = new ArrayList<>();

    public Memento addToken(int value)
    {
        tokens.add(new Token(value));
        return new Memento(tokens);
    }

    public Memento addToken(Token token)
    {
        tokens.add(token);
        return new Memento(tokens);
    }

    public void revert(Memento m)
    {
        tokens = m.tokens;
    }
}
