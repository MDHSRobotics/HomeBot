
package frc.robot;

import frc.robot.consoles.Logger;

// Configures all the button->command bindings for the robot.
public class ButtonBindings {

    // Configure xbox buttons
    public static void configureXbox() {
        Logger.setup("Configure Buttons -> Xbox Controller...");

        //DiffDriver
        BotControllers.xbox.btnY.whileHeld(BotCommands.rotateTowardsTarget);

        //BotControllers.xbox.btnStickLeft.whileHeld(BotCommands.alignDiffDriveToGyro);

        //Limelight
        BotControllers.xbox.btnY.whenReleased(BotCommands.turnOffLimelightArray);
        // Roller
        // BotControllers.xbox.btnX.whileHeld(BotCommands.reverseRoller);
        // BotControllers.xbox.btnB.whileHeld(BotCommands.spinRollerAndConveyor);

        // Conveyor & Shooter
        BotControllers.xbox.btnX.whileHeld(BotCommands.spinRollerAndConveyor);
        BotControllers.xbox.btnB.whileHeld(BotCommands.spinConveyorBackward);

        // BotControllers.xbox.btnBumperLeft.whileHeld(BotCommands.reverseConveyor);
        BotControllers.xbox.btnBumperRight.whenPressed(BotCommands.reverseConveyorAndShoot);
        BotControllers.xbox.btnBumperLeft.whenPressed(BotCommands.stopConveyorAndShooter);
        BotControllers.xbox.btnStart.whenPressed(BotCommands.resetShoot);

    }

}