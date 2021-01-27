
package frc.robot.brains;

import edu.wpi.first.networktables.NetworkTableEntry;

// This class contains all the shared NetworkTableEntries for the Shooter Subsystem,
// their default values, and methods for retrieving their current values.
public class RobotBrain {

    //----------------//
    // Default Values //
    //----------------//

    public static double fieldTargetHeightFeet = 98.25 / 12.0;
    public static double shooterAngleDegreesDefault = Math.PI / 4; // equivalent of 45 degrees
    public static double shooterHeightFeetDefault = 30.0 / 12.0;

    //---------------------//
    // NetworkTableEntries //
    //---------------------//

    public static NetworkTableEntry shootAngleDegreesEntry;
    public static NetworkTableEntry shootHeightFeetEntry;

    //---------//
    // Setters //
    //---------//

    public static void setShootAngleDegrees(double value) {
        shootAngleDegreesEntry.setDouble(value);
    }

    public static void setShootHeightFeet(double value) {
        shootHeightFeetEntry.setDouble(value);
    }

    //---------//
    // Getters //
    //---------//

    public static double getShootAngleDegrees() {
        return shootAngleDegreesEntry.getDouble(shooterAngleDegreesDefault);
    }

    public static double getShootHeightFeet() {
        return shootHeightFeetEntry.getDouble(shooterHeightFeetDefault);
    }

}
