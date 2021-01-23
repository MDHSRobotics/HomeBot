
package frc.robot;
import frc.robot.oi.controllers.XboxControllerContainer;
import frc.robot.consoles.Logger;

// This class contains the robot controllers and defined ports.
public class BotControllers {

    // Controllers
    public static final XboxControllerContainer xbox = new XboxControllerContainer(0);

    // Configure all the controllers
    public static void configure() {
        configureXbox();
    }


    // Configure the xbox controller
    public static void configureXbox() {
        // Detect whether the xbox controller has been plugged in after start-up
        if (!BotControllers.xbox.configured) {
            boolean isConnected = BotControllers.xbox.isConnected();
            if (!isConnected) return;

            // Configure button bindings
            ButtonBindings.configureXbox();
            BotControllers.xbox.configured = true;
            Logger.setup("Xbox controller detected and configured");
        }
    }

}
