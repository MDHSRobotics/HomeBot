
package frc.robot.commands.pixyCam;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.consoles.Logger;
import frc.robot.sensors.Pixy;

// This command spins the Pickup
public class pixyTest extends CommandBase {

    public pixyTest() {
        Logger.setup("Constructing Command: pixyTest...");
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: pixyTest...");
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
            Logger.ending("Interrupting Command: pixyTest...");
        } else {
            Logger.ending("Ending Command: pixyTest...");
        }
    }

}