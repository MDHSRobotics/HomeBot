
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.shuffleboard.*;
import java.util.Map;

import frc.robot.brains.ShooterBrain;
import frc.robot.consoles.ShuffleLogger;

// The Shuffleboard Shooter tab.
public class ShooterTab {

    // Tab & Layouts
    private ShuffleboardTab m_tab;
    private ShuffleboardLayout m_bottomWheelLayout;
    private ShuffleboardLayout m_topWheelLayout;
    private ShuffleboardLayout m_shootTargetLayout;

    // Widgets
    private SimpleWidget m_shooterTopWheelCurrentVelocity;
    private SimpleWidget m_shooterBottomWheelCurrentVelocity;

    private SimpleWidget m_shooterBottomWheelMaxVelocity;
    private SimpleWidget m_shooterBottomWheelMinVelocity;
    private SimpleWidget m_shooterTopWheelMaxVelocity;
    private SimpleWidget m_shooterTopWheelMinVelocity;

    private SimpleWidget m_shooterDistance;
    private SimpleWidget m_shooterTargetTPHMS;

    // Constructor
    public ShooterTab() {
        ShuffleLogger.logTrivial("Constructing ShooterTab...");

        m_tab = Shuffleboard.getTab("Shooter");

        m_bottomWheelLayout = m_tab.getLayout("Bottom Wheel", BuiltInLayouts.kList);
        m_topWheelLayout = m_tab.getLayout("Top Wheel", BuiltInLayouts.kList);
        m_shootTargetLayout = m_tab.getLayout("Target", BuiltInLayouts.kList);
    }

    // Create Brain Widgets
    public void preInitialize() {

        // Current velocity
        m_shooterBottomWheelCurrentVelocity = m_bottomWheelLayout.add("Current Velocity (TpHMS)",
                ShooterBrain.shootBottomWheelCurrentVelocityDefault);
        ShooterBrain.shootBottomWheelCurrentVelocityEntry = m_shooterBottomWheelCurrentVelocity.getEntry();
        m_shooterBottomWheelCurrentVelocity.withWidget(BuiltInWidgets.kGraph);

        m_shooterTopWheelCurrentVelocity = m_topWheelLayout.add("Current Velocity (TpHMS)",
                ShooterBrain.shootTopWheelCurrentVelocityDefault);
        ShooterBrain.shootTopWheelCurrentVelocityEntry = m_shooterTopWheelCurrentVelocity.getEntry();
        m_shooterTopWheelCurrentVelocity.withWidget(BuiltInWidgets.kGraph);

        // Min velocity
        m_shooterBottomWheelMinVelocity = m_bottomWheelLayout.add("Min Velocity (TpHMS)",
                ShooterBrain.shootBottomWheelMinVelocityDefault);
        ShooterBrain.shootBottomWheelMinVelocityEntry = m_shooterBottomWheelMinVelocity.getEntry();
        m_shooterBottomWheelMinVelocity.withWidget(BuiltInWidgets.kTextView);

        m_shooterTopWheelMinVelocity = m_topWheelLayout.add("Min Velocity (TpHMS)",
                ShooterBrain.shootTopWheelMinVelocityDefault);
        ShooterBrain.shootTopWheelMinVelocityEntry = m_shooterTopWheelMinVelocity.getEntry();
        m_shooterTopWheelMinVelocity.withWidget(BuiltInWidgets.kTextView);

        // Max velocity
        m_shooterBottomWheelMaxVelocity = m_bottomWheelLayout.add("Max Velocity (TpHMS)",
                ShooterBrain.shootBottomWheelMaxVelocityDefault);
        ShooterBrain.shootBottomWheelMaxVelocityEntry = m_shooterBottomWheelMaxVelocity.getEntry();
        m_shooterBottomWheelMaxVelocity.withWidget(BuiltInWidgets.kTextView);

        m_shooterTopWheelMaxVelocity = m_topWheelLayout.add("Max Velocity (TpHMS)",
                ShooterBrain.shootTopWheelMaxVelocityDefault);
        ShooterBrain.shootTopWheelMaxVelocityEntry = m_shooterTopWheelMaxVelocity.getEntry();
        m_shooterTopWheelMaxVelocity.withWidget(BuiltInWidgets.kTextView);

        // Distance
        m_shooterDistance = m_shootTargetLayout.add("Distance (Feet)", ShooterBrain.shootDistanceDefault);
        ShooterBrain.shootDistanceEntry = m_shooterDistance.getEntry();
        m_shooterDistance.withWidget(BuiltInWidgets.kTextView);

        // Target velocity
        m_shooterTargetTPHMS = m_shootTargetLayout.add("Velocity (TpHMS)", ShooterBrain.shootTargetTPHMSDefault);
        ShooterBrain.shootTargetTPHMSEntry = m_shooterTargetTPHMS.getEntry();
        m_shooterTargetTPHMS.withWidget(BuiltInWidgets.kTextView);
    }

    // Create all other Widgets
    public void initialize() {
    }

    // Configure all Widgets
    public void configure() {
        m_bottomWheelLayout.withPosition(0, 0);
        m_bottomWheelLayout.withSize(2, 3);
        // m_bottomWheelLayout.withProperties(Map.of("Number of columns", 1));
        // m_bottomWheelLayout.withProperties(Map.of("Number of rows", 3));
        m_bottomWheelLayout.withProperties(Map.of("Label position", "TOP"));

        m_topWheelLayout.withPosition(2, 0);
        m_topWheelLayout.withSize(2, 3);
        // m_topWheelLayout.withProperties(Map.of("Number of columns", 1));
        // m_topWheelLayout.withProperties(Map.of("Number of rows", 3));
        m_topWheelLayout.withProperties(Map.of("Label position", "TOP"));

        m_shootTargetLayout.withPosition(4, 0);
        m_shootTargetLayout.withSize(1, 3);
        // m_shootTargetLayout.withProperties(Map.of("Number of columns", 1));
        // m_shootTargetLayout.withProperties(Map.of("Number of rows", 3));
        m_shootTargetLayout.withProperties(Map.of("Label position", "TOP"));
    }

    // This will be called in the robotPeriodic
    public void update() {

    }

}