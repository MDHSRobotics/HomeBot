package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.consoles.Logger;
import frc.robot.commands.diffdriver.*;
import frc.robot.commands.Delivery.*;
import frc.robot.commands.pickup.*;
import frc.robot.commands.shooter.*;

// Contains singleton instances of all the commands on the robot.
public class BotCommands {

	// DiffDriver
    public static RotateToDpadDirection rotateToDpadDirection;
    public static DriveDiffTank driveDiffTank;

    // Delivery
    public static SpinDelivery spinDelivery;
    public static StopDelivery stopDelivery;



    // Pickup
    public static spinPickup SpinPickup;
    public static stopPickup StopPickup;

    //Shoot
    public static Shoot shoot;
    public static StopShooter stopShooter;
    
    // Initialize all robot commands
    public static void initializeCommands() {
        Logger.setup("Initializing BotCommands...");

        // DiffDriver
        driveDiffTank = new DriveDiffTank(BotSubsystems.diffDriver, BotControllers.xbox);

        // Delivery
        spinDelivery = new SpinDelivery(BotSubsystems.Delivery);
        stopDelivery = new StopDelivery(BotSubsystems.Delivery);

    }

    // Return the command to run in autonomous mode
    public static Command getAutonomousCommand() {

        // Find the currently selected auto command in Shuffleboard and return it
        Command autoCommand = RobotManager.autoCommandChooser.getSelected();
        return autoCommand;
    }

}
