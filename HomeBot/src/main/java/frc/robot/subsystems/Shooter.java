
package frc.robot.subsystems;

import java.util.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.brains.LimelightBrain;
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
    private static final double GEAR_RATIO = 4.0; // Gear ratio bewteen Input Shaft : Output Shaft of a gearbox

    // Shuffleboard
    // private double pidkFTop = ShooterBrain.pidkFTopDefault;
    // private double pidkFBottom = ShooterBrain.pidkFBottomDefault;

    private double topVelocity = ShooterBrain.topWheelCurrentVelocityDefault;
    private double bottomVelocity = ShooterBrain.bottomWheelCurrentVelocityDefault;

    private double minTopVelocity = ShooterBrain.topWheelMinVelocityDefault;
    private double maxTopVelocity = ShooterBrain.topWheelMaxVelocityDefault;

    private double minBottomVelocity = ShooterBrain.bottomWheelMinVelocityDefault;
    private double maxBottomVelocity = ShooterBrain.bottomWheelMaxVelocityDefault;

    private double avgTopVelocity = 0;
    private double avgBottomVelocity = 0;
    private int sampleSize = 20;
    private double[] topVelocitySamples = new double[sampleSize];
    private double[] bottomVelocitySamples = new double[sampleSize];

    public Shooter() {
        Logger.setup("Constructing Subsystem: Shooter...");

        if (isReal) {
            // Configure devices
            PIDValues pidTop = new PIDValues(0.00835, 0.0, 0.0, 0.0);
            TalonUtils.configureTalonWithEncoder(talonSrxShooterTopWheel, SENSOR_PHASE_TOP, MOTOR_INVERT_TOP, pidTop);

            PIDValues pidBottom = new PIDValues(0.00835, 0.0, 0.0, 0.0); // Calibrated for 20,000: TpHMS 0.00835
            TalonUtils.configureTalonWithEncoder(talonSrxShooterBottomWheel, SENSOR_PHASE_BOTTOM, MOTOR_INVERT_BOTTOM, pidBottom);
        }
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        // Retrieve PID values
        // pidkFTop = ShooterBrain.getPidkFTop();
        // pidkFBottom = ShooterBrain.getPidkFBottom();

        // Retrieve current top and bottom flywheel velocities
        topVelocity = getTopWheelVelocity();
        bottomVelocity = getBottomWheelVelocity();

        // Post current top and bottom flywheel velocities
        ShooterBrain.setTopWheelCurrentVelocity(topVelocity);
        ShooterBrain.setBottomWheelCurrentVelocity(bottomVelocity);

        // Capture min and max recorded flywheel velocity
        if (topVelocity < minTopVelocity) minTopVelocity = topVelocity;
        if (topVelocity > maxTopVelocity) maxTopVelocity = topVelocity;
        if (bottomVelocity < minBottomVelocity) minBottomVelocity = bottomVelocity;
        if (bottomVelocity > maxBottomVelocity) maxBottomVelocity = bottomVelocity;

        ShooterBrain.setBottomWheelMinVelocity(minBottomVelocity);
        ShooterBrain.setBottomWheelMaxVelocity(maxBottomVelocity);
        ShooterBrain.setTopWheelMinVelocity(minTopVelocity);
        ShooterBrain.setTopWheelMaxVelocity(maxTopVelocity);

        // Calculate average velocities
        for (int i = sampleSize - 2; i > 0; i--){
            topVelocitySamples[i] = topVelocitySamples[i - 1];
            bottomVelocitySamples[i] = bottomVelocitySamples[i - 1];
        }

        topVelocitySamples[0] = topVelocity;
        bottomVelocitySamples[0] = bottomVelocity;

        for (double sample : topVelocitySamples) {
            avgTopVelocity += sample;
        }

        for (double sample : bottomVelocitySamples) {
            avgBottomVelocity += sample;
        }

        avgTopVelocity /= sampleSize;
        avgBottomVelocity /= sampleSize;

        // Post average velocities
        ShooterBrain.setTopWheelAverageVelocity(avgTopVelocity);
        ShooterBrain.setBottomWheelAverageVelocity(avgBottomVelocity);
    }

    // Stop the flywheels
    public void stop() {
        talonSrxShooterBottomWheel.stopMotor();
        talonSrxShooterTopWheel.stopMotor();
    }

    /**
     * Spin the shooter motors at a velocity to hit the target center based on a given distance
     * Note: the distance is currently manually defined in Shuffleboard
     */
    public void shootBasedOnDistance() {
        double shootDistance = LimelightBrain.getDistanceEntry();
        ShooterBrain.setShootDistance(shootDistance);

        // Convert the desired ball velocity (ft/sec) into the required motor speed (Ticks per 100 ms)
        double velocityTPHMS = translateDistanceToTicksViaTable(shootDistance) * GEAR_RATIO;
        ShooterBrain.setShootVelocity(velocityTPHMS);

        double ballSpinVelocity = 0; //ShooterBrain.getBallSpinVelocity();

        talonSrxShooterTopWheel.set(ControlMode.Velocity, velocityTPHMS - ballSpinVelocity);
        talonSrxShooterBottomWheel.set(ControlMode.Velocity, velocityTPHMS + ballSpinVelocity);

        // Update values for Shuffleboard
        ShooterBrain.setShootVelocity(velocityTPHMS);
    }

    /**
     * Spins both flywheels at a specified TpHMS velocity entered in Shuffleboard
     */
    public void shootBasedOnTPHMS() {
        // double velocityTPHMS = ShooterBrain.getTargetTPHMS() * GEAR_RATIO;
        double ballSpinVelocity = ShooterBrain.getBallSpinVelocity() / 2;
        talonSrxShooterTopWheel.set(ControlMode.Velocity, 15000 - ballSpinVelocity);
        talonSrxShooterBottomWheel.set(ControlMode.Velocity, 15000 + ballSpinVelocity);
    }

    public void testEncoders(){
        talonSrxShooterBottomWheel.set(ControlMode.Position, 10000);
        talonSrxShooterTopWheel.set(ControlMode.Position, 10000);
    }
    /**
     * Translate a desired target shoot distance (ft) to a motor velocity in Ticks per 100 ms.
     * The translation is done via a lookup table with values based on shooting experiments.
     * Each entry in the table is the result of testing a particular motor speed (Ticks per 100ms) and
     * then measuring the range that the ball is shot. The lookup table correlates the ball velocity 
     * and motor speed for each test. We can deduce target shoot distances for other ball velocities by 
     * interpolating (or extrapolating) using the values in the lookup table.
     * @param targetDistance
     * @return targetTPHMS
     */

    public static double translateDistanceToTicksViaTable(double targetDistance) {

        // Initialize the lookup table; Key=Velocity in FPS; Value=Motor speed in Ticks/100ms
        SortedMap<Double, Double> luTable = new TreeMap<Double, Double>();

        // The data below is based on shooting experiments conducted on March 11, 2021:
        // (Distance, Ticks per 100ms)
        luTable.put(2.5, 24000.); // untested
        luTable.put(7.5, 22000.);
        luTable.put(10., 20000.);
        luTable.put(12.5, 19500.);
        luTable.put(15., 19800.);
        luTable.put(17.5, 20300.);
        luTable.put(20., 21100.);
        luTable.put(22.5, 21300.);
        luTable.put(25., 21500.);

        boolean firstPass = true;
        double f1 = -99.;
        double t1 = -99.;
        double targetTPHMS = -99.;

        // Iterate over the lookup table, which is sorted based on the key (fps)
        for (Map.Entry<Double, Double> entry : luTable.entrySet()) {
            double f2 = entry.getKey();
            double t2 = entry.getValue();
            Logger.debug(f2 + " (ft) => " + t2 + " (Ticks/100ms");

            // Skip over the first value because we can't compute a slope until we have read at least 2 values
            if (firstPass) {
                firstPass = false;
            }
            else {
                double slope = (t2-t1) / (f2-f1);
                targetTPHMS = t1 + (targetDistance-f1) * slope;

                if (targetDistance <= f2)
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

    /**
     * Get the current top flywheel velocity (TpHMS)
     * @return velocity
     */
    public int getTopWheelVelocity() {
        int velocity = (int)(talonSrxShooterTopWheel.getSelectedSensorVelocity() / GEAR_RATIO);
        return velocity;
    }

    /**
     * Get the current bottom flywheel velocity (TpHMS)
     * @return velocity
     */
    public int getBottomWheelVelocity() {
        int velocity = (int)(talonSrxShooterBottomWheel.getSelectedSensorVelocity() / GEAR_RATIO);
        return velocity;
    }

    //--------------//
    // Shuffleboard //
    //--------------//

    /**
     * Resets the min/max/avg velocity variables in Shuffleboard to their default values
     */
    public void reset() {
        minTopVelocity = ShooterBrain.topWheelMinVelocityDefault;
        maxTopVelocity = ShooterBrain.topWheelMaxVelocityDefault;

        minBottomVelocity = ShooterBrain.bottomWheelMinVelocityDefault;
        maxBottomVelocity = ShooterBrain.bottomWheelMaxVelocityDefault;

        avgTopVelocity = ShooterBrain.topWheelAverageVelocityDefault;
        avgBottomVelocity = ShooterBrain.bottomWheelAverageVelocityDefault;
    }

    //---------//
    // Testing //
    //---------//

    public void testMotor() {
        talonSrxShooterBottomWheel.set(0.3);
        talonSrxShooterTopWheel.set(0.3);
    }

}
