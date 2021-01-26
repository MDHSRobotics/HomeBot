
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.Map;

import frc.robot.brains.ShufflerBrain;
import frc.robot.consoles.ShuffleLogger;
import frc.robot.RobotManager;

// The Shuffleboard Main tab.
public class MainTab {

    // Tab & Layouts
    private ShuffleboardTab m_tab;

    // Widgets
    private ComplexWidget m_autoCmdWidget;
    private SimpleWidget m_matchTimeWidget;

    // Constructor
    public MainTab() {
        ShuffleLogger.logTrivial("Constructing MainTab...");

        m_tab = Shuffleboard.getTab("Main");
    }

    // Create Brain Widgets
    public void preInitialize() {
        // Match Time
        m_matchTimeWidget = m_tab.add("Match Time", ShufflerBrain.matchTimeDefault);
        ShufflerBrain.matchTimeEntry = m_matchTimeWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
        // Autonomous Command
        m_autoCmdWidget = m_tab.add("Auto Command", RobotManager.autoCommandChooser);
    }

    // Configure all Widgets
    public void configure() {
        m_autoCmdWidget.withPosition(0, 0);
        m_autoCmdWidget.withSize(2, 1);

        m_matchTimeWidget.withPosition(2, 0);
        m_matchTimeWidget.withWidget(BuiltInWidgets.kDial);
        m_matchTimeWidget.withProperties(Map.of("min", -1, "max", 135)); // this property setting isn't working
    }

    // This will be called in the robotPeriodic
    public void update() {
        // Match time
        DriverStation ds = DriverStation.getInstance();
        double matchTime = ds.getMatchTime();
        ShufflerBrain.matchTimeEntry.setDouble(matchTime);
    }

}
