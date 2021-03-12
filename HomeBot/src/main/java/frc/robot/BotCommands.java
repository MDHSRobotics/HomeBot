package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.consoles.Logger;
import frc.robot.commands.deliverer.*;
import frc.robot.subsystems.constants.PathConstants;
import frc.robot.subsystems.constants.AutoConstants;
import frc.robot.commands.diffdriver.*;
import frc.robot.commands.pickupper.*;
import frc.robot.commands.pixyCam.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.sensors.TurnOffLed;
import frc.robot.commands.sensors.TurnOnLed;
import frc.robot.commands.gate.*;

import frc.robot.commands.auto.*;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;


import java.io.IOException;
import java.nio.file.Path;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.sensors.Pixy;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.PIDController;



// Contains singleton instances of all the commands on the robot.
public class BotCommands {

	// DiffDriver
    public static RotateToDpadDirection rotateToDpadDirection;
    public static DriveDiffTank driveDiffTank;
    public static DriveDiffArcade driveDiffArcade;
    public static DriveTankForward driveTankForward;

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
    //Sensors
    public static TurnOffLed turnOffLed;
    public static TurnOnLed turnOnLed;
    public static RotateTowardsTarget rotateTowardsTarget;

    // Pixy
    public static PixyTest pixyTest;
    
    private static Trajectory m_trajectory;

    // Autonomous
    public static AutoDrivePath autoDrivePath;
    public static MoveForwardAuto moveForwardAuto;

    //Gate
    public static ToggleGate toggleGate;
    public static FeedGate feedGate;

    // Initialize all robot commands
    public static void initializeCommands() {
        Logger.setup("Initializing BotCommands...");

        // DiffDriver
        driveDiffTank = new DriveDiffTank(BotSubsystems.diffDriver, BotControllers.xbox);
        driveDiffArcade = new DriveDiffArcade(BotSubsystems.diffDriver, BotControllers.xbox);
        driveTankForward = new DriveTankForward(BotSubsystems.diffDriver, BotControllers.xbox);


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
        rotateTowardsTarget = new RotateTowardsTarget(BotSubsystems.diffDriver);

        // Sensors 
        turnOnLed = new TurnOnLed();
        turnOffLed = new TurnOffLed();

        // Autonomous
        autoDrivePath = new AutoDrivePath(BotSubsystems.diffDriver);
        moveForwardAuto10Feet = new MoveForwardAuto(BotSubsystems.diffDriver, 10.0);
        
        // Pixy
        pixyTest = new PixyTest();

        //Gate
        toggleGate = new ToggleGate(BotSubsystems.gate);
        feedGate = new FeedGate(BotSubsystems.gate);

    }

    // Return the command to run in autonomous mode
    public static Command getAutonomousCommand(char color) {
        // Create a voltage constraint to ensure we don't accelerate too fast
        var autoVoltageConstraint =
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(PathConstants.ksVolts,
                                        PathConstants.kvVoltSecondsPerMeter,
                                        PathConstants.kaVoltSecondsSquaredPerMeter),
                PathConstants.kDriveKinematics,
                10);
    
        // Create config for trajectory (might not need it)
        TrajectoryConfig config =
            new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
                                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(PathConstants.kDriveKinematics)
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint);

    
    Logger.action("Initializing Command: AutoDrivePath...");

        String trajectoryJSON = Pixy.detectPath(color);

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            m_trajectory = trajectory; 
            Logger.info("Trajectory created.");
        }

        catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }
        
    
        RamseteCommand ramseteCommand = new RamseteCommand(
            m_trajectory,
            BotSubsystems.diffDriver::getPose,
            new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
            new SimpleMotorFeedforward(PathConstants.ksVolts,
                                    PathConstants.kvVoltSecondsPerMeter,
                                    PathConstants.kaVoltSecondsSquaredPerMeter),
            PathConstants.kDriveKinematics,
            BotSubsystems.diffDriver::getWheelSpeeds,
            new PIDController(PathConstants.kPDriveVel, 0, 0),
            new PIDController(PathConstants.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            BotSubsystems.diffDriver::tankDriveVolts,
            BotSubsystems.diffDriver
        );
    
        // Reset odometry to the starting pose of the trajectory.
        BotSubsystems.diffDriver.resetOdometry(m_trajectory.getInitialPose());

    
        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> BotSubsystems.diffDriver.tankDriveVolts(0, 0));
    

        
    // Find the currently selected auto command in Shuffleboard and return it
    // Command autoCommand = RobotManager.autoCommandChooser.getSelected();
    // return autoCommand;
    }
}
