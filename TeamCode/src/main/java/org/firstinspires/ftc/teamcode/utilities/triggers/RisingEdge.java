package org.firstinspires.ftc.teamcode.utilities.triggers;

// on rising edge is the instant when it goes from false to true
public class RisingEdge implements TriggerBehavior {
    private final Runnable onTrigger;
    private boolean prevState = false;
    public RisingEdge(Runnable onTrigger) {
        this.onTrigger = onTrigger;
    }

    public void update(boolean state) {
        if (state && prevState) onTrigger.run();
        prevState = state;
    }
}
