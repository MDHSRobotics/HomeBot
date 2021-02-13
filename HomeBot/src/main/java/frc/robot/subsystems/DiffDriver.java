
package frc.robot.subsystems;

import frc.robot.subsystems.Devices;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//Pathweaver libraries 
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.subsystems.constants.*;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
//

import frc.robot.brains.DiffDriverBrain;
import frc.robot.consoles.Logger;
import frc.robot.sensors.Gyro;
import frc.robot.BotSensors;
import static frc.robot.subsystems.Devices.*;
import frc.robot.BotSubsystems;

// Differential driver subsystem base class
public class DiffDriver extends SubsystemBase {

    // Motor constants
    private final double AUTO_PERIOD_SPEED = 0.5;

    //Odometry class for tracking robot pose (PathWeaver)
    private final DifferentialDriveOdometry m_odometry;

    // The direction of forward/backward via the controller
    public boolean controlStickDirectionFlipped = false;

    // The subsystem devices
    public DifferentialDrive diffDrive;

    public static double distance;

    // Constructor requires device instances
    public DiffDriver(DifferentialDrive diffDrive) {
        this.diffDrive = diffDrive;
        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    // Flip the control direction of the joystick in Y (or Y Left for Xbox thumbsticks)
    public Boolean flipControlStickDirection() {
        Logger.action("Toggling DiffDriver control stick direction...");

        controlStickDirectionFlipped = !controlStickDirectionFlipped;
        if (controlStickDirectionFlipped) {
            Logger.info("DiffDriver control stick direction is now flipped.");
        } else {
            Logger.info("DiffDriver control stick direction is now standard (not flipped).");
        }
        return controlStickDirectionFlipped;
    }

    // Stop all the drive motors
    public void stop() {
        diffDrive.stopMotor();
    }

    // Drive using the arcade method
    public void driveArcade(double xSpeed, double zRotation) {
        // Logger.info("DiffDriver.driveArcade -> xSpeed: " + xSpeed + "; zRotation: " + zRotation);
        diffDrive.arcadeDrive(xSpeed, zRotation);
    }

    // Drive using the tank method
    public void driveTank(double leftSpeed, double rightSpeed) {
        // Logger.info("DiffDriver.driveTank -> Left Speed: " + leftSpeed + "; Right Speed: " + rightSpeed);
        diffDrive.tankDrive(leftSpeed, rightSpeed);
    }

    // Drive forward at a set speed
    public void moveForwardAuto() {
        driveArcade(AUTO_PERIOD_SPEED, AUTO_PERIOD_SPEED); // drive towards heading 0
    }

    // Drive to align the robot to a detected line at the given yaw
    public void driveAlign(double targetYaw) {
        // Get the correction yaw needed to align the Robot with the target yaw
        double yaw = BotSensors.gyro.getYaw();
        double correction = targetYaw - yaw;
        if (correction > 180) correction = correction - 360;
        if (correction < -180) correction = correction + 360;
        Logger.info("DiffDriver -> Gyro -> Target Yaw: " + targetYaw + "; Current Yaw: " + yaw + "; Correction: " + correction);

        // Get the rotation speed to align the robot with the target gyro yaw
        double zRotation = (correction / 180) * DiffDriverBrain.getAlignZSensitivity();
        boolean isCloseEnough = Math.abs(correction) < DiffDriverBrain.getAlignZTolerance();
        if (!isCloseEnough) {
            double alignZSpeedMinimum = DiffDriverBrain.getAlignZSpeedMinimum();
            if (0 < zRotation && zRotation < alignZSpeedMinimum) zRotation = alignZSpeedMinimum;
            if (0 > zRotation && zRotation > -alignZSpeedMinimum) zRotation = -alignZSpeedMinimum;
        }

        Logger.action("DiffDriver -> Drive Tank: " + zRotation);
        diffDrive.arcadeDrive(0, zRotation);
    }

    // TODO: Use this to indicate to the driver that the robot is aligned with the target (lights? Shuffleboard?)
    public boolean isAligned(double targetAngle) {
        boolean straight = Gyro.isYawAligned(targetAngle);
        if (!straight) return false;

        Logger.info("DiffDriver -> Robot is fully aligned!");
        return true;
    }

    //---PathWeaver methods---//

    // returns the heading of the robot
    public double getHeading() {
        return Math.IEEEremainder(BotSensors.gyro.getAngle(), 360) * (PathConstants.kGyroReversed ? -1.0 : 1.0);
    }

    // returns the turn rate of the robot
    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }
    //TODO must change the value 20 to a pathconstant 
    public void resetEncoders() {
        talonSrxDiffWheelFrontLeft.setSelectedSensorPosition(0, 0, 20);
        talonSrxDiffWheelFrontLeft.setSelectedSensorPosition(0, 0, 20);
    }

    // Resets the odometry to the specified pose.
    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
    }

    /**
     * Controls the left and right sides of the drive directly with voltages. Uses
     * setVoltage() rather than set(), as this will automatically compensate
     * for battery “voltage sag” during operation.
     */

    public DifferentialDriveWheelSpeeds getWheelSpeeds(){
        double leftSpeed = (double)(talonSrxDiffWheelFrontLeft.getSelectedSensorVelocity());
        double rightSpeed = (double)(talonSrxDiffWheelFrontRight.getSelectedSensorVelocity());
        return new DifferentialDriveWheelSpeeds(leftSpeed, rightSpeed);
     }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        talonSrxDiffWheelFrontLeft.setVoltage(leftVolts);
        talonSrxDiffWheelFrontRight.setVoltage(-rightVolts);
        diffDrive.feed();
    }

}

