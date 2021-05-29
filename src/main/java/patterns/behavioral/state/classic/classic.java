package patterns.behavioral.state.classic;

//Classic implementation not used in the industry

class State {
    void on(LightSwitch ls){
        System.out.println("Light is already on");
    }

    void off(LightSwitch ls)
    {
        System.out.println("Light is already off");
    }
}

class OnState extends State {
    public OnState() {
        System.out.println("Light is turned on");
    }

    @Override
    void off(LightSwitch ls) {
        System.out.println("Switching light off...");
        ls.setState(new OffState());
    }
}

class OffState extends State{
    public OffState() {
        System.out.println("Light is turned of");
    }

    @Override
    void on(LightSwitch ls) {
        System.out.println("Switching light on...");
        ls.setState(new OnState());
    }
}

class LightSwitch {
    private State state;

    public LightSwitch() {
        state = new OffState();
    }

    void on() {state.on(this);}
    void off() {state.off(this);}

    public void setState(State state) {
        this.state = state;
    }
}

class Demo{
    public static void main(String[] args) {
        LightSwitch lightSwitch = new LightSwitch();
        lightSwitch.off();
        lightSwitch.on();
        lightSwitch.on();
        lightSwitch.off();
    }
}