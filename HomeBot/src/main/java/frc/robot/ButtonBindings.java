package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");

        // Roller
        BotControllers.xbox.btnB.whileHeld(BotCommands.spinDelivery);

        // Pickup
        BotControllers.xbox.btnX.whileHeld(BotCommands.spinPickup);

        //Shoot
        BotControllers.xbox.btnA.whileHeld(BotCommands.shoot);

        //Pixy
        BotControllers.xbox.btnY.whileHeld(BotCommands.pixyTest);

    }

}