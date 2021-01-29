
package frc.robot.subsystems;

import java.util.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.brains.RobotBrain;
import frc.robot.brains.ShooterBrain;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.utils.*;

import static frc.robot.subsystems.Devices.talonSrxShooterBottomWheel;
import static frc.robot.subsystems.Devices.talonSrxShooterTopWheel;
import static frc.robot.RobotManager.isReal;

// Shooter subsystem, for shooting balls with two flywheels.
public class Shooter extends SubsystemBase {

    // Encoder constants
    private static final boolean SENSOR_PHASE_BOTTOM = true;
    private static final boolean MOTOR_INVERT_BOTTOM = false;

    private static final boolean SENSOR_PHASE_TOP = true;
    private static final boolean MOTOR_INVERT_TOP = false;

    // Position constants
    private static final double GEAR_RATIO = 4.0;

    // Shuffleboard
    private double topVelocity = ShooterBrain.shootTopWheelCurrentVelocityDefault;
    private double bottomVelocity = ShooterBrain.shootBottomWheelCurrentVelocityDefault;

    private double minTopVelocity = ShooterBrain.shootTopWheelMinVelocityDefault;
    private double maxTopVelocity = ShooterBrain.shootTopWheelMaxVelocityDefault;

    private double minBottomVelocity = ShooterBrain.shootBottomWheelMinVelocityDefault;
    private double maxBottomVelocity = ShooterBrain.shootBottomWheelMaxVelocityDefault;

    private double avgTopVelocity = 0;
    private double avgBottomVelocity = 0;
    private int sampleSize = 5;
    private double[] averageTopVelocity = new double[sampleSize];
    private double[] averageBottomVelocity = new double[sampleSize];

    public Shooter() {
        Logger.setup("Constructing Subsystem: Shooter...");

        if (isReal) {
            // Configure devices
            PIDValues pidBottom = new PIDValues(0.087, 0.0, 0.0, 0.0);
            TalonUtils.configureTalonWithEncoder(talonSrxShooterBottomWheel, SENSOR_PHASE_BOTTOM, MOTOR_INVERT_BOTTOM, pidBottom);

            PIDValues pidTop = new PIDValues(0.0885, 0.0, 0.0, 0.0); // kF should be 0.0015 greater than the bottom kF
            TalonUtils.configureTalonWithEncoder(talonSrxShooterTopWheel, SENSOR_PHASE_TOP, MOTOR_INVERT_TOP, pidTop);
        }
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        topVelocity = getTopWheelVelocity();
        bottomVelocity = getBottomWheelVelocity();

        // Current Velocity
        ShooterBrain.setTopWheelCurrentVelocity(topVelocity);
        ShooterBrain.setBottomWheelCurrentVelocity(bottomVelocity);

        // Min and Max Velocity
        if (topVelocity < minTopVelocity) minTopVelocity = topVelocity;
        if (topVelocity > maxTopVelocity) maxTopVelocity = topVelocity;
        if (bottomVelocity < minBottomVelocity) minBottomVelocity = bottomVelocity;
        if (bottomVelocity > maxBottomVelocity) maxBottomVelocity = bottomVelocity;

        ShooterBrain.setBottomWheelMinVelocity(minBottomVelocity);
        ShooterBrain.setBottomWheelMaxVelocity(maxBottomVelocity);
        ShooterBrain.setTopWheelMinVelocity(minTopVelocity);
        ShooterBrain.setTopWheelMaxVelocity(maxTopVelocity);

        // Average Velocity
        for (int i = sampleSize - 2; i > 0; i--){
            averageTopVelocity[i] = averageTopVelocity[i - 1];
            averageBottomVelocity[i] = averageBottomVelocity[i - 1];
        }

        averageTopVelocity[0] = topVelocity;
        averageBottomVelocity[0] = bottomVelocity;

        for (double sample : averageTopVelocity) {
            avgTopVelocity += sample;
        }

        for (double sample : averageBottomVelocity) {
            avgBottomVelocity += sample;
        }

        avgTopVelocity /= sampleSize;
        avgBottomVelocity /= sampleSize;

        ShooterBrain.setTopWheelAverageVelocity(avgBottomVelocity);
        ShooterBrain.setBottomWheelAverageVelocity(avgTopVelocity);
    }

    // Stop the shooter
    public void stop() {
        talonSrxShooterBottomWheel.stopMotor();
        talonSrxShooterTopWheel.stopMotor();
    }

    // Spin the shooter motors at a velocity to hit the target center based on a given distance
    // Note: the distance is currently manually defined in Shuffleboard
    public void shootBasedOnDistance() {
        double sHeight = RobotBrain.shooterHeightFeetDefault;
        double sAngle = RobotBrain.shooterAngleDegreesDefault;
        double fHeight = RobotBrain.fieldTargetHeightFeet;

        double distanceFeet = ShooterBrain.getShootDistance();
        double numerator = 32.174 * Math.pow(distanceFeet, 2);
        double denominator = 2 * (distanceFeet * Math.sin(sAngle) * Math.cos(sAngle)
                - (fHeight - sHeight) * Math.pow(Math.cos(sAngle), 2));
        double velocityFPS = Math.sqrt(numerator) / Math.sqrt(denominator);

        // Convert the desired ball velocity (ft/sec) into the required motor speed (Ticks per 100 ms)
        double velocityTPHMS = translateFPSToTicksViaTable(velocityFPS);
        double velocityTPHMSOffsetTop = ShooterBrain.getShooterVelocityTPHMSOffsetTop();
        double velocityTPHMSOffsetBottom = ShooterBrain.getShooterVelocityTPHMSOffsetBottom();

        talonSrxShooterTopWheel.set(ControlMode.Velocity, velocityTPHMS + velocityTPHMSOffsetTop);
        talonSrxShooterBottomWheel.set(ControlMode.Velocity, velocityTPHMS + velocityTPHMSOffsetBottom);

        // Update values for Shuffleboard
        ShooterBrain.setTargetFPS(velocityFPS);
        ShooterBrain.setTargetTPHMS(velocityTPHMS);
    }

    public void shootBasedOnVelocity() {
        double velocityFPS = ShooterBrain.getTargetFPS();
        double velocityTPHMS = translateFPSToTicksViaTable(velocityFPS);
        talonSrxShooterBottomWheel.set(ControlMode.Velocity, velocityTPHMS);
        talonSrxShooterTopWheel.set(ControlMode.Velocity, velocityTPHMS);

        ShooterBrain.setTargetTPHMS(velocityTPHMS);
    }

    public void shootBasedOnTPHMS() {
        double velocityTPHMS = ShooterBrain.getTargetTPHMS() / GEAR_RATIO;
        talonSrxShooterBottomWheel.set(velocityTPHMS);
        talonSrxShooterTopWheel.set(velocityTPHMS);
    }

    // Translate a desired target velocity in feet per second to a motor speed in Ticks per 100 ms.
    // The translation is done via a lookup table with values based on shooting experiments.
    // Each entry in the table is the result of testing a particular motor speed (Ticks per 100ms) and
    // then measuring the range that the ball is shot. From the range we can calculate the effective
    // velocity of the ball (ft/sec). The lookup table correlates the ball velocity and motor speed
    // for each test.  We can deduce the motor speed for other ball velocities by interpolating
    // (or extrapolating) using the values in the lookup table.

    public static double translateFPSToTicksViaTable(double targetFPS) {

        // Initialize the lookup table; Key=Velocity in FPS; Value=Motor speed in Ticks/100ms
        SortedMap<Double, Double> luTable = new TreeMap<Double, Double>();

        // The data below is based on shooting experiments conducted on March 5, 2020:
        // (Feet per second, Ticks per 100ms)
        luTable.put(22.676, 4671.); // 8
        luTable.put(19.753, 4275.); // 10
        luTable.put(27.878, 5425.); // 7
        luTable.put(25.117, 4946.); // 7.5
        luTable.put(33.766, 6402.); // 6.5
        luTable.put(30.830, 5814.); // 6.75

        boolean firstPass = true;
        double f1 = -99.;
        double t1 = -99.;
        double targetTPHMS = -99.;

        // Iterate over the lookup table, which is sorted based on the key (fps)
        for (Map.Entry<Double, Double> entry : luTable.entrySet()) {
            double f2 = entry.getKey();
            double t2 = entry.getValue();
            Logger.debug(f2 + " (ft/sec) => " + t2 + " (Ticks/100ms");

            // Skip over the first value because we can't compute a slope until we have read at least 2 values
            if (firstPass) {
                firstPass = false;
            }
            else {
                double slope = (t2-t1) / (f2-f1);
                targetTPHMS = t1 + (targetFPS-f1) * slope;

                if (targetFPS <= f2)
                    break;
            }

            f1 = f2;
            t1 = t2;
        }

        return targetTPHMS;
    }

    //---------//
    // Getters //
    //---------//

    // Get the current Shooter BottomWheel motor velocity
    public int getBottomWheelVelocity() {
        int velocity = talonSrxShooterBottomWheel.getSelectedSensorVelocity() / 10;
        return velocity;
    }

    // Get the current Shooter TopWheel motor velocity
    public int getTopWheelVelocity() {
        int velocity = talonSrxShooterTopWheel.getSelectedSensorVelocity() / 10;
        return velocity;
    }

    //--------------//
    // Shuffleboard //
    //--------------//

    // Resets the min/max velocity capture variables to their default values
    public void reset() {
        minTopVelocity = ShooterBrain.shootTopWheelMinVelocityDefault;
        maxTopVelocity = ShooterBrain.shootTopWheelMaxVelocityDefault;

        minBottomVelocity = ShooterBrain.shootBottomWheelMinVelocityDefault;
        maxBottomVelocity = ShooterBrain.shootBottomWheelMaxVelocityDefault;

        avgTopVelocity = ShooterBrain.shootTopWheelAverageVelocityDefault;
        avgBottomVelocity = ShooterBrain.shootBottomWheelAverageVelocityDefault;
    }

    //---------//
    // Testing //
    //---------//

    public void testMotor() {
        talonSrxShooterBottomWheel.set(0.3);
        talonSrxShooterTopWheel.set(0.3);
    }

}
