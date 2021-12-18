package frc.robot.commands.sensors;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.consoles.Logger;
import frc.robot.sensors.WebCamera.*;
public class GetCameraStream extends CommandBase{
    private WebCamera webCamera;
    public GetCameraStream() {
        Logger.setup("Constructing InstantCommand: Get Camera Stream");
    }

    public void initialize() {
       webCamera.CameraInit(); 
    }

}