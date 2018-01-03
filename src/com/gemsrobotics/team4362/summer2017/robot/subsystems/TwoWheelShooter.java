package com.gemsrobotics.team4362.summer2017.robot.subsystems;

import java.util.function.Consumer;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.VelocityMeasurementPeriod;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("unused")
public final class TwoWheelShooter {
	private final CANTalon m_hopper, m_kicker, m_kickerSlave, m_hopperSlave;
	private final int m_hopperSlavePort, m_kickerSlavePort;

	private static final double
		kP = 4.5, // used to be 3.875
		kI = 0.002, // 0.0095 I with foam: 0.00225
		kD = 0.0,
		kF = 2.7648648648648648648648648648649; // trust me on this

	private static void configurePID(final CANTalon device) {
		// really important configuration lines to enable RPM control
		device.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		device.changeControlMode(TalonControlMode.Speed);

		device.configNominalOutputVoltage(+0.0f, -0.0f);
		device.configPeakOutputVoltage(+12.0f, -12.0f);

		device.configEncoderCodesPerRev(0);
		device.setForwardSoftLimit(0);

		// for future tuning
		device.setP(kP);
		device.setI(kI);
		device.setD(kD);
		device.setF(kF);

		device.enableControl();
	}

	private static void configurePeriod(final CANTalon device) {
		device.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_10Ms);
		device.SetVelocityMeasurementWindow(4);
	}

	public TwoWheelShooter(final int[] ports, final int hopperSlavePort, final int kickerSlavePort) {
		m_hopper = new CANTalon(ports[0]) {
			public void setSetpoint(final double speed) {
				SmartDashboard.putNumber("hopper setpoint", speed);
				super.setSetpoint(speed);
			}
		};
		
		m_hopper.setInverted(false); // usually inverted
		m_hopper.reverseOutput(true);
		m_hopper.reverseSensor(true);
		configurePID(m_hopper);

		m_kicker = new CANTalon(ports[1]) {
			public void setSetpoint(final double speed) {
				SmartDashboard.putNumber("kicker setpoint", speed);
				super.setSetpoint(speed);
			}
		};
		
		m_kicker.setInverted(false);
		m_kicker.reverseSensor(false);
		configurePID(m_kicker);
		
		m_hopperSlave = new CANTalon(hopperSlavePort);
		m_hopperSlave.changeControlMode(TalonControlMode.Follower);
		m_hopperSlavePort = hopperSlavePort;

		m_kickerSlave = new CANTalon(kickerSlavePort);
		m_kickerSlave.changeControlMode(TalonControlMode.Follower);
		m_kickerSlavePort = kickerSlavePort;
		
		configurePeriod(m_hopper);
		configurePeriod(m_kicker);
		configurePeriod(m_hopperSlave);
		configurePeriod(m_kickerSlave);
	}

	// shooting at a new range is as simple as adding a new ShooterPreset
	public enum ShooterPreset {
		OFF(0, 0, TalonControlMode.PercentVbus),
		SHORT(5650, 6850, TalonControlMode.Speed),
		LONG(6500, 6850, TalonControlMode.Speed),
		MAX(18000, 18000, TalonControlMode.Speed);

		private final double m_hopperSpeed, m_kickerSpeed;
		private final TalonControlMode m_talonMode;

		/*
		 * @param hopperSpeed In native units per 100ms
		 * @param kickerSpeed In native units per 100ms
		 */
		ShooterPreset(final double hopperSpeed, final double kickerSpeed, final TalonControlMode mode) {
			m_hopperSpeed = hopperSpeed / 50d;
			m_kickerSpeed = kickerSpeed / 50d;
			m_talonMode = mode;
		}

		public static ShooterPreset ofBool(final boolean on) {
			return on ? LONG : OFF;
		}

		public double getHopperSpeed() {
			return m_hopperSpeed;
		}

		public double getKickerSpeed() {
			return m_kickerSpeed;
		}
		
		public TalonControlMode getMode() {
			return m_talonMode;
		}
	}

	public void set(final ShooterPreset targetSpeed) {
		m_hopper.changeControlMode(targetSpeed.getMode());
		m_kicker.changeControlMode(targetSpeed.getMode());

		m_hopper.setSetpoint(targetSpeed.getHopperSpeed());
		m_kicker.setSetpoint(targetSpeed.getKickerSpeed());

		m_hopperSlave.set(m_hopperSlavePort);
		m_kickerSlave.set(m_kickerSlavePort);
	}

	public boolean isOn() {
		return (m_hopper.getSpeed() * 50) > 4500 && (m_kicker.getSpeed() * 50) > 5000;
	}

	public CANTalon getHopper() {
		return m_hopper;
	}
	
	public CANTalon getKicker() {
		return m_kicker;
	}
}
