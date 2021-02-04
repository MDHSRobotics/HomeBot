package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.consoles.Logger;
import frc.robot.commands.diffdriver.*;
import frc.robot.commands.sensors.TurnOffLed;
import frc.robot.commands.sensors.TurnOnLed;



// Contains singleton instances of all the commands on the robot.
public class BotCommands {
    // DiffDriver
    public static RotateToDpadDirection rotateToDpadDirection;
    public static DriveDiffTank driveDiffTank;
    //Sensors
    public static TurnOffLed turnOffLed;
    public static TurnOnLed turnOnLed;
    
    // Initialize all robot commands
    public static void initializeCommands() {
        Logger.setup("Initializing BotCommands...");

        // DiffDriver
        driveDiffTank = new DriveDiffTank(BotSubsystems.diffDriver, BotControllers.xbox);
        // Sensors 
        turnOffLed = new TurnOffLed();
        turnOnLed = new TurnOnLed();
    }

    // Return the command to run in autonomous mode
    public static Command getAutonomousCommand() {

        // Find the currently selected auto command in Shuffleboard and return it
        Command autoCommand = RobotManager.autoCommandChooser.getSelected();
        return autoCommand;
    }

}
