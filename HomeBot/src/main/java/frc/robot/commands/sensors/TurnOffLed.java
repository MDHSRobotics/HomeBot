package frc.robot.commands.sensors;
import frc.robot.sensors.LimeLight;
import frc.robot.consoles.Logger;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class TurnOffLed extends InstantCommand {
    public TurnOffLed(){
        Logger.setup("Constructing InstantCommand: Turn off Limelight's LED...");
    }
    @Override
    public void initialize(){
        LimeLight.setLedMode(1);
    }
    
}
