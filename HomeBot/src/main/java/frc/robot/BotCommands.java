package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.consoles.Logger;
import frc.robot.commands.diffdriver.*;
import frc.robot.commands.auto.*;




// Contains singleton instances of all the commands on the robot.
public class BotCommands {
    // DiffDriver
    public static RotateToDpadDirection rotateToDpadDirection;
    public static DriveDiffTank driveDiffTank;
    
    // Autonomous
    public static AutoDrivePath autoDrivePath;
    
    // Initialize all robot commands
    public static void initializeCommands() {
        Logger.setup("Initializing BotCommands...");

        // DiffDriver
        driveDiffTank = new DriveDiffTank(BotSubsystems.diffDriver, BotControllers.xbox);

        // Autonomous 
        autoDrivePath = new AutoDrivePath(BotSubsystems.diffDriver);
    }

    // Return the command to run in autonomous mode
    public static Command getAutonomousCommand() {

        return autoDrivePath;

        // Find the currently selected auto command in Shuffleboard and return it
       // Command autoCommand = RobotManager.autoCommandChooser.getSelected();
        //return autoCommand;
    }

}
