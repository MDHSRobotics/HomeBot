
package frc.robot.oi.movements;

import frc.robot.oi.controllers.XboxPositionAccessible;
import frc.robot.oi.positions.ThumbstickPosition;

// The values needed to drive with SwerveDriver.
public class SwerveMovement {

    public double forwardBackwardSpeed = 0; // Forward & Backward
    public double sideToSideSpeed = 0; // Side to Side Speed
    public double rotationSpeed = 0; // Rotation speed
    public double DEADBAND = 0.05;

    public SwerveMovement(double xLeftSpeed, double yLeftSpeed, double yRightSpeed) {
        if (Math.abs(xLeftSpeed) < DEADBAND) {
            forwardBackwardSpeed = 0;
        } else {
            forwardBackwardSpeed = xLeftSpeed;
        }
        if (Math.abs(yLeftSpeed) < DEADBAND) {
            sideToSideSpeed = 0;
        } else {
            sideToSideSpeed = yLeftSpeed;
        }
        if (Math.abs(yRightSpeed) < DEADBAND) {
            rotationSpeed = 0;
        } else {
            rotationSpeed = yRightSpeed;
        }
    }

    // Determines the Swerve movement (straight speed, side speed, rotation speed)
    // from the given xbox thumbstick positions
    public static SwerveMovement getMovement(XboxPositionAccessible controller, boolean isYFlipped) {
        ThumbstickPosition pos = ThumbstickPosition.getPositions(controller, isYFlipped);
        SwerveMovement move = new SwerveMovement(pos.leftForwardBackPosition, pos.leftSideToSidePosition, pos.rightSideToSidePosition);
        return move;
    }

}
