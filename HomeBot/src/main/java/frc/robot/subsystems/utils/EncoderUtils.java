
package frc.robot.subsystems.utils;

import static frc.robot.subsystems.constants.EncoderConstants.*;

import java.lang.Math;

// Utility translator methods for encoder values.
public class EncoderUtils {

    //-------------------------//
    // Positioning Translators //
    //-------------------------//

    /** 
     * Computes an encoder tick count based on the desired motor rotation in degrees for a given gearbox ratio (MS : GS)
     * 
     * @param angle Desired angle to compute to ticks
     * @param gearRatio The gear ratio of the gearbox
     * @return Returns the angle computed to ticks
    */
    public static int translateDegreesToTicks(double angle, double gearRatio) {
        double rotationCountGS = angle / 360.0; // Amount of rotations on the gearbox shaft
        double rotationCountMS = rotationCountGS * gearRatio; // Amount of rotations on the motor shaft
        double rotationTicks = rotationCountMS * ENCODER_TPR; // Amount of ticks to rotate
        return (int) (rotationTicks);
    }

    /**
     * Computes ticks to radians
     * 
     * @param ticks tick integer that is computed to radian value
     * @return Returns the desired radian value
    */
    public static double translateTicksToRadians(int ticks) {
        double radians = ticks / ENCODER_TPR * 2 * Math.PI;
        return radians;
    }

    /**
     * Computes radians to ticks
     * 
     * @param radians radian value that is computed to a tick integer
     * @return Returns the desired tick integer
    */
    public static int translateRadiansToTicks(double radians) {
        double ticks = (radians * ENCODER_TPR / 2 / Math.PI);
        return (int) ticks;
    }

    /** 
     * Computes an encoder tick count based on the desired distance in inches for a given wheel diameter and gearbox ratio (MS : GS)
     * 
     * @param distance The desired distance in feet
     * @param wheelDiameter The diameter of the wheel in inches
     * @param gearRatio The gear ratio of the gearbox
     * @return Returns the amount of ticks to move a certain distance
    */
    public static int translateDistanceToTicks(double distance, double wheelDiameter, double gearRatio) {
        double wheelCircumference = Math.PI * (wheelDiameter / 12.0);
        double rotationCountGS = distance / wheelCircumference; // Amount of rotations on the gearbox shaft
        double rotationCountMS = rotationCountGS * gearRatio; // Amount of rotations on the motor shaft
        double rotationTicks = rotationCountMS * ENCODER_TPR; // Amount of ticks to rotate
        return (int) rotationTicks;
    }

    public static double translateTicksToDistance(double rotationTicks, double wheelCircumference){
        double distance = (rotationTicks/360)*(wheelCircumference / 12.0);
        return distance;
    }

    //----------------------//
    // Velocity Translators //
    //----------------------//

    /** 
     * Computes an encoder velocity tick count based on the desired velocity in feet per second for a given wheel diameter and gearbox ratio (MS : GS)
     * 
     * @param fps The velocity in feet per second
     * @param wheelDiameter The diameter of the wheel in inches
     * @param gearRatio The gear ratio of the gearbox
     * @return Returns ticksPerDecisecond
    */
    public static int translateFPSToTicksPerDecisecond(double fps, double wheelDiameter, double gearRatio) {
        double wheelCircumference = Math.PI * (wheelDiameter / 12.0);
        double rotationsPerSecondGS = fps / wheelCircumference; // Amount of rotations per second on the gearbox shaft
        double rotationsPerSecondMS = rotationsPerSecondGS * gearRatio; // Amount of rotations per second on the motor shaft
        double ticksPerDecisecond = rotationsPerSecondMS * ENCODER_TPR / 10.0; // Amount of ticks per decisecond on the motor shaft
        return (int) ticksPerDecisecond;
    }

    /** 
     * Computes a velocity in feet per second based on the ticksPerDecisecond for a given wheelDiameter and gearboxration (MS: GS)
     * 
     * @param ticksPerDecisecond Amount of ticks per decisecond to rotate
     * @param wheelDiameter The diameter of the wheel in inches
     * @param gearRatio The gear ratio of the gearbox
     * @return Returns feet per second
     */
    public static double translateTicksPerDecisecondToFPS(double ticksPerDecisecond, double wheelDiameter, double gearRatio){
        double rotationsPerSecondMS = ticksPerDecisecond / ENCODER_TPR * 10.0;
        double rotationsPerSecondGS = rotationsPerSecondMS / gearRatio;
        double wheelCircumference = Math.PI * (wheelDiameter / 12.0);
        double fps = rotationsPerSecondGS * wheelCircumference;
        return fps;
    }

    /** 
     * Computes a velocity in meters per second based on the ticksPerDecisecond for a given wheelDiameter and gearboxration (MS: GS)
     * 
     * @param ticksPerDecisecond Amount of ticks per decisecond to rotate
     * @param wheelDiameter The diameter of the wheel in inches
     * @param gearRatio The gear ratio of the gearbox
     * @return Returns meters per second
     */
    public static double translateTicksPerDecisecondToMPS(double ticksPerDecisecond, double wheelDiameter, double gearRatio){
        double rotationsPerSecondMS = ticksPerDecisecond / ENCODER_TPR * 10.0;
        double rotationsPerSecondGS = rotationsPerSecondMS / gearRatio;
        double wheelCircumference = Math.PI * (wheelDiameter * 0.0254);
        double mps = rotationsPerSecondGS * wheelCircumference;
        return mps;
    }

    /** 
     * Computes a velocity in meters per second based on the ticksPerDecisecond for a given wheelDiameter and gearboxration (MS: GS)
     * 
     * @param mps A velocity in meters per second
     * @param wheelDiameter The diameter of the wheel in inches
     * @param gearRatio The gear ratio of the gearbox attatched to the motor
     * @return Returns ticks per decisecond
     */
    public static int translateMPSToTicksPerDecisecond(double mps, double wheelDiameter, double gearRatio){
        double wheelCircumference = Math.PI * (wheelDiameter * 0.0254);
        double rotationsPerSecondGS = mps / wheelCircumference;
        double rotationsPerSecondMS = rotationsPerSecondGS * gearRatio;
        double ticksPerDecisecond = rotationsPerSecondMS * ENCODER_TPR / 10.0;
        return (int) ticksPerDecisecond;
    }

    /** 
     * Computes an encoder velocity tick count based on the desired rotations per second and the gearbox ratio (MS : GS)
     * 
     * @param rps Amount of rps on the gearbox shaft
     * @param gearRatio The gear ratio of the gearbox
     * @return Returns ticksPerDecisecond as an int
    */
    public static int translateRPSToTicksPerDecisecond(double rps, double gearRatio) {
        double rotationsPerDecisecondGS = rps / 10.0; // Amount of rpds on the gearbox shaft
        double rotationsPerDecisecondMS = rotationsPerDecisecondGS * gearRatio; // Amount of rpds on the motor shaft
        double ticksPerDecisecond = rotationsPerDecisecondMS * ENCODER_TPR; // Amount of ticks per decisecond to rotate
        return (int) ticksPerDecisecond;
    }
}
