package frc.robot;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.*;

// Contains singleton instances of all the subsystems on the robot.
public class BotSubsystems {

    public static DiffDriver diffDriver;
    public static Pickup pickup;
    public static Delivery delivery;
    public static Gate gate;
    public static Shooter shooter;
    public static Turret turret;
    // Initialize all robot subsystems
    public static void initializeSubsystems() {
        Logger.setup("Initializing BotSubsystems...");

        diffDriver = new DiffDriver();
        pickup = new Pickup();
        delivery = new Delivery();
        gate = new Gate();
        shooter = new Shooter();
        turret = new Turret();
    }

    // Set all the subsystem "teleop" default commands
    public static void setTeleopDefaultCommands() {

        // DiffDriver
        Logger.setup("DiffDriver Teleop Default Command -> DriveDiffTank...");
        diffDriver.setDefaultCommand(BotCommands.driveDiffArcade);

        // Pickup
        Logger.setup("Pickup Teleop Default Command -> StopPickup...");
        pickup.setDefaultCommand(BotCommands.stopPickup);

        // Delivery
        Logger.setup("Delivery Teleop Default Command -> StopDelivery...");
        delivery.setDefaultCommand(BotCommands.stopDelivery);

        // Gate
        Logger.setup("Gate Teleop Default Command -> FeedGate...");
        gate.setDefaultCommand(BotCommands.feedGate);
        
        // Shoot
        Logger.setup("Shooter Teleop Default Command -> Shoot...");
        shooter.setDefaultCommand(BotCommands.stopShoot);
        
        // Turret 
        Logger.setup("Turret Teleop Default Command -> TurretAligning ...");
        turret.setDefaultCommand(BotCommands.TurretAligning);

    }

}
