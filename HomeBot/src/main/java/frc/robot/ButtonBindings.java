package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");
        
        //Shoot
        //BotControllers.xbox.btnA.whileHeld(BotCommands.shoot);
        BotControllers.xbox.btnA.whileHeld(BotCommands.toggleGate);

        // Pickup
        BotControllers.xbox.btnBumperLeft.whenPressed(BotCommands.spinPickup);
        BotControllers.xbox.btnBumperLeft.whenPressed(BotCommands.spinDelivery);

        // Delivery
        BotControllers.xbox.btnBumperRight.whenPressed(BotCommands.stopPickup);
        BotControllers.xbox.btnBumperRight.whenPressed(BotCommands.stopDelivery);

        BotControllers.xbox.btnA.whenPressed(BotCommands.swerveDrive);

    }

}
