package patterns.behavioral.template;

abstract class Game {
    protected int currentPlayer;
    protected final int numberOfPlayer;

    public Game(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public void run() {
        start();
        while (!haveWinner())
            takeTurn();
        System.out.printf("Player %s wins%n", getWinningPlayer());
    }

    protected abstract int getWinningPlayer();

    protected abstract void takeTurn();

    protected abstract boolean haveWinner();

    protected abstract void start();
}

class Chess extends Game{
    private final int maxTurns = 10;
    private int turn = 1;

    public Chess() {
        super(2);
    }

    @Override
    protected int getWinningPlayer() {
        return 0;
    }

    @Override
    protected void takeTurn() {
        System.out.printf("Turn %s taken by player %s%n", turn++, currentPlayer);
        currentPlayer = (currentPlayer+1) % numberOfPlayer;
    }

    @Override
    protected boolean haveWinner() {
        return turn == maxTurns;
    }

    @Override
    protected void start() {
        System.out.println("Starting Chess.");
    }
}

class Demo{
    public static void main(String[] args) {
        new Chess().run();
    }
}