package frc.robot.commands.sensors;
import frc.robot.sensors.LimeLight;
import frc.robot.consoles.Logger;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class TurnOnLed extends InstantCommand {
    public TurnOnLed(){
        Logger.setup("Constructing InstantCommand: Turn on Limelight's LED...");
    }
    @Override
    public void initialize(){
        LimeLight.setLedMode(3);
        Logger.info("turn on");
    }
    

}
