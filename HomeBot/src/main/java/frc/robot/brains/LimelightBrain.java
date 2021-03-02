
package frc.robot.brains;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.sensors.LimeLight;

// This class contains all the shared NetworkTableEntries for the Shooter Subsystem,
// their default values, and methods for retrieving their current values.
public class LimelightBrain {

    //----------------//
    // Default Values //
    //----------------//

    public static double txEntryDefault = -1;
    public static double tyEntryDefault = -1;
    public static double distanceEntryDefault = -1;

    //---------------------//
    // NetworkTableEntries //
    //---------------------//

    public static NetworkTableEntry txEntry;
    public static NetworkTableEntry tyEntry;
    public static NetworkTableEntry distanceEntry;

    //---------//
    // Setters //
    //---------//

    public static void updateTxEntry() {
        txEntry.setDouble(LimeLight.getXOffset());
    }
    public static void updateTyEntry() {
        tyEntry.setDouble(LimeLight.getYOffset());
    }
    public static void updateDistanceEntry() {
        distanceEntry.setDouble(LimeLight.calculateDistanceToTarget());
    }

    //---------//
    // Getters //
    //---------//

    public static double getTxEntry() {
        return txEntry.getDouble(txEntryDefault);
    }
    public static double getTyEntry() {
        return tyEntry.getDouble(tyEntryDefault);
    }
    public static double getDistanceEntry() {
        return distanceEntry.getDouble(distanceEntryDefault);
    }

}
