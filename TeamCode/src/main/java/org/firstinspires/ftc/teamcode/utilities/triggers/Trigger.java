package org.firstinspires.ftc.teamcode.utilities.triggers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

// usage (from Primary.java):
// for onRisingEdge and onFallingEdge (when you press the button and when you release it):
// new Trigger(gamepad1.a).onRisingEdge(this::foo).onFallingEdge(this::bar);

// Runnable (onTrigger, whileHeld, onToggleOn, etc.) accept lambdas or method references
// lambda are inline statements:
// () -> x += 1;
// this is good for basic one line stuff

// method references are references of methods :)
// this::handler
// so if you have private void handler() {} in primary, you pass this::handler into the Runnable
// note handler() can't have arguments, or it won't work (i.e., handler(int arg1, int arg2))

public class Trigger {
    private final BooleanSupplier input;
    private final List<TriggerBehavior> behaviors = new ArrayList<>();

    public Trigger(BooleanSupplier input) {
        this.input = input;
    }

    public Trigger(DoubleSupplier doubleInput, double threshold) {
        input = () -> doubleInput.getAsDouble() > threshold;
    }
    public Trigger onRisingEdge(Runnable onTrigger) {
        behaviors.add(new RisingEdge(onTrigger));
        return this;
    }

    public Trigger onFallingEdge(Runnable onTrigger) {
        behaviors.add(new FallingEdge(onTrigger));
        return this;
    }

    public Trigger onHeld(Runnable whileHeld, Runnable whileReleased) {
        behaviors.add(new Held(whileHeld, whileReleased));
        return this;
    }

    public Trigger onHeld(Runnable whileHeld) {
        return this.onHeld(whileHeld, () -> {});
    }

    public Trigger onToggle(Runnable onToggleOn, Runnable onToggleOff) {
        behaviors.add(new Toggle(onToggleOn, onToggleOff));
        return this;
    }

    public Trigger onTrigger(Runnable onToggleOn) {
        return this.onToggle(onToggleOn, ()->{});
    }

    public void update() {
        boolean state = input.getAsBoolean();
        for (TriggerBehavior b: behaviors) {
            b.update(state);
        }
    }

}
