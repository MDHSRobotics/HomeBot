package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");
        
        //Shoot
        BotControllers.xbox.btnA.whileHeld(BotCommands.webCamera);
        BotControllers.xbox.btnA.whileHeld(BotCommands.toggleGate);

        // Drive
        BotControllers.xbox.btnStart.whenPressed(BotCommands.moveForwardAuto10Feet);
        BotControllers.xbox.btnB.whenPressed(BotCommands.driveDiffArcade);

        // Pickup
        BotControllers.xbox.btnBumperLeft.whenPressed(BotCommands.spinPickup);
        BotControllers.xbox.btnBumperLeft.whenPressed(BotCommands.spinDelivery);

        // Delivery
        BotControllers.xbox.btnBumperRight.whenPressed(BotCommands.stopPickup);
        BotControllers.xbox.btnBumperRight.whenPressed(BotCommands.stopDelivery);

        BotControllers.xbox.btnDpadUp.whenPressed(BotCommands.rotateTowardsTarget);

        //camera
        
    }

}
