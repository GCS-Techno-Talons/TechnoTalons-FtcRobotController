package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.utilities.input.AprilTagHelper;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp(name = "AprilTagHelper", group = "AprilTag")
//@Disabled
public class AprilTagHelperTest extends OpMode {

    AprilTagHelper aprilTagHelper;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        aprilTagHelper = new AprilTagHelper(hardwareMap.get(WebcamName.class, "Logitech"));
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {

    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
        double bearing = aprilTagHelper.getAngleFromId(40);
        telemetry.addData("Tag bearing", bearing);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }

}