package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");
        
        //Shoot
        BotControllers.xbox.btnA.whileHeld(BotCommands.openSolenoid);
        BotControllers.xbox.btnB.whileHeld(BotCommands.closeSolenoid);

        BotControllers.xbox.btnX.whileHeld(BotCommands.startCompressor);
        BotControllers.xbox.btnY.whileHeld(BotCommands.stopCompressor);
        
    }

}
