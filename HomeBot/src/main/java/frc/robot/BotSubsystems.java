package frc.robot;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.*;


// Contains singleton instances of all the subsystems on the robot.
public class BotSubsystems {

    public static DiffDriver diffDriver;
    public static Pickup Pickup;

    // Initialize all robot subsystems
    public static void initializeSubsystems() {
        Logger.setup("Initializing BotSubsystems...");
        diffDriver = new DiffDriverTalon();
        Pickup = new Pickup();
    }

    // Set all the subsystem "teleop" default commands
    public static void setTeleopDefaultCommands() {

        // DiffDriver
        Logger.setup("DiffDriver Teleop Default Command -> DriveDiffTank...");
        diffDriver.setDefaultCommand(BotCommands.driveDiffTank);

        // Pickup
        Logger.setup("Pickup Teleop Default Command -> StopPickup...");
        Pickup.setDefaultCommand(BotCommands.StopPickup);
    }

}
