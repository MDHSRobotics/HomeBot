
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.sensors.Pixy;
import frc.robot.commands.auto.MoveForwardAuto;

import frc.robot.consoles.Logger;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    // Autonomous variables
    private Command m_autonomousCommandRed;
    private Command m_autonomousCommandBlue;
    private Command m_autonomousCommandAutoNav;
    private MoveForwardAuto m_moveForwardAuto;

    /**
     * AutoNav Paths: "barrel", "bounce", "slalom"
     * Enter "none" if running Galactic Search.
     */
    private String autoNavPath = "slalom";

    // Test variables
    private int m_numberOfTests;
    // private int m_currentTestNumber;
    // private int m_testIteration;

    // When connected to the RoboRio, use this constructor because it will use the
    // proper period duration
    public Robot() {
        super();
    }

    // When running in Simulation mode (not connected to the RoboRio), use this
    // constructor because it can specify a longer period duration which avoids
    // watchdog overruns that can occur since the Simulator uses the VSCode debugger
    public Robot(double period) {
        super(period);
    }

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        System.out.println("--");
        Logger.setup("Initializing Robot...");

        // Initialize our RobotManager, which initializes and perists the state of the robot,
        // including flags, sensors, devices, subsystems, commands, shuffleboard,
        // and puts our autonomous chooser on the dashboard.
        RobotManager.initialize();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Update the Shuffleboard
        RobotManager.botShuffler.update();

        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
        System.out.println("--");
        Logger.ending("Disabling Robot...");

        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected by your {@link BotCommands} class.
     */
    @Override
    public void autonomousInit() {
        System.out.println("--");
        Logger.setup("Initializing Autonomous Mode...");

        CommandScheduler.getInstance().cancelAll();

        // Schedule the autonomous command
        m_autonomousCommandRed = BotCommands.getAutonomousCommand('R');
        m_autonomousCommandBlue = BotCommands.getAutonomousCommand('B');
        m_autonomousCommandAutoNav = BotCommands.getAutonomousCommand(autoNavPath);
        m_moveForwardAuto = BotCommands.moveForwardAuto10Feet;
        
        if (autoNavPath.equals("none")) {
            if (Pixy.detectFieldMode().equals("no blocks detected")) {
                if (m_moveForwardAuto != null) {
                    m_moveForwardAuto.schedule();
                }
                if (m_autonomousCommandBlue != null) {
                    m_autonomousCommandBlue.schedule();
                }
            } else {
                if (m_autonomousCommandRed != null) {
                    m_autonomousCommandRed.schedule();
                }
            }
        } else {
            if (m_autonomousCommandAutoNav != null) {
                m_autonomousCommandAutoNav.schedule();
            }
        }
        
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        System.out.println("--");
        Logger.setup("Initializing Teleop Mode...");

        // Set subsystem "teleop" default commands
        BotSubsystems.setTeleopDefaultCommands();

        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        // Configure all controllers
        BotControllers.configure();
    }

    @Override
    public void testInit() {
        System.out.println("--");
        Logger.setup("Initializing Test Mode...");

        CommandScheduler.getInstance().cancelAll();
        // Re-enable the scheduler
        CommandScheduler.getInstance().enable();


        // Reset the test variables
        Logger.info("Number of tests registered: " + m_numberOfTests);
        // m_currentTestNumber = 0;
        // m_testIteration = 0;
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
        
    }

}
