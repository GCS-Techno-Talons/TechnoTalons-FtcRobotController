package org.firstinspires.ftc.teamcode.utilities;

import org.firstinspires.ftc.teamcode.utilities.triggers.Trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class Controller {
    // all triggers
    private final List<Trigger> triggers = new ArrayList<>();

    // add a new trigger via
    // controller.add(new Trigger(gamepad1.a).onRisingEdge(this::foo));
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

    // check all triggers
    public void update() {
        for (Trigger t : triggers) {
            t.update();
        }
    }
}
