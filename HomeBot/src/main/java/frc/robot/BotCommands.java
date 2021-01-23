
package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.commands.auto.*;
import frc.robot.commands.conveyor.*;
import frc.robot.commands.diffdriver.*;
import frc.robot.commands.lighter.*;
import frc.robot.commands.roller.*;
import frc.robot.commands.sensors.*;
import frc.robot.commands.shooter.*;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.Conveyor.ConveyorDirection;


// Contains singleton instances of all the commands on the robot.
public class BotCommands {

    // Conveyor
    public static SpinConveyor spinConveyorForward;
    public static SpinConveyor spinConveyorBackward;
    public static StopConveyor stopConveyor;

    // DiffDriver
    public static RotateToDpadDirection rotateToDpadDirection;
    public static RotateTowardsTarget rotateTowardsTarget;
    public static DriveDiffTank driveDiffTank;

    // Lighter
    public static CycleLights cycleLights;

    // Roller
    public static SpinRollerAndConveyor spinRollerAndConveyor;
    public static StopRoller stopRoller;

    // Sensors
    public static TurnOffLimelightArray turnOffLimelightArray;

    // Shooter
    public static ResetShoot resetShoot;
    public static ReverseConveyorAndShoot reverseConveyorAndShoot;
    public static StopConveyorAndShooter stopConveyorAndShooter;
    public static StopShooter stopShooter;

    // Initialize all robot commands
    public static void initializeCommands() {
        Logger.setup("Initializing BotCommands...");

        // Conveyor
        spinConveyorForward = new SpinConveyor(BotSubsystems.conveyor, ConveyorDirection.forward);
        spinConveyorBackward = new SpinConveyor(BotSubsystems.conveyor, ConveyorDirection.backward);
        stopConveyor = new StopConveyor(BotSubsystems.conveyor);

        // DiffDriver
        rotateTowardsTarget = new RotateTowardsTarget(BotSubsystems.diffDriver);
        driveDiffTank = new DriveDiffTank(BotSubsystems.diffDriver, BotControllers.xbox);

        // Lighter
        cycleLights = new CycleLights(BotSubsystems.lighter);

        // Roller
        spinRollerAndConveyor = new SpinRollerAndConveyor(BotSubsystems.roller, BotSubsystems.conveyor);
        stopRoller = new StopRoller(BotSubsystems.roller);

        // Sensors
        turnOffLimelightArray = new TurnOffLimelightArray();

        // Shooter
        resetShoot = new ResetShoot(BotSubsystems.shooter);
        reverseConveyorAndShoot = new ReverseConveyorAndShoot(BotSubsystems.conveyor, BotSubsystems.shooter);
        stopConveyorAndShooter = new StopConveyorAndShooter(BotSubsystems.conveyor, BotSubsystems.shooter);
        stopShooter = new StopShooter(BotSubsystems.shooter);
    }

    // Return the command to run in autonomous mode
    public static Command getAutonomousCommand() {

        // Find the currently selected auto command in Shuffleboard and return it
        Command autoCommand = RobotManager.autoCommandChooser.getSelected();
        return autoCommand;
    }

}
