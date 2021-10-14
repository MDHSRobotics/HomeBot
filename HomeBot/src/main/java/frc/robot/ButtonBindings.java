package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");
        
        //Shoot
        BotControllers.xbox.btnA.whenPressed(BotCommands.shoot);
        BotControllers.xbox.btnA.whileHeld(BotCommands.toggleGate);
        BotControllers.xbox.btnB.whenPressed(BotCommands.turretAligning);
        BotControllers.xbox.btnX.whenPressed(BotCommands.stopShoot);
        BotControllers.xbox.btnY.whenPressed(BotCommands.setFlapAngle);
        // Drive
        BotControllers.xbox.btnStart.whenPressed(BotCommands.moveForwardAuto10Feet);
        BotControllers.xbox.btnA.whenPressed(BotCommands.driveDiffArcade);

        // Pickup
        BotControllers.xbox.btnBumperLeft.whenPressed(BotCommands.spinPickup);
        BotControllers.xbox.btnBumperLeft.whenPressed(BotCommands.spinDelivery);

        // Delivery
        BotControllers.xbox.btnBumperRight.whenPressed(BotCommands.stopPickup);
        BotControllers.xbox.btnBumperRight.whenPressed(BotCommands.stopDelivery);

        BotControllers.xbox.btnDpadUp.whenPressed(BotCommands.rotateTowardsTarget);
    }

}
