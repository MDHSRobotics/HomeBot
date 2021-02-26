
package frc.robot.commands.pixyCam;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.sensors.Pixy;

// This command spins the Pickup
public class PixyTest extends CommandBase {

    public PixyTest() {
        Logger.setup("Constructing Command: PixyTest...");
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: PixyTest...");
    }

    @Override
    public void execute() {
       Pixy.detectFieldMode();
    }

    // This command continues until interrupted
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("--");
            Logger.ending("Interrupting Command: PixyTest...");
        } else {
            Logger.ending("Ending Command: PixyTest...");
        }
    }

}