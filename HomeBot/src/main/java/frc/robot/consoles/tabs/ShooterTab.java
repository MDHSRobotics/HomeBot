
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.shuffleboard.*;
import java.util.Map;

import frc.robot.brains.ShooterBrain;
import frc.robot.consoles.ShuffleLogger;
import frc.robot.BotCommands;

// The Shuffleboard Shooter tab.
public class ShooterTab {

    // Tab & Layouts
    private ShuffleboardTab m_tab;
    private ShuffleboardLayout m_bottomWheelLayout;
    private ShuffleboardLayout m_topWheelLayout;
    private ShuffleboardLayout m_shootConfigLayout;

    // Commands
    private ComplexWidget m_shooterReset;
    private ComplexWidget m_shootWithVelocity;
    private ComplexWidget m_stopShooter;

    // Widgets
    private SimpleWidget m_shooterTopWheelCurrentVelocity;
    private SimpleWidget m_shooterBottomWheelCurrentVelocity;

    private SimpleWidget m_shooterBottomWheelMaxVelocity;
    private SimpleWidget m_shooterBottomWheelMinVelocity;
    private SimpleWidget m_shooterBottomWheelAverageVelocity;
    private SimpleWidget m_shooterTopWheelMaxVelocity;
    private SimpleWidget m_shooterTopWheelMinVelocity;
    private SimpleWidget m_shooterTopWheelAverageVelocity;

    private SimpleWidget m_shooterDistance;
    private SimpleWidget m_shooterTargetTPHMS;
    private SimpleWidget m_shootBallSpinVelocity;
    private SimpleWidget m_pidkFTop;
    private SimpleWidget m_pidkFBottom;

    // Constructor
    public ShooterTab() {
        ShuffleLogger.logTrivial("Constructing ShooterTab...");

        m_tab = Shuffleboard.getTab("Shooter");

        m_bottomWheelLayout = m_tab.getLayout("Bottom Wheel", BuiltInLayouts.kList);
        m_topWheelLayout = m_tab.getLayout("Top Wheel", BuiltInLayouts.kList);
        m_shootConfigLayout = m_tab.getLayout("Config", BuiltInLayouts.kList);
    }

    // Create Brain Widgets
    public void preInitialize() {

        // Top Wheel Layout

        // Current Velocity
        m_shooterTopWheelCurrentVelocity = m_topWheelLayout.add("Current Velocity (TpHMS)",
                ShooterBrain.shootTopWheelCurrentVelocityDefault);
        ShooterBrain.shootTopWheelCurrentVelocityEntry = m_shooterTopWheelCurrentVelocity.getEntry();
        m_shooterTopWheelCurrentVelocity.withWidget(BuiltInWidgets.kGraph);

        // Min Velocity
        m_shooterTopWheelMinVelocity = m_topWheelLayout.add("Min Velocity (TpHMS)",
                ShooterBrain.shootTopWheelMinVelocityDefault);
        ShooterBrain.shootTopWheelMinVelocityEntry = m_shooterTopWheelMinVelocity.getEntry();
        m_shooterTopWheelMinVelocity.withWidget(BuiltInWidgets.kTextView);

        // Max Velocity
        m_shooterTopWheelMaxVelocity = m_topWheelLayout.add("Max Velocity (TpHMS)",
                ShooterBrain.shootTopWheelMaxVelocityDefault);
        ShooterBrain.shootTopWheelMaxVelocityEntry = m_shooterTopWheelMaxVelocity.getEntry();
        m_shooterTopWheelMaxVelocity.withWidget(BuiltInWidgets.kTextView);

        // Average velocity
        m_shooterTopWheelAverageVelocity = m_topWheelLayout.add("Average Velocity (TpHMS)",
                ShooterBrain.shootTopWheelAverageVelocityDefault);
        ShooterBrain.shootTopWheelAverageVelocityEntry = m_shooterTopWheelAverageVelocity.getEntry();
        m_shooterTopWheelAverageVelocity.withWidget(BuiltInWidgets.kTextView);


        // Bottom Wheel Layout

        // Current velocity
        m_shooterBottomWheelCurrentVelocity = m_bottomWheelLayout.add("Current Velocity (TpHMS)",
                ShooterBrain.shootBottomWheelCurrentVelocityDefault);
        ShooterBrain.shootBottomWheelCurrentVelocityEntry = m_shooterBottomWheelCurrentVelocity.getEntry();
        m_shooterBottomWheelCurrentVelocity.withWidget(BuiltInWidgets.kGraph);

        // Min velocity
        m_shooterBottomWheelMinVelocity = m_bottomWheelLayout.add("Min Velocity (TpHMS)",
                ShooterBrain.shootBottomWheelMinVelocityDefault);
        ShooterBrain.shootBottomWheelMinVelocityEntry = m_shooterBottomWheelMinVelocity.getEntry();
        m_shooterBottomWheelMinVelocity.withWidget(BuiltInWidgets.kTextView);

        // Max velocity
        m_shooterBottomWheelMaxVelocity = m_bottomWheelLayout.add("Max Velocity (TpHMS)",
                ShooterBrain.shootBottomWheelMaxVelocityDefault);
        ShooterBrain.shootBottomWheelMaxVelocityEntry = m_shooterBottomWheelMaxVelocity.getEntry();
        m_shooterBottomWheelMaxVelocity.withWidget(BuiltInWidgets.kTextView);

        // Avergage Velocity
        m_shooterBottomWheelAverageVelocity = m_bottomWheelLayout.add("Average Velocity (TpHMS)",
                ShooterBrain.shootBottomWheelAverageVelocityDefault);
        ShooterBrain.shootBottomWheelAverageVelocityEntry = m_shooterBottomWheelAverageVelocity.getEntry();
        m_shooterBottomWheelAverageVelocity.withWidget(BuiltInWidgets.kTextView);

        
        // Config Layout

        // Distance
        m_shooterDistance = m_shootConfigLayout.add("Shoot Distance (Feet)", ShooterBrain.shootDistanceDefault);
        ShooterBrain.shootDistanceEntry = m_shooterDistance.getEntry();
        m_shooterDistance.withWidget(BuiltInWidgets.kTextView);

        // Target velocity
        m_shooterTargetTPHMS = m_shootConfigLayout.add("Shoot Velocity (TpHMS)", ShooterBrain.shootTargetTPHMSDefault);
        ShooterBrain.shootTargetTPHMSEntry = m_shooterTargetTPHMS.getEntry();
        m_shooterTargetTPHMS.withWidget(BuiltInWidgets.kTextView);

        // Ball Spin Offset
        m_shootBallSpinVelocity = m_shootConfigLayout.add("Ball Spin Velocity (TpHMS)", ShooterBrain.shootBallSpinVelocityDefault);
        ShooterBrain.shootBallSpinVelocityEntry = m_shootBallSpinVelocity.getEntry();
        m_shootBallSpinVelocity.withWidget(BuiltInWidgets.kTextView);

        // pidkFTop
        m_pidkFTop = m_shootConfigLayout.add("Top PID kF", ShooterBrain.pidkFTopDefault);
        ShooterBrain.pidkFTopEntry = m_pidkFTop.getEntry();
        m_pidkFTop.withWidget(BuiltInWidgets.kTextView);

        // pidkFBottom
        m_pidkFBottom = m_shootConfigLayout.add("Bottom PID kF", ShooterBrain.pidkFBottomDefault);
        ShooterBrain.pidkFBottomEntry = m_pidkFBottom.getEntry();
        m_pidkFBottom.withWidget(BuiltInWidgets.kTextView);
    }

    // Create all other Widgets
    public void initialize() {
        m_shooterReset = m_shootConfigLayout.add("RESET", BotCommands.resetShoot);
        m_shooterReset.withWidget(BuiltInWidgets.kCommand);

        m_shootWithVelocity = m_shootConfigLayout.add("SHOOT", BotCommands.shoot);
        m_shootWithVelocity.withWidget(BuiltInWidgets.kCommand);

        m_stopShooter = m_shootConfigLayout.add("STOP", BotCommands.stopShooter);
        m_stopShooter.withWidget(BuiltInWidgets.kCommand);
    }

    // Configure all Widgets
    public void configure() {
        m_bottomWheelLayout.withPosition(0, 0);
        m_bottomWheelLayout.withSize(2, 4);
        // m_bottomWheelLayout.withProperties(Map.of("Number of columns", 1));
        // m_bottomWheelLayout.withProperties(Map.of("Number of rows", 3));
        m_bottomWheelLayout.withProperties(Map.of("Label position", "TOP"));

        m_topWheelLayout.withPosition(2, 0);
        m_topWheelLayout.withSize(2, 4);
        // m_topWheelLayout.withProperties(Map.of("Number of columns", 1));
        // m_topWheelLayout.withProperties(Map.of("Number of rows", 3));
        m_topWheelLayout.withProperties(Map.of("Label position", "TOP"));

        m_shootConfigLayout.withPosition(4, 0);
        m_shootConfigLayout.withSize(2, 5);
        // m_shootTargetLayout.withProperties(Map.of("Number of columns", 1));
        // m_shootTargetLayout.withProperties(Map.of("Number of rows", 3));
        m_shootConfigLayout.withProperties(Map.of("Label position", "TOP"));
    }

    // This will be called in the robotPeriodic
    public void update() {

    }

}