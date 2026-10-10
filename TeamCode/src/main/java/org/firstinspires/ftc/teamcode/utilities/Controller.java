package org.firstinspires.ftc.teamcode.utilities;

import org.firstinspires.ftc.teamcode.utilities.triggers.Trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/**
 * Controller class for using basic types of triggers. Here are some examples:
 * <p>
 * To toggle x when button A is pressed:
 * <pre>{@code
 * controller.addTrigger(()->gamepad1.a)<br>
 *      .onToggle(() -> x = !x);
 * }</pre>
 * To run launch() when right trigger is held down past 0.5 threshold:
 *<pre>{@code
 * controller.addTrigger(() -> gamepad1.right_trigger, 0.5)
 *      .onRisingEdge(this::launch);
 * }</pre>
 */
public class Controller {
    private final List<Trigger> triggers = new ArrayList<>();

    private Trigger add(Trigger trigger) {
        triggers.add(trigger);
        return trigger;
    }

    public Trigger addTrigger(BooleanSupplier input) {
        return add(new Trigger(input));
    }

    public Trigger addTrigger(DoubleSupplier input, double threshold) {
        return add(new Trigger(input, threshold));
    }

    public void update() {
        for (int i = 0; i < triggers.size(); i++) {
            triggers.get(i).update();
        }
    }
}
