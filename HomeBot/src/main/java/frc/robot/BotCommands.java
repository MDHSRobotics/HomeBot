package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.constants.PathConstants;
import frc.robot.subsystems.constants.AutoConstants;
import frc.robot.commands.diffdriver.*;
import frc.robot.subsystems.DiffDriver;
import frc.robot.commands.auto.*;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import java.io.IOException;
import java.nio.file.Path;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import java.nio.file.FileSystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.BotSubsystems;


// Contains singleton instances of all the commands on the robot.
public class BotCommands {
    // DiffDriver
    public static RotateToDpadDirection rotateToDpadDirection;
    public static DriveDiffTank driveDiffTank;
    private static Trajectory paths;

    // Autonomous
    public static AutoDrivePath autoDrivePath;

    // Initialize all robot commands
    public static void initializeCommands() {
        Logger.setup("Initializing BotCommands...");

        // DiffDriver
        driveDiffTank = new DriveDiffTank(BotSubsystems.diffDriver, BotControllers.xbox);

        // Autonomous
        autoDrivePath = new AutoDrivePath(BotSubsystems.diffDriver);
    }

    // Return the command to run in autonomous mode
    public Command getAutonomousCommand() {
        // Create a voltage constraint to ensure we don't accelerate too fast
        var autoVoltageConstraint =
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(PathConstants.ksVolts,
                                        PathConstants.kvVoltSecondsPerMeter,
                                        PathConstants.kaVoltSecondsSquaredPerMeter),
                PathConstants.kDriveKinematics,
                10);
    
        // Create config for trajectory
        TrajectoryConfig config =
            new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
                                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(PathConstants.kDriveKinematics)
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint);

    
    Logger.action("Initializing Command: AutoDrivePath...");

        String trajectoryJSON = "paths/Practice.wpilib.json";

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            paths = trajectory; 
            Logger.info("Trajectory created.");
        }

        catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }
        
    
        RamseteCommand ramseteCommand = new RamseteCommand(
            paths,
            BotSubsystems.diffDriver::getPose,
            new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
            new SimpleMotorFeedforward(PathConstants.ksVolts,
                                    PathConstants.kvVoltSecondsPerMeter,
                                    PathConstants.kaVoltSecondsSquaredPer;Meter),
            PathConstants.kDriveKinematics,
            BotSubsystems.diffDriver::getWheelSpeeds,
            new PIDController(PathConstants.kPDriveVel, 0, 0),
            new PIDController(PathConstants.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            BotSubsystems.diffDriver::tankDriveVolts,
            BotSubsystems.diffDriver
        );
    
        // Reset odometry to the starting pose of the trajectory.
        BotSubsystems.diffDriver.resetOdometry(paths.getInitialPose());

    
        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> BotSubsystems.diffDriver.tankDriveVolts(0, 0));
    }

    return autoDrivePath;

    // Find the currently selected auto command in Shuffleboard and return it
    // Command autoCommand = RobotManager.autoCommandChooser.getSelected();
    // return autoCommand;
}

}
