package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");
        //Sensors 
        BotControllers.xbox.btnX.whenPressed(BotCommands.spinDelivery);
        BotControllers.xbox.btnY.whenPressed(BotCommands.stopDelivery);

        //Shoot
        BotControllers.xbox.btnA.whileHeld(BotCommands.shoot);
        BotControllers.xbox.btnA.whenPressed(BotCommands.stopShoot);

        // Delivery
        BotControllers.xbox.btnBumperLeft.whenPressed(BotCommands.spinPickup);
        BotControllers.xbox.btnBumperRight.whenPressed(BotCommands.stopPickup);

    }

}