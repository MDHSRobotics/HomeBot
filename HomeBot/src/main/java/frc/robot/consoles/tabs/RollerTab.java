
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.brains.RollerBrain;
import frc.robot.consoles.ShuffleLogger;

// The Shuffleboard Roller tab.
public class RollerTab {

    // Tab & Layouts
    private ShuffleboardTab m_tab;

    // Widgets
    private SimpleWidget m_powerWidget;

    // Constructor
    public RollerTab() {
        ShuffleLogger.logTrivial("Constructing RollerTab...");

        m_tab = Shuffleboard.getTab("Roller");
    }

    // Create Brain Widgets
    public void preInitialize() {
        m_powerWidget = m_tab.add("Power", RollerBrain.powerDefault);
        RollerBrain.powerEntry = m_powerWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
    }

    // Configure all Widgets
    public void configure() {
        m_powerWidget.withWidget(BuiltInWidgets.kTextView);
        m_powerWidget.withPosition(0, 0);
    }

    // This will be called in the robotPeriodic
    public void update() {
    }

}
