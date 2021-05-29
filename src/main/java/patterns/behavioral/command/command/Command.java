package patterns.behavioral.command.command;

import com.google.common.collect.Lists;

import java.util.List;

//Command - contains all information necessary to perform an action. Useful when you need something like undo/redo.
class BankAccount {
    private int balance = 0;
    private int overdraftLimit = -500;

    public boolean deposit(int amount){
        balance += amount;
        System.out.printf("Deposited %s balance is now %s%n", amount, balance);
        return true;
    }

    public boolean withdraw(int amount){
        if (balance - amount >= overdraftLimit){
            balance -= amount;
            System.out.printf("Withdrew %s balance is now %s%n", amount, balance);
            return true;
        }
        else return false;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}

interface Command{
    void call();
    void undo();
}

class BankAccountCommand implements Command{
    private BankAccount account;
    private  boolean succeeded;
    public enum Action{
        DEPOSIT, WITHDRAW
    }
    private Action action;
    private int amount;

    public BankAccountCommand(BankAccount account, Action action, int amount) {
        this.account = account;
        this.action = action;
        this.amount = amount;
    }

    @Override
    public void call() {
        switch (action) {
            case DEPOSIT:
                succeeded = account.deposit(amount);
                break;
            case WITHDRAW:
                succeeded = account.withdraw(amount);
                break;
        }
    }

    @Override
    public void undo() {
        if (succeeded) {
            switch (action) {
                case DEPOSIT:
                    account.withdraw(amount);
                    break;
                case WITHDRAW:
                    account.deposit(amount);
                    break;
            }
        }
    }
}

class Demo{
    public static void main(String[] args) {
        BankAccount ba = new BankAccount();
        System.out.println(ba);

        List<BankAccountCommand> commands = List.of(
                new BankAccountCommand(ba,
                        BankAccountCommand.Action.DEPOSIT,
                        100),
                new BankAccountCommand(ba, BankAccountCommand.Action.WITHDRAW, 1000)
        );

        for (BankAccountCommand c : commands){
            c.call();
            System.out.println(ba);
        }

        ;
        for (Command c : Lists.reverse(commands)){
            c.undo();
            System.out.println(ba);
        }

    }
}
