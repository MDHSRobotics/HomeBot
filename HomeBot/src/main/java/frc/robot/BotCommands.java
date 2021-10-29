
package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import java.io.IOException;
import java.nio.file.Path;

import frc.robot.commands.deliverer.*;
import frc.robot.commands.gate.*;
import frc.robot.commands.pickupper.*;
import frc.robot.commands.sensors.TurnOffLed;
import frc.robot.commands.sensors.TurnOnLed;
import frc.robot.commands.shooter.*;
import frc.robot.consoles.Logger;
import frc.robot.sensors.Pixy;
import frc.robot.subsystems.constants.AutoConstants;
import frc.robot.subsystems.constants.PathConstants;
import frc.robot.commands.swervedriver.*;


// Contains singleton instances of all the commands on the robot.
public class BotCommands {

	// SwerveDrive
    public static SwerveDrive swerveDrive;
    public static RotateSwerveWheelsToStart rotateSwerveWheelsToStart;
    public static ResetModulePositions resetModulePositions;

    // Pickup
    public static SpinPickup spinPickup;
    public static StopPickup stopPickup;

    // Delivery
    public static SpinDelivery spinDelivery;
    public static StopDelivery stopDelivery;

    // Gate
    public static ToggleGate toggleGate;
    public static FeedGate feedGate;

    // Shoot
    public static Shoot shoot;
    public static StopShoot stopShoot;
    public static ResetShoot resetShoot;

    // Limelight
    public static TurnOffLed turnOffLed;
    public static TurnOnLed turnOnLed;

    // Pathweaver
    private static Trajectory m_trajectory;


    // Initialize all robot commands
    public static void initializeCommands() {
        Logger.setup("Initializing BotCommands...");

        // Pickup
        spinPickup = new SpinPickup(BotSubsystems.pickup);
        stopPickup = new StopPickup(BotSubsystems.pickup);

        // SwerveDriver
        swerveDrive = new SwerveDrive(BotSubsystems.swerveDriver, BotControllers.xbox);
        rotateSwerveWheelsToStart = new RotateSwerveWheelsToStart(BotSubsystems.swerveDriver);
        resetModulePositions = new ResetModulePositions(BotSubsystems.swerveDriver);

        // Delivery
        spinDelivery = new SpinDelivery(BotSubsystems.delivery);
        stopDelivery = new StopDelivery(BotSubsystems.delivery);

        // Gate
        toggleGate = new ToggleGate(BotSubsystems.gate);
        feedGate = new FeedGate(BotSubsystems.gate);

        // Shooter
        shoot = new Shoot(BotSubsystems.shooter);
        stopShoot = new StopShoot(BotSubsystems.shooter);
        resetShoot = new ResetShoot(BotSubsystems.shooter);

        // Limelight
        turnOnLed = new TurnOnLed();
        turnOffLed = new TurnOffLed();
    }
/*
    public static Command getPathweaverCommand(String pathweaverPath) {
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(pathweaverPath);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            m_trajectory = trajectory;
            Logger.info("Trajectory created.");
        }
        catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + pathweaverPath, ex.getStackTrace());
        }

        RamseteCommand ramseteCommand = new RamseteCommand(m_trajectory, BotSubsystems.diffDriver::getPose,
                new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
                new SimpleMotorFeedforward(PathConstants.ksVolts, PathConstants.kvVoltSecondsPerMeter,
                        PathConstants.kaVoltSecondsSquaredPerMeter),
                PathConstants.kDriveKinematics, BotSubsystems.diffDriver::getWheelSpeeds,
                new PIDController(PathConstants.kPDriveVel, 0, 0), new PIDController(PathConstants.kPDriveVel, 0, 0),
                // RamseteCommand passes volts to the callback
                BotSubsystems.diffDriver::tankDriveVolts, BotSubsystems.diffDriver);

        // Reset odometry to the starting pose of the trajectory.
        BotSubsystems.diffDriver.resetOdometry(m_trajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> BotSubsystems.diffDriver.tankDriveVolts(0, 0)); 
    }

    // Return the command to run in autonomous mode (AutoNav)
    public static Command getAutonomousCommand(String game) {
        String trajectoryJSON = "";
        if (game.equals("barrel")) {
            trajectoryJSON = "/home/lvuser/deploy/paths/BarrelRacing.wpilib.json";
        } else if (game.equals("bounce")) {
            trajectoryJSON = "/home/lvuser/deploy/paths/Bounce.wpilib.json";
        } else if (game.equals("slalom")) {
            trajectoryJSON = "/home/lvuser/deploy/paths/Slalom.wpilib.json";
        }

        return getPathweaverCommand(trajectoryJSON);
    }

    // Return the command to run in autonomous mode (Galactic Search)
    public static Command getAutonomousCommand(char color) {
        String trajectoryJSON = "/home/lvuser/deploy/paths/" + Pixy.detectPath(color);

        return getPathweaverCommand(trajectoryJSON);
    }*/

}
