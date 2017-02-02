package org.usfirst.frc.team9972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Robot extends IterativeRobot {
	
//	Thread visionThread;
	static FrontCameraStreamingThread frontCST;
	static BackCameraStreamingThread backCST;
	Thread frontCSThread = new Thread();
//	Thread backCSThread = new Thread(backCST); 
//	static UsbCamera frontCam;
//	static UsbCamera backCam;
//	
	static boolean pressedLastTime = false;
	static boolean rearCam = false;
	static boolean switchCam = false;
	
//	static CvSink cvSink;
//	static CvSource outputStream;
//	
	Joystick joystick = new Joystick(0);
//	
//	static Mat mat = new Mat();
	
	@Override
	public void robotInit() {
		SmartDashboard.putBoolean("Run thread", false );
//		visionThread = new Thread(() -> {
//			// Get the UsbCamera from CameraServer
//			frontCam = CameraServer.getInstance().startAutomaticCapture();
//			backCam = CameraServer.getInstance().startAutomaticCapture();
//			// Set the resolution
//			frontCam.setResolution(640, 480);
//			backCam.setResolution(640, 480);
//			
//			// Get a CvSink. This will capture Mats from the camera
//			
////			CameraServer.getInstance().getVideo(name)
//			cvSink = CameraServer.getInstance().getVideo(frontCam);
//			
//			// Setup a CvSource. This will send images back to the Dashboard
//			outputStream = CameraServer.getInstance().putVideo("Camera Stream", 640, 480);
//
//			// This cannot be 'true'. The program will never exit if it is. This
//			// lets the robot stop this thread when restarting robot code or
//			// deploying.
//		});
//		visionThread.setDaemon(true);
//		visionThread.start();
		
		frontCST = new FrontCameraStreamingThread(this);
//		backCST = new BackCameraStreamingThread(this);
		FrontCameraStreamingThread.init();
//		BackCameraStreamingThread.init();
		frontCSThread = new Thread(frontCST);
		frontCSThread.start();
	}

	
	@Override
	public void teleopPeriodic() {
		boolean buttonPressed = joystick.getRawButton(1);
		if (buttonPressed && !pressedLastTime) {
//			backCSThread.start();
		}
		pressedLastTime = buttonPressed;
	}

}

