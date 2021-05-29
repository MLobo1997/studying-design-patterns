package patterns.behavioral.command.exercise;

class Command
{
    enum Action
    {
        DEPOSIT, WITHDRAW
    }

    public Action action;
    public int amount;
    public boolean success;

    public Command(Action action, int amount)
    {
        this.action = action;
        this.amount = amount;
    }

}

class Account
{
    public int balance;

    public boolean deposit(float value){
        balance += value;
        return true;
    }

    public boolean withdraw(float value){
        if (value <= balance) {
            balance -= value;
            return true;
        } else {
            return false;
        }
    }

    public void process(Command c){
        switch (c.action){
            case DEPOSIT:
                c.success = deposit(c.amount);
                break;
            case WITHDRAW:
                c.success = withdraw(c.amount);
                break;
        }
    }
}

