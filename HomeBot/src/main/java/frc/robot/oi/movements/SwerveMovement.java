
package frc.robot.oi.movements;

import frc.robot.oi.controllers.XboxPositionAccessible;
import frc.robot.oi.positions.ThumbstickPosition;
import frc.robot.subsystems.SwerveDriver;

// The values needed to drive with SwerveDriver.
public class SwerveMovement {

    public double forwardBackwardSpeed = 0; // Forward & Backward
    public double sideToSideSpeed = 0; // Side to Side Speed
    public double rotationSpeed = 0; // Rotation speed

    public SwerveMovement(double xLeftSpeed, double yLeftSpeed, double yRightSpeed) {
        forwardBackwardSpeed = xLeftSpeed;
        sideToSideSpeed = yLeftSpeed;
        rotationSpeed = yRightSpeed;
    }

    // Determines the tank movement (left-side straight speed, right-side straight speed)
    // from the given xbox thumbstick positions
    public static SwerveMovement getMovement(XboxPositionAccessible controller) {
        ThumbstickPosition pos = ThumbstickPosition.getPositions(controller, SwerveDriver.isXLeftFlipped);
        SwerveMovement move = new SwerveMovement(pos.leftForwardBackPosition, pos.leftSideToSidePosition, pos.rightSideToSidePosition);
        return move;
    }

}
