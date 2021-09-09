package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");

        // Drive
        BotControllers.xbox.btnA.whileHeld(BotCommands.test1DiffDrive);
        BotControllers.xbox.btnB.whileHeld(BotCommands.test2DiffDrive);
        BotControllers.xbox.btnX.whileHeld(BotCommands.test3DiffDrive);
        BotControllers.xbox.btnY.whileHeld(BotCommands.test4DiffDrive);
        BotControllers.xbox.btnStart.whenPressed(BotCommands.test5DiffDrive);

        // // Pickup
        // BotControllers.xbox.btnBumperLeft.whenPressed(BotCommands.spinPickup);
        // BotControllers.xbox.btnBumperRight.whenPressed(BotCommands.stopPickup);

        // // Delivery
        // BotControllers.xbox.btnX.whenPressed(BotCommands.spinDelivery);
        // BotControllers.xbox.btnY.whenPressed(BotCommands.stopDelivery);

        // // Gate
        // BotControllers.xbox.btnDpadDown.whenPressed(BotCommands.toggleGate);

        // // Shoot
        // BotControllers.xbox.btnA.whileHeld(BotCommands.shoot);
    }

}
