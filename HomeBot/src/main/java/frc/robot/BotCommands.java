package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.consoles.Logger;
import frc.robot.commands.delivery.*;
import frc.robot.commands.diffdriver.*;
import frc.robot.commands.pickup.*;
import frc.robot.commands.shooter.*;

// Contains singleton instances of all the commands on the robot.
public class BotCommands {

	// DiffDriver
    public static RotateToDpadDirection rotateToDpadDirection;
    public static DriveDiffTank driveDiffTank;
    public static DriveDiffArcade driveDiffArcade;

    // Delivery
    public static SpinDelivery spinDelivery;
    public static StopDelivery stopDelivery;

    // Pickup
    public static SpinPickup spinPickup;
    public static StopPickup stopPickup;

    //Shoot
    public static Shoot shoot;
    public static StopShoot stopShoot;
    public static ResetShoot resetShoot;
    public static DeliverAndShoot deliverAndShoot;
    public static StopDeliverButShoot stopDeliverbutShoot;
    
    // Initialize all robot commands
    public static void initializeCommands() {
        Logger.setup("Initializing BotCommands...");

        // DiffDriver
        driveDiffTank = new DriveDiffTank(BotSubsystems.diffDriver, BotControllers.xbox);
        driveDiffArcade = new DriveDiffArcade(BotSubsystems.diffDriver, BotControllers.xbox);


        // Delivery
        spinDelivery = new SpinDelivery(BotSubsystems.delivery);
        stopDelivery = new StopDelivery(BotSubsystems.delivery);

        // Pickup
        spinPickup = new SpinPickup(BotSubsystems.pickup);
        stopPickup = new StopPickup(BotSubsystems.pickup);

        // Shooter
        shoot = new Shoot(BotSubsystems.shooter);
        stopShoot = new StopShoot(BotSubsystems.shooter);
        resetShoot = new ResetShoot(BotSubsystems.shooter);
        deliverAndShoot = new DeliverAndShoot(BotSubsystems.delivery, BotSubsystems.shooter);
        stopDeliverbutShoot = new StopDeliverButShoot(BotSubsystems.delivery, BotSubsystems.shooter);
    }

    // Return the command to run in autonomous mode
    public static Command getAutonomousCommand() {

        // Find the currently selected auto command in Shuffleboard and return it
        Command autoCommand = RobotManager.autoCommandChooser.getSelected();
        return autoCommand;
    }

}
