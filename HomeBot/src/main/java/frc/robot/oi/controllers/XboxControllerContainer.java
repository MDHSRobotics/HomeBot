
package frc.robot.oi.controllers;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.oi.controllers.DPadButton.Direction;
import frc.robot.oi.positions.ThumbstickPosition;
import frc.robot.oi.positions.TriggerAxisPosition;

// This class contains an xbox controller and properties for all its buttons.
public class XboxControllerContainer extends ControllerContainer implements XboxPositionAccessible {

    public XboxController xbox;
    public JoystickButton btnA;
    public JoystickButton btnB;
    public JoystickButton btnX;
    public JoystickButton btnY;
    public JoystickButton btnBumperLeft;
    public JoystickButton btnBumperRight;
    public JoystickButton btnBack;
    public JoystickButton btnStart;
    public JoystickButton btnStickLeft;
    public JoystickButton btnStickRight;
    public DPad btnDpad;
    public DPadButton btnDpadUp;
    public DPadButton btnDpadDown;
    public DPadButton btnDpadLeft;
    public DPadButton btnDpadRight;
    public DPadButton btnDpadUpLeft;
    public DPadButton btnDpadUpRight;
    public DPadButton btnDpadDownLeft;
    public DPadButton btnDpadDownRight;

    public XboxControllerContainer(int port) {
        super(port);
        xbox = new XboxController(port);
        btnA = new JoystickButton(xbox, 1);
        btnB = new JoystickButton(xbox, 2);
        btnX = new JoystickButton(xbox, 3);
        btnY = new JoystickButton(xbox, 4);
        btnBumperLeft = new JoystickButton(xbox, 5);
        btnBumperRight = new JoystickButton(xbox, 6);
        btnBack = new JoystickButton(xbox, 7);
        btnStart = new JoystickButton(xbox, 8);
        btnStickLeft = new JoystickButton(xbox, 9);
        btnStickRight = new JoystickButton(xbox, 10);
        btnDpad = new DPad(xbox);
        btnDpadUp = new DPadButton(xbox, Direction.UP);
        btnDpadDown = new DPadButton(xbox, Direction.DOWN);
        btnDpadLeft = new DPadButton(xbox, Direction.LEFT);
        btnDpadRight = new DPadButton(xbox, Direction.RIGHT);
        btnDpadUpLeft = new DPadButton(xbox, Direction.UP_LEFT);
        btnDpadUpRight = new DPadButton(xbox, Direction.UP_RIGHT);
        btnDpadDownLeft = new DPadButton(xbox, Direction.DOWN_LEFT);
        btnDpadDownRight = new DPadButton(xbox, Direction.DOWN_RIGHT);
    }

    // Gets the raw xbox thumbstick positions
    public ThumbstickPosition getThumbstickPositions(boolean isYleftFlipped) {
        if (!isConnected()) return new ThumbstickPosition();

        double yLeft = xbox.getY(Hand.kLeft); // Forward & backward, flipped
        double xLeft = xbox.getX(Hand.kLeft); // Strafe
        double yRight = xbox.getY(Hand.kRight); // Forward & backward, flipped
        double xRight = xbox.getX(Hand.kRight); // Rotate

        ThumbstickPosition pos = new ThumbstickPosition(yLeft, xLeft, yRight, xRight);
        return pos;
    }

    // Gets the raw xbox trigger positions
    public TriggerAxisPosition getTriggerAxisPositions() {
        if (!isConnected()) return new TriggerAxisPosition();

        double leftTriggerAxis = xbox.getTriggerAxis(Hand.kLeft);
        double rightTriggerAxis = xbox.getTriggerAxis(Hand.kRight);

        TriggerAxisPosition pos = new TriggerAxisPosition(leftTriggerAxis, rightTriggerAxis);
        return pos;
    }

}
