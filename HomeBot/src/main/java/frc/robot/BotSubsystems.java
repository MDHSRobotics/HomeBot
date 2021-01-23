package frc.robot;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.*;

// Contains singleton instances of all the subsystems on the robot.
public class BotSubsystems {


    public static Conveyor conveyor;
    public static DiffDriver diffDriver;
    public static Lighter lighter;
    public static Roller roller;
    public static Shooter shooter;

    // Initialize all robot subsystems
    public static void initializeSubsystems() {
        Logger.setup("Initializing BotSubsystems...");
        conveyor = new Conveyor();
        diffDriver = new DiffDriverTalon();
        lighter = new Lighter();
        roller = new Roller();
        shooter = new Shooter();
    }

    // Set all the subsystem "teleop" default commands
    public static void setTeleopDefaultCommands() {

        // Conveyor
        Logger.setup("Conveyor Teleop Default Command -> StopConveyor...");
        conveyor.setDefaultCommand(BotCommands.stopConveyor);
        // DiffDriver
        Logger.setup("DiffDriver Teleop Default Command -> DriveDiffTank...");
        diffDriver.setDefaultCommand(BotCommands.driveDiffTank);
        // Roller
        Logger.setup("Roller Teleop Default Command -> StopRoller...");
        roller.setDefaultCommand(BotCommands.stopRoller);
        // Shooter
        Logger.setup("Shooter Teleop Default Command -> StopShooter...");
        shooter.setDefaultCommand(BotCommands.stopShooter);
    }

    // Set all the subsystem "test" default commands
    public static void setTestDefaultCommands() {
        Logger.setup("DiffDriver Test Default Command -> DriveDiffTank...");
        diffDriver.setDefaultCommand(TestCommands.driveDiffTank);
    }

}
