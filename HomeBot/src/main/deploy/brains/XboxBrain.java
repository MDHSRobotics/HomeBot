
package frc.robot.brains;

import edu.wpi.first.networktables.NetworkTableEntry;

// This class contains all the shared NetworkTableEntries for the Xbox Controller,
// their default values, and methods for retrieving their current values.
public class XboxBrain {

    //----------------//
    // Default Values //
    //----------------//

    public static double yLeftDeadZoneDefault = .001;
    public static double xLeftDeadZoneDefault = .001;
    public static double yRightDeadZoneDefault = .001;
    public static double xRightDeadZoneDefault = .001;
    public static double yLeftSensitivityDefault = .6;
    public static double xLeftSensitivityDefault = .6;
    public static double yRightSensitivityDefault = .6;
    public static double xRightSensitivityDefault = .6;

    //---------------------//
    // NetworkTableEntries //
    //---------------------//

    public static NetworkTableEntry yLeftDeadZoneEntry;
    public static NetworkTableEntry xLeftDeadZoneEntry;
    public static NetworkTableEntry yRightDeadZoneEntry;
    public static NetworkTableEntry xRightDeadZoneEntry;
    public static NetworkTableEntry yLeftSensitivityEntry;
    public static NetworkTableEntry xLeftSensitivityEntry;
    public static NetworkTableEntry yRightSensitivityEntry;
    public static NetworkTableEntry xRightSensitivityEntry;

    //---------//
    // Setters //
    //---------//



    //---------//
    // Getters //
    //---------//

    public static double getYleftDeadZone() {
        return yLeftDeadZoneEntry.getDouble(yLeftDeadZoneDefault);
    }

    public static double getXleftDeadZone() {
        return xLeftDeadZoneEntry.getDouble(xLeftDeadZoneDefault);
    }

    public static double getYrightDeadZone() {
        return yRightDeadZoneEntry.getDouble(yRightDeadZoneDefault);
    }

    public static double getXrightDeadZone() {
        return xRightDeadZoneEntry.getDouble(xRightDeadZoneDefault);
    }

    public static double getYleftSensitivity() {
        return yLeftSensitivityEntry.getDouble(yLeftSensitivityDefault);
    }

    public static double getXleftSensitivity() {
        return xLeftSensitivityEntry.getDouble(xLeftSensitivityDefault);
    }

    public static double getYrightSensitivity() {
        return yRightSensitivityEntry.getDouble(yRightSensitivityDefault);
    }

    public static double getXrightSensitivity() {
        return xRightSensitivityEntry.getDouble(xRightSensitivityDefault);
    }

}
