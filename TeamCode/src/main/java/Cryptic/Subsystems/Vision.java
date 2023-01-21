package Cryptic.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;

import Cryptic.Superclasses.Subsystem;

public class Vision implements Subsystem {
    OpenCvWebcam webcam;
    Pipeline pipeline;

    //for calibration
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    double tagsize = 0.166;

    @Override
    public void initialize (LinearOpMode opMode){

        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(opMode.hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        //webcam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK);
        pipeline = new Pipeline();
        webcam.setPipeline(pipeline);
        //aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        //webcam.setPipeline(aprilTagDetectionPipeline);
        webcam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(320, 176, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode)
            {
                //opMode.telemetry.speak("OpenCVCamera Error");
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
    }
    public enum pLocation {
        LOCATION1,
        LOCATION2,
        LOCATION3
    }
/*
    //Detect with AprilTags to flex on judges
    public void detect(LinearOpMode opMode){

        final double FEET_PER_METER = 3.28084;

        int numFramesWithoutDetection = 0;

        final float DECIMATION_HIGH = 3;
        final float DECIMATION_LOW = 2;
        final float THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
        final int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;

        // Calling getDetectionsUpdate() will only return an object if there was a new frame
        // processed since the last time we called it. Otherwise, it will return null. This
        // enables us to only run logic when there has been a new frame, as opposed to the
        // getLatestDetections() method which will always return an object.
        ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();

        // If there's been a new frame...
        if(detections != null)
        {
            opMode.telemetry.addData("FPS", webcam.getFps());
            opMode.telemetry.addData("Overhead ms", webcam.getOverheadTimeMs());
            opMode.telemetry.addData("Pipeline ms", webcam.getPipelineTimeMs());

            // If we don't see any tags
            if(detections.size() == 0)
            {
                numFramesWithoutDetection++;

                // If we haven't seen a tag for a few frames, lower the decimation
                // so we can hopefully pick one up if we're e.g. far back
                if(numFramesWithoutDetection >= THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION)
                {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_LOW);
                }
            }
            // We do see tags!
            else
            {
                numFramesWithoutDetection = 0;

                // If the target is within 1 meter, turn on high decimation to
                // increase the frame rate
                if(detections.get(0).pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS)
                {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_HIGH);
                }

                for(AprilTagDetection detection : detections)
                {
                    opMode.telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
                    opMode.telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
                    opMode.telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
                    opMode.telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
                    opMode.telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
                    opMode.telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
                    opMode.telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
                }
            }

            opMode.telemetry.update();
        }

        opMode.sleep(20);
    }
*/
//old stuff with signal sleeve with solid colors on it
    public static class Pipeline extends OpenCvPipeline {
        final Scalar RED = new Scalar(255, 0, 0);
        final Scalar BLUE = new Scalar(0, 0, 255);
        final Scalar GREEN = new Scalar(0, 255, 0);

        final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(150,120);
        final int REGION_WIDTH = 30;
        final int REGION_HEIGHT = 30;
        Point region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        Point region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        Mat region1_Cb;
        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        static int avg1;

        void inputToCb(Mat input) {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 2);
        }
        @Override
        public void init(Mat firstFrame)
        {
            inputToCb(firstFrame);
            region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
        }

        @Override
        public Mat processFrame(Mat input) {
            inputToCb(input);
            avg1 = (int) Core.mean(region1_Cb).val[0];
            Imgproc.rectangle(
                    input,
                    region1_pointA,
                    region1_pointB,
                    RED,
                    2);
            return input;
        }

        public static String getAnalysis() {
            //best colors: Blue, Green, Yellow
            if (avg1 > 130 && avg1 < 200) {
                return "BLUE" + " " + avg1;
            } else if (avg1 > 100 && avg1 < 130) {
                return "GREEN" + " " + avg1;
            } else {
                return "YELLOW" + " " + avg1;
            }
        }
    }
}
