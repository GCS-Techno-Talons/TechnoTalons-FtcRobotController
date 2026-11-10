package org.firstinspires.ftc.teamcode.utilities.input;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.*;

import java.util.List;

public class AprilTagHelper {

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    public AprilTagHelper(WebcamName webcam) {
        AprilTagProcessor.Builder aprilTagProcessorBuilder = new AprilTagProcessor.Builder()
            .setTagLibrary(AprilTagGameDatabase.getCurrentGameTagLibrary())   // The OpMode must have already created a Library.
            .setDrawTagID(true)       // Default: true, for all detections.
            .setDrawTagOutline(true)  // Default: true, when tag size was provided (thus eligible for pose estimation).
            .setDrawAxes(true)        // Default: false.
            .setDrawCubeProjection(true);        // Default: false.

        // Create an AprilTagProcessor by calling build()
        aprilTagProcessor = aprilTagProcessorBuilder.build();

        // Create a new VisionPortal Builder object.
        VisionPortal.Builder visionPortalBuilder = new VisionPortal.Builder()
            .setCamera(webcam)      // Other choices are: RC phone camera and "switchable camera name".
            .addProcessor(aprilTagProcessor)       // An added Processor is enabled by default.
            .setCameraResolution(new Size(640, 480))  // Each resolution, for each camera model, needs calibration values for good pose estimation.
            .setStreamFormat(VisionPortal.StreamFormat.MJPEG)  // MJPEG format uses less bandwidth than the default YUY2.
            .enableLiveView(true)      // Enable LiveView (RC preview).
            .setAutoStopLiveView(true);     // Automatically stop LiveView (RC preview) when all vision processors are disabled.

        // Create a VisionPortal by calling build()
        visionPortal = visionPortalBuilder.build();
    }

    public List<AprilTagDetection> getTagDetections() {
        return aprilTagProcessor.getDetections();  // list of all detections
    }

    public AprilTagDetection getDetection(int id) {
        List<AprilTagDetection> detections = getTagDetections();


        //for detection in
        return null;
    }
}