package frc.robot;
import frc.robot.commands.talonFXTester.TestTalonFX;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.*;

// Contains singleton instances of all the subsystems on the robot.
public class BotSubsystems {

    public static DiffDriver diffDriver;
    public static TalonFXTest testTalonFX;

    // Initialize all robot subsystems
    public static void initializeSubsystems() {
        Logger.setup("Initializing BotSubsystems...");
        diffDriver = new DiffDriverTalon();
        testTalonFX = new TalonFXTest();
    }

    // Set all the subsystem "teleop" default commands
    public static void setTeleopDefaultCommands() {

        // DiffDriver
        Logger.setup("DiffDriver Teleop Default Command -> DriveDiffTank...");
        diffDriver.setDefaultCommand(BotCommands.driveDiffTank);

        // TestTalonFX
        Logger.setup("testTalonFX Teleop Default Command -> testTalonFX...");
        TestTalonFX.setDefaultCommand(BotCommands.testTalonFX);
    }

}
