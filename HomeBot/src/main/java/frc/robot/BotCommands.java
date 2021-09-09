
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

import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import java.util.List;
import edu.wpi.first.wpilibj.geometry.Translation2d;

import java.io.IOException;
import java.nio.file.Path;

import frc.robot.commands.auto.*;
import frc.robot.commands.deliverer.*;
import frc.robot.commands.diffdriver.*;
import frc.robot.commands.gate.*;
import frc.robot.commands.pickupper.*;
import frc.robot.commands.pixyCam.*;
import frc.robot.commands.sensors.TurnOffLed;
import frc.robot.commands.sensors.TurnOnLed;
import frc.robot.commands.shooter.*;
import frc.robot.consoles.Logger;
import frc.robot.sensors.Pixy;
import frc.robot.subsystems.constants.AutoConstants;
import frc.robot.subsystems.constants.PathConstants;


// Contains singleton instances of all the commands on the robot.
public class BotCommands {

    // DiffDriver
    public static DriveDiffArcade driveDiffArcade;
    public static DriveDiffTank driveDiffTank;
    public static DriveTankForward driveTankForward;
    public static RotateToDpadDirection rotateToDpadDirection;
    public static RotateTowardsTarget rotateTowardsTarget;
    public static TestDiffDrive test1DiffDrive;
    public static TestDiffDrive test2DiffDrive;
    public static TestDiffDrive test3DiffDrive;
    public static TestDiffDrive test4DiffDrive;
    public static TestDiffDrive test5DiffDrive;

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

    // Pixy
    public static PixyTest pixyTest;

    // Autonomous
    public static AutoDrivePath autoDrivePath;
    public static MoveForwardAuto moveForwardAuto10Feet;

    // Pathweaver
    private static Trajectory m_trajectory;


    // Initialize all robot commands
    public static void initializeCommands() {
        Logger.setup("Initializing BotCommands...");

        // DiffDriver
        driveDiffTank = new DriveDiffTank(BotSubsystems.diffDriver, BotControllers.xbox);
        driveDiffArcade = new DriveDiffArcade(BotSubsystems.diffDriver, BotControllers.xbox);
        driveTankForward = new DriveTankForward(BotSubsystems.diffDriver, BotControllers.xbox);
        rotateTowardsTarget = new RotateTowardsTarget(BotSubsystems.diffDriver);
        test1DiffDrive = new TestDiffDrive(BotSubsystems.diffDriver, 1);
        test2DiffDrive = new TestDiffDrive(BotSubsystems.diffDriver, 2);
        test3DiffDrive = new TestDiffDrive(BotSubsystems.diffDriver, 3);
        test4DiffDrive = new TestDiffDrive(BotSubsystems.diffDriver, 4);
        test5DiffDrive = new TestDiffDrive(BotSubsystems.diffDriver, 5);

        // Pickup
        spinPickup = new SpinPickup(BotSubsystems.pickup);
        stopPickup = new StopPickup(BotSubsystems.pickup);

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

        // Pixy
        pixyTest = new PixyTest();

        // Autonomous
        autoDrivePath = new AutoDrivePath(BotSubsystems.diffDriver);
        moveForwardAuto10Feet = new MoveForwardAuto(BotSubsystems.diffDriver, 10.0);
    }

    public static Command getPathweaverCommand(String pathweaverPath) {
        Logger.info("Getting Pathweaver Command for: " + pathweaverPath);

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
        } else if (game.equals("straight")) {
            trajectoryJSON = "/home/lvuser/deploy/paths/straight.wpilib.json";
        } else if (game.equals("test")) {
            trajectoryJSON = "/home/lvuser/deploy/paths/test.wpilib.json";
        }
        return getPathweaverCommand(trajectoryJSON);
    }

    // Return the command to run in autonomous mode (Galactic Search)
    public static Command getAutonomousCommand(char color) {
        String trajectoryJSON = "/home/lvuser/deploy/paths/" + Pixy.detectPath(color);

        return getPathweaverCommand(trajectoryJSON);
    }

    public static Command getAutonomousCommandExample() {

        // Create a voltage constraint to ensure we don't accelerate too fast
        var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(PathConstants.ksVolts, PathConstants.kvVoltSecondsPerMeter,
                        PathConstants.kaVoltSecondsSquaredPerMeter),
                PathConstants.kDriveKinematics, 10);

        // Create config for trajectory
        TrajectoryConfig config = new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                        // Add kinematics to ensure max speed is actually obeyed
                        .setKinematics(PathConstants.kDriveKinematics)
                        // Apply the voltage constraint
                        .addConstraint(autoVoltageConstraint);

        // An example trajectory to follow. All units in meters.
        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)),
                // Pass config
                config);

        RamseteCommand ramseteCommand = new RamseteCommand(exampleTrajectory, BotSubsystems.diffDriver::getPose,
                new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
                new SimpleMotorFeedforward(PathConstants.ksVolts, PathConstants.kvVoltSecondsPerMeter,
                        PathConstants.kaVoltSecondsSquaredPerMeter),
                PathConstants.kDriveKinematics, BotSubsystems.diffDriver::getWheelSpeeds,
                new PIDController(PathConstants.kPDriveVel, 0, 0), new PIDController(PathConstants.kPDriveVel, 0, 0),
                // RamseteCommand passes volts to the callback
                BotSubsystems.diffDriver::tankDriveVolts, BotSubsystems.diffDriver);

        // Reset odometry to the starting pose of the trajectory.
        BotSubsystems.diffDriver.resetOdometry(exampleTrajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> BotCommands.driveDiffArcade.schedule());
    }

}
