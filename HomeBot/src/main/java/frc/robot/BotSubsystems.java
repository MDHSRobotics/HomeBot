package frc.robot;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.*;


// Contains singleton instances of all the subsystems on the robot.
public class BotSubsystems {

    public static DiffDriver diffDriver;
    public static Delivery Delivery;
    public static Pickup Pickup;
    public static Shooter shooter;

    // Initialize all robot subsystems
    public static void initializeSubsystems() {
        Logger.setup("Initializing BotSubsystems...");
        diffDriver = new DiffDriverTalonSRX();
        Delivery = new Delivery();
        Pickup = new Pickup();
        shooter = new Shooter();
    }

    // Set all the subsystem "teleop" default commands
    public static void setTeleopDefaultCommands() {

        // DiffDriver
        Logger.setup("DiffDriver Teleop Default Command -> DriveDiffTank...");
        diffDriver.setDefaultCommand(BotCommands.driveDiffTank);

        // Delivery
        Logger.setup("Delivery Teleop Default Command -> StopDelivery...");
        Delivery.setDefaultCommand(BotCommands.stopDelivery);

        // Pickup
        Logger.setup("Pickup Teleop Default Command -> StopPickup...");
        Pickup.setDefaultCommand(BotCommands.stopPickup);

        //Shoot
        Logger.setup("Shooter Teleop Default Command -> Shoot...");
        shooter.setDefaultCommand(BotCommands.stopShooter);

    }

}
