
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.brains.ConveyorBrain;
import frc.robot.consoles.ShuffleLogger;

// The Shuffleboard Roller tab.
public class ConveyorTab {

    // Tab & Layouts
    private ShuffleboardTab m_tab;

    // Widgets
    private SimpleWidget m_powerWidget;

    // Constructor
    public ConveyorTab() {
        ShuffleLogger.logTrivial("Constructing ConveyorTab...");

        m_tab = Shuffleboard.getTab("Conveyor");
    }

    // Create Brain Widgets
    public void preInitialize() {
        m_powerWidget = m_tab.add("Power", ConveyorBrain.powerDefault);
        ConveyorBrain.powerEntry = m_powerWidget.getEntry();
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
