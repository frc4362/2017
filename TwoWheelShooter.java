package com.gemsrobotics.team4362.frc2017.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;


public class TwoWheelShooter extends Subsystem {
	private final CANTalon m_hopper, m_kicker;
	
	public TwoWheelShooter(final int hopperPort, final int kickerPort) {
		m_hopper = new CANTalon(hopperPort);
		m_kicker = new CANTalon(kickerPort);
		
		// really important configuration lines to enable RPM control
		m_kicker.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		m_kicker.changeControlMode(TalonControlMode.Speed);
		m_kicker.configEncoderCodesPerRev(12);
		m_kicker.setForwardSoftLimit(0);
		m_kicker.setPosition(0);
		
		// for future tuning
		m_kicker.setP(0);
		m_kicker.setI(0);
		m_kicker.setD(0);
		m_kicker.setF(0);
	}

	// shooting at a new range is as simple as adding a new ShooterPreset
	public enum ShooterPreset {
		OFF(0.0, 0.0),
		ON(0.5, 1433);

		private final double m_hopperSpeed, m_kickerSpeed;

		ShooterPreset(final double hopperSpeed, final double kickerSpeed) {
			m_hopperSpeed = hopperSpeed;
			m_kickerSpeed = kickerSpeed;
		}

		public static ShooterPreset ofBool(final boolean on) {
			return on ? ON : OFF;
		}
		
		public double getHopperSpeed() {
			return m_hopperSpeed;
		}
		
		public double getKickerSpeed() {
			return m_kickerSpeed;
		}
	}
	
	public void set(final ShooterPreset targetSpeed) {
		m_hopper.set(targetSpeed.getHopperSpeed());
		m_kicker.set(targetSpeed.getKickerSpeed());
	}
	
    public void initDefaultCommand() {
    	
    }
}
