
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.brains.DiffDriverBrain;
import frc.robot.consoles.Logger;
import frc.robot.sensors.Gyro;
import frc.robot.subsystems.constants.*;
import frc.robot.subsystems.utils.EncoderUtils;
import frc.robot.subsystems.utils.PIDValues;
import frc.robot.subsystems.utils.TalonUtils;
import frc.robot.BotSensors;

import static frc.robot.subsystems.constants.TalonConstants.*;
import static frc.robot.subsystems.Devices.*;
import static frc.robot.RobotManager.isReal;


// Differential driver subsystem base class
public class DiffDriver extends SubsystemBase {

    // Motor constants
    private final double AUTO_PERIOD_SPEED = 0.5;
    private final double SECONDS_FROM_NEUTRAL_TO_FULL = 0.1;

    //Odometry class for tracking robot pose (PathWeaver)
    private final DifferentialDriveOdometry m_odometry;

    // The direction of forward/backward via the controller
    public boolean controlStickDirectionFlipped = false;

    public static double distance;

    // Constructor requires device instances
    public DiffDriver() {
        TalonUtils.configureBaseTalon(talonFxDiffWheelFrontLeft, true);
        TalonUtils.configureBaseTalon(talonFxDiffWheelRearLeft, true);
        TalonUtils.configureBaseTalon(talonFxDiffWheelFrontRight, true);
        TalonUtils.configureBaseTalon(talonFxDiffWheelRearRight, true);

        PIDValues pidLeft = new PIDValues(0.0, .8, 0.0, 0.0);
        PIDValues pidRight = new PIDValues(0.0, .8, 0.0, 0.0);
        TalonUtils.configureTalonWithEncoder(talonFxDiffWheelFrontLeft, false, pidLeft);
        TalonUtils.configureTalonWithEncoder(talonFxDiffWheelFrontRight, true, pidRight);

        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

        if (isReal) {
            // Configure the TalonFX devices used for DiffDriver
            TalonUtils.configureBaseTalonMasterFollower(talonFxDiffWheelFrontLeft, talonFxDiffWheelRearLeft);
            TalonUtils.configureBaseTalonMasterFollower(talonFxDiffWheelFrontRight, talonFxDiffWheelRearRight);
            talonFxDiffWheelFrontLeft.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            talonFxDiffWheelFrontRight.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            talonFxDiffWheelRearLeft.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            talonFxDiffWheelRearRight.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
        }
    }
    
    // This method will be called once per scheduler run
    // Might change constant 6 depending on wheel's diameter 
    @Override
    public void periodic() {
        double wheelCircumference = .1524 * Math.PI;
        double rightDistance = EncoderUtils.translateTicksToDistance(talonFxDiffWheelFrontRight.getSelectedSensorPosition(0), wheelCircumference);
        double leftDistance = EncoderUtils.translateTicksToDistance(talonFxDiffWheelFrontLeft.getSelectedSensorPosition(0), wheelCircumference);
        
        m_odometry.update(BotSensors.gyro.getRotation2d(), leftDistance, rightDistance);
        Logger.debug("Odometry X: " + m_odometry.getPoseMeters().getTranslation().getX());
        Logger.debug("Odometry Y: " + m_odometry.getPoseMeters().getTranslation().getY());
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
        diffDriveTalonFX.stopMotor();
    }

    // Drive using the arcade method
    public void driveArcade(double xSpeed, double zRotation) {
        // Logger.info("DiffDriver.driveArcade -> xSpeed: " + xSpeed + "; zRotation: " + zRotation);
        diffDriveTalonFX.arcadeDrive(xSpeed, zRotation);
    }

    // Drive using the tank method
    public void driveTank(double leftSpeed, double rightSpeed) {
        // Logger.info("DiffDriver.driveTank -> Left Speed: " + leftSpeed + "; Right Speed: " + rightSpeed);
        diffDriveTalonFX.tankDrive(leftSpeed, rightSpeed);
    }

    public void driveTankStraight(double leftSpeed, double rightSpeed) {
        // Logger.info("DiffDriver.driveTank -> Left Speed: " + leftSpeed + "; Right Speed: " + rightSpeed);
        diffDriveTalonFX.tankDrive(leftSpeed, rightSpeed);
    }

    // Drive forward at a set speed
    public void moveForwardAuto() {
        driveArcade(AUTO_PERIOD_SPEED, AUTO_PERIOD_SPEED); // drive towards heading 0
    }
    
    public void moveForwardAuto(double feet) {
        Logger.info("Rotating wheel 5 times...");
        // double ticks = EncoderUtils.translateDistanceToTicks(feet, WHEEL_DIAMETER, GEAR_RATIO);
        talonFxDiffWheelFrontLeft.setSelectedSensorPosition(0);
        talonFxDiffWheelFrontRight.setSelectedSensorPosition(0);

        int leftPosition = getPositionLeft();
        int rightPosition = getPositionRight();

        //if (leftPosition != 0) {
            Logger.debug("MoveForwardAutoInitialize: Left Wheel Position --> " + leftPosition);
        //}

        //if (rightPosition != 0) {
            Logger.debug("MoveForwardAutoInitialize: Right Wheel Position --> " + rightPosition);
        //}

        talonFxDiffWheelFrontLeft.set(ControlMode.Position, 40000);
        talonFxDiffWheelFrontRight.set(ControlMode.Position, -40000);
        Logger.info("Finished...");

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
        diffDriveTalonFX.arcadeDrive(0, zRotation);
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

    public double getTurnRate(){
        return BotSensors.gyro.getRate();
    }

    // returns the turn rate of the robot
    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    //TODO must change the value 20 to a pathconstant value 
    public void resetEncoders() {
        talonFxDiffWheelFrontLeft.setSelectedSensorPosition(0, 0, 20);
        talonFxDiffWheelFrontRight.setSelectedSensorPosition(0, 0, 20);
    }

    // Resets the odometry to the specified pose.
    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
    }

    public void resetGyro(){
        BotSensors.gyro.reset();
    }

    // Controls the left and right sides of the drive directly with voltages.
    // Uses setVoltage() rather than set(), as this will automatically compensate for battery voltage sag during operation.
    public DifferentialDriveWheelSpeeds getWheelSpeeds(){
        double leftSpeed = (double)(talonFxDiffWheelFrontLeft.getSelectedSensorVelocity());
        double rightSpeed = (double)(talonFxDiffWheelFrontRight.getSelectedSensorVelocity());
        return new DifferentialDriveWheelSpeeds(leftSpeed, rightSpeed);
     }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        talonFxDiffWheelFrontLeft.setVoltage(leftVolts);
        talonFxDiffWheelFrontRight.setVoltage(rightVolts);
        diffDriveTalonFX.feed();
    }

    public void feed() {
        diffDriveTalonFX.feed();
    }
    private double encoderConstant = (1 / 4) * (1 / 2048);

    public int getPositionLeft() {
        return talonFxDiffWheelFrontLeft.getSelectedSensorPosition(0) * encoderConstant;
    }

    public int getPositionRight() {
        return talonFxDiffWheelFrontRight.getSelectedSensorPosition(0) * encoderConstant;
    }
 


    // Test all the drive motors
    public void test(int testNumber) {
        diffDriveTalonFX.feed();
        if (testNumber == 1) {
            Logger.debug("Test1");
            talonFxDiffWheelFrontLeft.set(.5);
            talonFxDiffWheelFrontRight.set(.5);
        }
        else if (testNumber == 2) {
            Logger.debug("Test2");
            talonFxDiffWheelFrontLeft.setVoltage(6);
            talonFxDiffWheelFrontRight.setVoltage(6);
        }
        else if (testNumber == 3) {
            Logger.debug("Test3");
            talonFxDiffWheelFrontLeft.set(ControlMode.Position, 50000);
            talonFxDiffWheelFrontRight.set(ControlMode.Position, 50000);
        }
        else if (testNumber == 4) {
            Logger.debug("Test4");
            talonFxDiffWheelFrontLeft.set(ControlMode.Position, 0);
            talonFxDiffWheelFrontRight.set(ControlMode.Position, 0);
        }
        else if (testNumber == 5) {
            Logger.debug("Test5");
            resetEncoders();
        }
    }

}
