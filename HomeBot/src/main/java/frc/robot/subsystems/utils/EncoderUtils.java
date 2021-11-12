
package frc.robot.subsystems.utils;

import static frc.robot.subsystems.constants.EncoderConstants.*;

import java.lang.Math;

// Utility methods for encoder values.
public class EncoderUtils {

    /** Computes an encoder tick count based on the desired motor rotation in degrees for a given gearbox ratio (MS : GS)
     * @param angle Desired angle to compute to ticks
     * @param gearRatio The gear ratio of the gearbox
     * @return Returns the angle computed to ticks
    */
    public static int translateAngleToTicks(double angle, double gearRatio) {
        double rotationCountGS = angle / 360.0; // Amount of rotations on the gearbox shaft
        double rotationCountMS = rotationCountGS * gearRatio; // Amount of rotations on the motor shaft
        double rotationTicks = rotationCountMS * ENCODER_TPR; // Amount of ticks to rotate
        return (int) (rotationTicks);
    }

    /** Computes an encoder tick count based on the desired distance in inches for a given wheel diameter and gearbox ratio (MS : GS)
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
    
    /** Computes an encoder tick count based on the desired distance in inches for a given wheel diameter and gearbox ratio (MS : GS)
     * @param rotationTicks The amount of ticks to rotate a certain distance
     * @param wheelCircumference The circumference of the wheel in inches
     * @return Returns the distance in feet
    */
    public static double translateTicksToDistance(double rotationTicks, double wheelCircumference){
        double distance = (rotationTicks/4096)*(wheelCircumference / 12.0);
        return distance;
    }

    /**
     * Converts by taking the MPS and dividing it by the cirfcumference to find the ratio. After finding the ratio, you multiply by the ticks per rotation to get ticks per second. Convert from there.
     * @param MPS
     * @param wheelDiameter
     * @return
     */
    public static double translateMetersPerSecondToTPHMS(double MPS, double wheelDiameter) {
        double wheelCircumference = wheelDiameter * Math.PI;
        double TPHMS = ((MPS / wheelCircumference) * ENCODER_TPR) / 10;
        return TPHMS;
    }

    /** Computes an encoder velocity tick count based on the desired velocity in feet per second for a given wheel diameter and gearbox ratio (MS : GS)
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

    /** Computes a velocity in feet per second based on the ticksPerDecisecond for a given wheelDiameter and gearboxration (MS: GS)
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

    /** Computes an encoder velocity tick count based on the desired rotations per second and the gearbox ratio (MS : GS)
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

    /**Computes ticks to radians
     * @param ticks tick integer that is computed to radian value
     * @return Returns the desired radian value
    */
    public static double translateTicksToRadians(int ticks) {
        double radians = ticks / 4096 * 2 * Math.PI;
        return radians;
    }

    /**Computes radians to ticks
     * @param radians radian value that is computed to a tick integer
     * @return Returns the desired tick integer
    */
    public static int translateRadiansToTicks(double radians) {
        int ticks = (int) (radians * 4096 / 2 / Math.PI);
        return ticks;
    }

	public static int translateMPSToTicksPerDecisecond(double MPS, double wheelDiameter, double gearRatio) {
        double wheelCircumference = Math.PI * (wheelDiameter);
        double rotationsPerSecondGS = MPS / wheelCircumference; // Amount of rotations per second on the gearbox shaft
        double rotationsPerSecondMS = rotationsPerSecondGS * gearRatio; // Amount of rotations per second on the motor shaft
        double ticksPerDecisecond = rotationsPerSecondMS * ENCODER_TPR / 10.0; // Amount of ticks per decisecond on the motor shaft
        return (int) ticksPerDecisecond;
	}
}
