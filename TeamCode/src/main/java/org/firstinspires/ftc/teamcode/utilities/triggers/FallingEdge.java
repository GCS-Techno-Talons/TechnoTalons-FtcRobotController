package org.firstinspires.ftc.teamcode.utilities.triggers;

// on falling edge is the instant when a button goes from pressed to released (false -> true)
public class FallingEdge implements TriggerBehavior {
    private final Runnable onTrigger;
    private boolean prevState = false;

    public FallingEdge(Runnable onTrigger) {
        this.onTrigger = onTrigger;
    }

    public void update(boolean state) {
        if (!state && prevState) {
            onTrigger.run();
        }
        prevState = state;
    }
}
