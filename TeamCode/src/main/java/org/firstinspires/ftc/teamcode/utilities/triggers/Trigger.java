package org.firstinspires.ftc.teamcode.utilities.triggers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class Trigger {
    private final BooleanSupplier input;
    private final List<TriggerBehavior> behaviors = new ArrayList<>();

    public Trigger(BooleanSupplier input) {
        this.input = input;
    }

    public Trigger(DoubleSupplier doubleInput, double threshold) {
        input = () -> doubleInput.getAsDouble() > threshold;
    }

    /**
     * Runs once when button is pressed
     *
     * @param onTrigger code to run
     * @return returns self for behaviour chaining
     */
    public Trigger onRisingEdge(Runnable onTrigger) {
        behaviors.add(new RisingEdge(onTrigger));
        return this;
    }

    /**
     * Runs once when button is released
     *
     * @param onTrigger code to run
     * @return returns self for behaviour chaining
     */
    public Trigger onFallingEdge(Runnable onTrigger) {
        behaviors.add(new FallingEdge(onTrigger));
        return this;
    }

    /**
     * Runs continuously while trigger is held
     *
     * @param whileHeld code to run while the trigger is held
     * @param whileReleased code to run while the trigger is released. Can be omitted
     * @return returns self for behaviour chaining
     */
    public Trigger onHeld(Runnable whileHeld, Runnable whileReleased) {
        behaviors.add(new Held(whileHeld, whileReleased));
        return this;
    }

    public Trigger onHeld(Runnable whileHeld) {
        return this.onHeld(whileHeld, () -> {});
    }

    /**
     * Runs once when the trigger is toggled
     *
     * @param onToggleOn code to run when the trigger is toggled "ON"
     * @param onToggleOff code to run when the trigger is toggled "OFF". This can be omitted to run onToggleOn again.
     * @return returns self for behaviour chaining
     */
    public Trigger onToggle(Runnable onToggleOn, Runnable onToggleOff) {
        behaviors.add(new Toggle(onToggleOn, onToggleOff));
        return this;
    }


    public Trigger onToggle(Runnable onToggle) {
        return this.onToggle(onToggle, onToggle);
    }

    public void update() {
        boolean state = input.getAsBoolean();
        for (TriggerBehavior b: behaviors) {
            b.update(state);
        }
    }

}
