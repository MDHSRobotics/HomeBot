package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");

        // Roller
        BotControllers.xbox.btnX.whileHeld(BotCommands.spinDelivery);

        BotControllers.xbox.btnA.whenPressed(BotCommands.shoot);
        BotControllers.xbox.btnB.whenPressed(BotCommands.stopShooter);
    }

}