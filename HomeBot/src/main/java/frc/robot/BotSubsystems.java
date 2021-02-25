package frc.robot;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.*;


// Contains singleton instances of all the subsystems on the robot.
public class BotSubsystems {

    public static DiffDriver diffDriver;
    public static Delivery delivery;
    public static Pickup pickup;
    public static Shooter shooter;

    // Initialize all robot subsystems
    public static void initializeSubsystems() {
        Logger.setup("Initializing BotSubsystems...");
        diffDriver = new DiffDriverTalonSRX();
        delivery = new Delivery();
        pickup = new Pickup();
        shooter = new Shooter();
    }

    // Set all the subsystem "teleop" default commands
    public static void setTeleopDefaultCommands() {

        // DiffDriver
        Logger.setup("DiffDriver Teleop Default Command -> DriveDiffTank...");
        diffDriver.setDefaultCommand(BotCommands.driveDiffTank);

        // Delivery
        Logger.setup("Delivery Teleop Default Command -> StopDelivery...");
        delivery.setDefaultCommand(BotCommands.stopDelivery);

        // Pickup
        Logger.setup("Pickup Teleop Default Command -> StopPickup...");
        pickup.setDefaultCommand(BotCommands.stopPickup);

        //Shoot
        Logger.setup("Shooter Teleop Default Command -> Shoot...");
        shooter.setDefaultCommand(BotCommands.stopShoot);

    }

}
