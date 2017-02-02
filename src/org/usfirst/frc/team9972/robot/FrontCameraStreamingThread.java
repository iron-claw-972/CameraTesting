package org.usfirst.frc.team9972.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;

public class FrontCameraStreamingThread implements Runnable {

	Robot r;
	Thread visionThread;
	static FrontCameraStreamingThread cst;
	static UsbCamera frontCam;
//	static UsbCamera backCam;

	static boolean pressedLastTime = false;
	static boolean rearCam = false;

	static CvSink cvSink;
	static CvSource outputStream;

	Joystick joystick = new Joystick(0);

	static Mat mat = new Mat();

	public FrontCameraStreamingThread(Robot r) {
		this.r = r;
	}

	public static boolean init() {
		frontCam = CameraServer.getInstance().startAutomaticCapture(0);
//		backCam = CameraServer.getInstance().startAutomaticCapture(1);
		// Set the resolution
		frontCam.setResolution(640, 480);
//		backCam.setResolution(640, 480);

		// Get a CvSink. This will capture Mats from the camera

		// CameraServer.getInstance().getVideo(name)
		cvSink = CameraServer.getInstance().getVideo(frontCam);

		// Setup a CvSource. This will send images back to the Dashboard
		outputStream = CameraServer.getInstance().putVideo("Camera Stream", 640, 480);

		// This cannot be 'true'. The program will never exit if it is. This
		// lets the robot stop this thread when restarting robot code or
		// deploying.

		System.out.println("HELLO");

		return true;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			// if (Robot.rearCam) {
//			if (Robot.switchCam) {
//				// CameraServer.getInstance().removeCamera(backCam.getName());
//				// CameraServer.getInstance().removeCamera("opencv_" +
//				// backCam.getName());
//				cvSink = CameraServer.getInstance().getVideo(frontCam);
//				Robot.switchCam = !Robot.switchCam;
//				System.out.println("HELLO");
//			}

			if (cvSink.grabFrame(mat) == 0) {
				// Send the output the error.
				outputStream.notifyError(cvSink.getError());
				// skip the rest of the current iteration
				continue;
			}
			// Put a rectangle on the image
			Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400), new Scalar(255, 255, 255), 5);
			// Give the output stream a new image to display
			outputStream.putFrame(mat);
			//// } else if (!Robot.rearCam) {
			//
			// if (Robot.switchCam) {
			//// CameraServer.getInstance().removeCamera("opencv_" +
			//// frontCam.getName());
			//// CameraServer.getInstance().removeCamera(frontCam.getName());
			// cvSink = CameraServer.getInstance().getVideo(backCam);
			// Robot.switchCam = !Robot.switchCam;
			// System.out.println("Bye");
			// }
			//
			// // Tell the CvSink to grab a frame from the camera and put it
			// // in the source mat. If there is an error notify the output.
			// if (cvSink.grabFrame(mat) == 0) {
			// // Send the output the error.
			// outputStream.notifyError(cvSink.getError());
			// // skip the rest of the current iteration
			// continue;
			// }
			// // Put a rectangle on the image
			// Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
			//// new Scalar(255, 255, 255), 5);
			// // Give the output stream a new image to display
			// outputStream.putFrame(mat);
			// }
		}

	}

}
