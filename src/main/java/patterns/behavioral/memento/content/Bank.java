package patterns.behavioral.memento.content;

//Commando stores changes. Memento stores whole changes.
class BankAccount{
    private int balance;

    public BankAccount() {
        this.balance = 0;
    }

    public Memento deposit(int amount){
        balance += amount;
        return new Memento(balance);
    }

    public void restore(Memento m)
    {
        balance = m.balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}

//If you decide to store all of them it can occupy much more space.
class Memento{
    public final int balance;

    public Memento(int balance) {
        this.balance = balance;
    }
}

class Demo{
    public static void main(String[] args) {
        BankAccount ba = new BankAccount();
        Memento m = ba.deposit(100);
        Memento m1 = ba.deposit(50);
        Memento m2 = ba.deposit(25);
        System.out.println(ba);

        ba.restore(m);
        System.out.println(ba);

        ba.restore(m1);
        System.out.println(ba);

        ba.restore(m2);
        System.out.println(ba);
    }
}

public class Bank {
}
