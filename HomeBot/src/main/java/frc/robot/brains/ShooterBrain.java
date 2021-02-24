
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

    public static double shootTopWheelCurrentVelocityDefault = 0.;
    public static double shootBottomWheelCurrentVelocityDefault = 0.;

    public static double shootBottomWheelMaxVelocityDefault = 0.;
    public static double shootBottomWheelMinVelocityDefault = 1000000.;
    public static double shootBottomWheelAverageVelocityDefault = 0;
    public static double shootTopWheelMaxVelocityDefault = 0.;
    public static double shootTopWheelMinVelocityDefault = 1000000.;
    public static double shootTopWheelAverageVelocityDefault = 0;

    // The ideal shooting distance is 11.38' which is where the apex is at the target center
    public static double shootDistanceDefault = 11.38;
    public static double shootTargetFPSDefault = 0.;
    public static double shootTargetTPHMSDefault = 0.;
    public static double shootBallSpinVelocityDefault = 0.;

    //---------------------//
    // NetworkTableEntries //
    //---------------------//

    public static NetworkTableEntry pidkFTopEntry;
    public static NetworkTableEntry pidkFBottomEntry;

    public static NetworkTableEntry shootBottomWheelCurrentVelocityEntry;
    public static NetworkTableEntry shootTopWheelCurrentVelocityEntry;

    public static NetworkTableEntry shootBottomWheelMaxVelocityEntry;
    public static NetworkTableEntry shootBottomWheelMinVelocityEntry;
    public static NetworkTableEntry shootBottomWheelAverageVelocityEntry;
    public static NetworkTableEntry shootTopWheelMaxVelocityEntry;
    public static NetworkTableEntry shootTopWheelMinVelocityEntry;
    public static NetworkTableEntry shootTopWheelAverageVelocityEntry;

    public static NetworkTableEntry shootDistanceEntry;
    public static NetworkTableEntry shootTargetTPHMSEntry;
    public static NetworkTableEntry shootBallSpinVelocityEntry;

    //---------//
    // Setters //
    //---------//

    public static void setTopWheelCurrentVelocity(double value) {
        shootTopWheelCurrentVelocityEntry.setDouble(value);
    }

    public static void setBottomWheelCurrentVelocity(double value) {
        shootBottomWheelCurrentVelocityEntry.setDouble(value);
    }

    public static void setBottomWheelMaxVelocity(double value) {
        shootBottomWheelMaxVelocityEntry.setDouble(value);
    }

    public static void setBottomWheelMinVelocity(double value) {
        shootBottomWheelMinVelocityEntry.setDouble(value);
    }

    public static void setBottomWheelAverageVelocity(double value) {
        shootBottomWheelAverageVelocityEntry.setDouble(value);
    }

    public static void setTopWheelMaxVelocity(double value) {
        shootTopWheelMaxVelocityEntry.setDouble(value);
    }

    public static void setTopWheelMinVelocity(double value) {
        shootTopWheelMinVelocityEntry.setDouble(value);
    }

    public static void setTopWheelAverageVelocity(double value) {
        shootTopWheelAverageVelocityEntry.setDouble(value);
    }

    public static void setTargetTPHMS(double value) {
        shootTargetTPHMSEntry.setDouble(value);
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
        return shootTargetTPHMSEntry.getDouble(shootTargetTPHMSDefault);
    }

    public static double getShootDistance() {
        return shootDistanceEntry.getDouble(shootDistanceDefault);
    }
    public static double getBallSpinOffset() {
        return shootBallSpinVelocityEntry.getDouble(shootBallSpinVelocityDefault);
    }

}
