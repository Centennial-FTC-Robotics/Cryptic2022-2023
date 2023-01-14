package Cryptic.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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

import Cryptic.Superclasses.Subsystem;

public class Vision implements Subsystem {
    OpenCvInternalCamera phoneCam;
    Pipeline pipeline;
    @Override
    public void initialize (LinearOpMode opMode){
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK);
        pipeline = new Pipeline();
        phoneCam.setPipeline(pipeline);
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.startStreaming(320, 176, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }
            @Override
            public void onError(int errorCode)
            {
                opMode.telemetry.speak("OpenCVCamera Error");
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
    }
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

        public static int getAnalysis() {
            return avg1;
        }
    }
}
