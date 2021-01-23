
package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");

        //DiffDriver
        BotControllers.xbox.btnY.whileHeld(BotCommands.rotateTowardsTarget);
        

    }

}