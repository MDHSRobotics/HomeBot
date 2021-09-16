
package frc.robot.brains;

import edu.wpi.first.networktables.NetworkTableEntry;

// This class contains all the shared NetworkTableEntries for the Shooter Subsystem,
// their default values, and methods for retrieving their current values.
public class ShooterBrain {

    //----------------//
    // Default Values //
    //----------------//

    public static double pidkFTopDefault = 0.;
    public static double pidkFBottomDefault = 0.;

    public static double topWheelCurrentVelocityDefault = 0.;
    public static double bottomWheelCurrentVelocityDefault = 0.;

    public static double bottomWheelMaxVelocityDefault = 0.;
    public static double bottomWheelMinVelocityDefault = 1000000.;
    public static double bottomWheelAverageVelocityDefault = 0;
    public static double topWheelMaxVelocityDefault = 0.;
    public static double topWheelMinVelocityDefault = 1000000.;
    public static double topWheelAverageVelocityDefault = 0.;

    // The ideal shooting distance is 11.38' which is where the apex is at the target center
    public static double shootDistanceDefault = 0.;
    public static double shootVelocityDefault = 0.;
    public static double ballSpinVelocityDefault = 0.;

    //---------------------//
    // NetworkTableEntries //
    //---------------------//
    
    public static NetworkTableEntry pidkFTopEntry;
    public static NetworkTableEntry pidkFBottomEntry;

    public static NetworkTableEntry bottomWheelCurrentVelocityEntry;
    public static NetworkTableEntry topWheelCurrentVelocityEntry;

    public static NetworkTableEntry bottomWheelMaxVelocityEntry;
    public static NetworkTableEntry bottomWheelMinVelocityEntry;
    public static NetworkTableEntry bottomWheelAverageVelocityEntry;
    public static NetworkTableEntry topWheelMaxVelocityEntry;
    public static NetworkTableEntry topWheelMinVelocityEntry;
    public static NetworkTableEntry topWheelAverageVelocityEntry;

    public static NetworkTableEntry shootDistanceEntry;
    public static NetworkTableEntry shootVelocityEntry;
    public static NetworkTableEntry shootBallSpinVelocityEntry;

    //---------//
    // Setters //
    //---------//

    public static void setTopWheelCurrentVelocity(double value) {
        topWheelCurrentVelocityEntry.setDouble(value);
    }

    public static void setBottomWheelCurrentVelocity(double value) {
        bottomWheelCurrentVelocityEntry.setDouble(value);
    }

    public static void setBottomWheelMaxVelocity(double value) {
        bottomWheelMaxVelocityEntry.setDouble(value);
    }

    public static void setBottomWheelMinVelocity(double value) {
        bottomWheelMinVelocityEntry.setDouble(value);
    }

    public static void setBottomWheelAverageVelocity(double value) {
        bottomWheelAverageVelocityEntry.setDouble(value);
    }

    public static void setTopWheelMaxVelocity(double value) {
        topWheelMaxVelocityEntry.setDouble(value);
    }

    public static void setTopWheelMinVelocity(double value) {
        topWheelMinVelocityEntry.setDouble(value);
    }

    public static void setTopWheelAverageVelocity(double value) {
        topWheelAverageVelocityEntry.setDouble(value);
    }

    public static void setShootVelocity(double value) {
        shootVelocityEntry.setDouble(value);
    }

    public static void setShootDistance(double value){
        shootDistanceEntry.setDouble(value);
    }
    //---------//
    // Getters //
    //---------//

    public static double getPidkFTop() {
        return pidkFTopEntry.getDouble(pidkFTopDefault);
    }

    public static double getPidkFBottom() {
        return pidkFBottomEntry.getDouble(pidkFBottomDefault);
    }

    public static double getTargetTPHMS() {
        return shootVelocityEntry.getDouble(shootVelocityDefault);
    }

    public static double getShootDistance() {
        return shootDistanceEntry.getDouble(shootDistanceDefault);
    }
    public static double getBallSpinVelocity() {
        return shootBallSpinVelocityEntry.getDouble(ballSpinVelocityDefault);
    }
   
}
