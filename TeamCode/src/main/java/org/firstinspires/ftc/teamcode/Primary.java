package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
=============================
    Gamepad Controls
=============================
- BALLS -
    (A) TOGGLE - Intake Motor Cylinder Surgical Tubing Thing
    (DPAD_D) TOGGLE - Intake Servos Sides
    (LEFT_TRIGGER) HOLD - Reverse intake motor and servos
    (RIGHT_TRIGGER) HOLD - Flywheel motor

- Drive -
    (LEFT_STICK) - Forwards, Backwards, Strafe Left, Strafe Right
    (RIGHT_STICK) - Turn/Rotate/Pivot

*/

@TeleOp
public class Primary extends LinearOpMode {
    private DcMotor backRight;
    private DcMotor backLeft;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    // speed modifier for wheels (NTS: Maybe add control for variable speed)
    private double speed = 1.0;
    private double flyWheelSpeed = 1.0;
    private boolean xPressed = false;

    // intake/shooting
    private DcMotor flyWheel;
    private DcMotor intake;
    private CRServo intakeServoL;
    private CRServo intakeServoR;
    private Servo flicker;
    private boolean intakeMotorActive = false;
    private boolean intakeServoActive = false;
    private boolean gamepadAPressed = false;
    private boolean dpadDownPressed = false;
    private boolean yPressed = false;
    private boolean triggerPressed = false;
    private boolean flyWheelActive = false;

    private void initialize() {
        frontLeft = hardwareMap.get(DcMotor.class, "front-left");
        frontRight = hardwareMap.get(DcMotor.class, "front-right");
        backRight = hardwareMap.get(DcMotor.class, "back-right");
        backLeft = hardwareMap.get(DcMotor.class, "back-left");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        intake = hardwareMap.get(DcMotor.class, "intake");
        intakeServoL = hardwareMap.get(CRServo.class, "intake-servo-L");
        intakeServoR = hardwareMap.get(CRServo.class, "intake-servo-R");
        flicker = hardwareMap.get(Servo.class, "flicker");
        flicker.setPosition(0);
        flyWheel = hardwareMap.get(DcMotor.class, "fly-wheel");


        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void runOpMode() {
        double servoMax = 0.6;
        double servoMin = 0.4;
        int intakeDirection;

        initialize();
        waitForStart();

        while (opModeIsActive()) {
            // reverse intake direction (there's probably better ways to do this idk)
            // reverse intake direction (there's probably better ways to do this idk)
            if (gamepad1.left_trigger > 0.5) {
                intakeDirection = -1;
            }
            else {
                intakeDirection = 1;
            }


            // omni wheel drive
            double drive = -1 * gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double twist = gamepad1.right_stick_x;
            double[] speeds = {
                    (drive + strafe + twist), // FL
                    (drive - strafe - twist), // FR
                    (drive - strafe + twist), // BL
                    (drive + strafe - twist) // BR
            };
            double max = Math.abs(speeds[0]); // normalize values
            for (double v : speeds) {
                if (max < Math.abs(v)) max = Math.abs(v);
            }
            if (max > 1) {
                for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
            }
            frontLeft.setPower(speeds[0] * speed);
            frontRight.setPower(speeds[1] * speed);
            backLeft.setPower(speeds[2] * speed);
            backRight.setPower(speeds[3] * speed);


            // intake motor
            if (gamepad1.a) {
                if (!gamepadAPressed) {
                    gamepadAPressed = true;
                    intakeMotorActive = !intakeMotorActive;
                }
            } else {
                gamepadAPressed = false;
            }
            intake.setPower((intakeMotorActive)? -1 * intakeDirection: 0);


            // intake servos
            if (gamepad1.dpad_down) {
                if (!dpadDownPressed) {
                    dpadDownPressed = true;
                    intakeServoActive = !intakeServoActive;
                }
            } else {
                dpadDownPressed = false;
            }
            intakeServoL.setPower((intakeServoActive)? -1 * intakeDirection: 0);
            intakeServoR.setPower((intakeServoActive)? intakeDirection: 0);


            // flywheel
            if (gamepad1.right_trigger > 0.7) {
                if (!triggerPressed) {
                    triggerPressed = true;
                    flyWheelActive = !flyWheelActive;
                }
            }
            else {
                triggerPressed = false;
            }
            flyWheel.setPower(flyWheelActive?flyWheelSpeed:0);


            // flicker
            if (gamepad2.dpad_up) {
                flicker.setPosition(servoMax);

            } else {
                flicker.setPosition(servoMin);
            }


            // slow mode for drive
            if (gamepad2.x) {
                if (!xPressed) {
                    xPressed = true;
                    speed = (speed >= 1)?0.3:1;
                }
            } else {
                xPressed = false;
            }

            if (gamepad2.y) {
                if (!yPressed) {
                    yPressed = true;
                    flyWheelSpeed = (flyWheelSpeed >= 1)?0.5:1;
                }
            } else {
                yPressed = false;
            }

            telemetry.addData("Intake Motor", (intakeMotorActive)? "Active": "Inactive");
            telemetry.addData("Intake Servos", (intakeServoActive)? "Active": "Inactive");
            telemetry.addData("Intake Direction", (intakeDirection == 1)? "Pull": "Push");
            telemetry.addData("Speed", speed);
            telemetry.addData("flywheelSpeed", flyWheelSpeed);
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
