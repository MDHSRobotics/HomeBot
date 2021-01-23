
package frc.robot.oi.controllers;

import edu.wpi.first.wpilibj2.command.button.Trigger;

// A virtual button for simulating button events.
public class VirtualButton extends Trigger {

    public boolean active = false;

    public VirtualButton() {
    }

    @Override
    public boolean get() {
        return active;
    }

}
