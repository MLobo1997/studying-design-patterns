package patterns.behavioral.state.exercise;

class CombinationLock
{
    private int [] combination;
    public String status;

    public CombinationLock(int[] combination)
    {
        this.combination = combination;
        this.status = "LOCKED";
    }

    private boolean pinIsCorrect(){
        for (int i = 0; i < status.length(); i++) {
            if (combination[i] != Integer.parseInt("" + status.charAt(i)))
                return false;
        }
        return true;
    }

    private boolean shouldOpen(){
        if (combination.length != status.length())
            return false;
        for (int i = 0; i < combination.length; i++) {
            if (combination[i] != Integer.parseInt("" + status.charAt(i)))
                return false;
        }
        return true;
    }

    public void enterDigit(int digit)
    {
        if (status.equals("LOCKED") || status.equals("ERROR")){
            status = "";
        }

        status += digit;

        if (!pinIsCorrect()) {
            status = "ERROR";
        }

        if (shouldOpen()) {
            status = "OPEN";
        }
    }
}

