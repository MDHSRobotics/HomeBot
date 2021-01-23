
package frc.robot.oi.controllers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;

// Represents the directional pad button on an xbox controller (or equivalent on another device.)
public class DPadButton extends Button {

    public enum Direction {
        UP(0), UP_RIGHT(45), RIGHT(90), DOWN_RIGHT(135), DOWN(180), DOWN_LEFT(225), LEFT(270), UP_LEFT(315);

        public int degrees;

        Direction(int povAngle) {
            this.degrees = povAngle;
        }
    }

    public GenericHID device;
    public Direction direction;

    public DPadButton(GenericHID humanInterfaceDevice, Direction dPadButtonDirection) {
        this.device = humanInterfaceDevice;
        this.direction = dPadButtonDirection;
    }

    @Override
    public boolean get() {
        int angle = device.getPOV(0);
        return (angle == direction.degrees);
    }

    // Converts the Dpad Angle (0 to 360, clockwise) into a Gyro Angle (0 to 180, clockwise, 0 to -180 counter-clockwise)
    public static int getGyroAngleFromDpadAngle(GenericHID humanInterfaceDevice) {
        int angle = humanInterfaceDevice.getPOV(0);
        if (angle > 180) angle = angle - 360;
        return angle;
    }

}
