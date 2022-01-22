
package frc.robot.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import edu.wpi.first.wpilibj.Solenoid;

import static frc.robot.RobotManager.isSim;

// This class is a wrapper around Solenoid in order to handle cases where the
// Solenoid is not physically connected.  This
// can be the case when running the simulator but it can also happen when
// executing code on the RoboRio without a fully assembled robot with all of
// necessary motors and controllers.

// If the Solenoid is connected then this class just forwards any calls directly
// to the Solenoid class.

// If the Solenoid is not connected, only a subset of the Solenoid interface is
// supported, mainly by tracing and other monitoring.

public class DevSolenoid extends Solenoid {

    private String m_devName;
    private String m_devDescription;
    private Monitor m_monitor;
    public boolean isConnected = true;

    public DevSolenoid(String devName, int channel) {
        super(channel);

        m_devName = devName;
        m_devDescription = String.format("Solenoid #%d", channel);

        isConnected = isConnected();
        if (!isConnected) {
            SendableRegistry.disableLiveWindow(this);
            m_monitor = new Monitor(m_devName, m_devDescription);
        }
    }

    // Determines if this is connected
    private boolean isConnected() {
        if (isSim) return false;

        boolean outputOn = this.get();
        return outputOn;
    }

    public void set(boolean state) {
        if (isConnected) {
            super.set(state);
            return;
        }

        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String arg = String.valueOf(state);
        m_monitor.log(methodName, arg);
    }

}