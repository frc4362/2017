package com.gemsrobotics.team4362.frc2017.subsystems;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * NEEDS TO BE CALIBRATED
 */
public final class OmniPositioningSystem extends Subsystem {
	// constants
	// btw use inches everywhere and we'll be okay
	private static final int 
			DISTANCE_PER_PULSE = 1440,
			WHEEL_DIAMETER = 6;

	private Encoder m_encoderY, m_encoderX;

	public static void configureEncoder(final Encoder device) {
		device.setDistancePerPulse(Math.PI * WHEEL_DIAMETER / DISTANCE_PER_PULSE);
		device.setReverseDirection(false);
		device.setMaxPeriod(0.1);
		device.setMinRate(1.4);
	}

	public OmniPositioningSystem(final int x1, final int x2, final int y1, final int y2) {
		m_encoderX = new Encoder(x1, x2);
		m_encoderY = new Encoder(y1, y2);

		configureEncoder(m_encoderX);
		configureEncoder(m_encoderY);
	}
	
    public void initDefaultCommand() {
    }

	public Encoder getXDevice() {
		return m_encoderX;
	}
	
	public Encoder getYDevice() {
		return m_encoderY;
	}
	
	/**@return The offset on an X/Y axis from the starting position, 
	 * in inches. 
	 */
	public String locationToString() {
		return "(" + m_encoderX.getDistance() + ", " + m_encoderY.getDistance() + ")";
	}
}
