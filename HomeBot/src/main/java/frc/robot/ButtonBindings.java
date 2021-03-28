package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");
        
        //Sensors 
        BotControllers.xbox.btnX.whenPressed(BotCommands.moveForwardAuto10Feet);
        BotControllers.xbox.btnY.whenPressed(BotCommands.driveDiffTank);

        //Shoot
        BotControllers.xbox.btnA.whileHeld(BotCommands.shoot);

        // Delivery
        BotControllers.xbox.btnBumperLeft.whenPressed(BotCommands.spinPickup);
        BotControllers.xbox.btnBumperRight.whenPressed(BotCommands.stopPickup);

        // Delivery
        BotControllers.xbox.btnX.whenPressed(BotCommands.spinDelivery);
        BotControllers.xbox.btnY.whenPressed(BotCommands.stopDelivery);

        BotControllers.xbox.btnDpadDown.whenPressed(BotCommands.toggleGate);
    }

}