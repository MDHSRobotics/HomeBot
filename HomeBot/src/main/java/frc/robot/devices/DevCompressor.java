
package frc.robot.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import edu.wpi.first.wpilibj.Compressor;

import static frc.robot.RobotManager.isSim;

// This class is a wrapper around Compressor in order to handle cases where the
// compressor is not physically connected.  This
// can be the case when running the simulator but it can also happen when
// executing code on theRoboRio without a fully assembled robot with all of
// necessary motors and controllers.

// If the compressor is connected then this class just forwards any calls directly
// to the Compressor class.

// If the compressor is not connected, only a subset of the Compressor interface is
// supported, mainly by tracing and other monitoring.

public class DevCompressor extends Compressor {

    private String m_devName;
    private String m_devDescription;
    private Monitor m_monitor;
    public boolean isConnected = true;

    public DevCompressor(String devName, int module) {
        super(module);

        m_devName = devName;
        m_devDescription = String.format("Compressor #%d", module);

        isConnected = isConnected();
        if (!isConnected) {
            SendableRegistry.disableLiveWindow(this);
            m_monitor = new Monitor(m_devName, m_devDescription);
        }
    }

    // Determines if this is connected
    private boolean isConnected() {
        if (isSim) return false;

        boolean enabled = this.enabled();
        return enabled;
    }

    public void setClosedLoopControl(boolean closedLoopState) {
        if (isConnected) {
            super.setClosedLoopControl(closedLoopState);
            return;
        }

        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        String arg = String.valueOf(closedLoopState);
        m_monitor.log(methodName, arg);
    }

    public double getCompressorCurrent() {
        if (isConnected) {
            return super.getCompressorCurrent();
        }

        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        m_monitor.log(methodName);
        return isSim ? 9.99 : -1;
    }

}