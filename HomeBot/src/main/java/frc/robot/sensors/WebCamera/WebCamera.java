package frc.robot.sensors.WebCamera;


import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;


import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

public class WebCamera extends IterativeRobot {

//a thread created in robotInit() gets the Camera Server instance. Each frame of the video is individually processed, in this case converting a color image (BGR) to gray scale using the OpenCV cvtColor() method. The resultant images are then passed to the output stream and sent to the dashboard. You can replace the cvtColor operation with any image processing code that is necessary for your application. You can even annotate the image using OpenCV methods to write targeting information onto the image being sent to the dashboard.

    public void robotInit() {
        new Thread(() -> {
          UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
          camera.setResolution(640, 480);
    
          CvSink cvSink = CameraServer.getInstance().getVideo();
          CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
    
          Mat source = new Mat();
          Mat output = new Mat();
    
          while(!Thread.interrupted()) {
            if (cvSink.grabFrame(source) == 0) {
              continue;
            }
            Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
            outputStream.putFrame(output);
          }
        }).start();
      }

}