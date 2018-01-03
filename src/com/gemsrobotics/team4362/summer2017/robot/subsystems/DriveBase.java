package com.gemsrobotics.team4362.summer2017.robot.subsystems;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.gemsrobotics.util.UnfilteredRobotDrive;

import edu.wpi.first.wpilibj.RobotDrive;

public class DriveBase {
	protected final List<CANTalon> m_driveTalons; 
	protected final RobotDrive m_driveBase;

	protected boolean m_usePID;

	private final CANTalon
		m_talonLeftMaster, 
		m_talonRightMaster, 
		m_talonLeftSlave, 
		m_talonRightSlave;

	private enum DriveSide {
		LEFT(1900, 0.6, 0.0, 0.0, 0.45),
		RIGHT(1900, 0.6, 0.0, 0.0, 0.5);

		public final double kP, kI, kD, kF, targetSpeed;

		DriveSide(
				final double tSpeed,
				final double p, 
				final double i, 
				final double d, 
				final double f
		) {
			targetSpeed = tSpeed;
			
			kP = p;
			kI = i;
			kD = d;
			kF = f;
		}
	}

	protected static void configureTalonVoltage(final CANTalon device) {
		device.configNominalOutputVoltage(+0.0f, -0.0f);
		device.configPeakOutputVoltage(+11.0f, -11.0f);
	}

	protected static void configureTalonFront(final CANTalon device, final DriveSide vars) {
		device.changeControlMode(TalonControlMode.Speed);
		device.setPID(vars.kP, vars.kI, vars.kD);
		device.setF(vars.kF);

		device.enableControl();
	}

	protected void configureTalonBack(final CANTalon device) {
		device.changeControlMode(TalonControlMode.Follower);
		device.enableControl();
	}

	// front left, back left, front right, back right!
	public DriveBase(final boolean usePID, final Integer... ports) {
		if (ports.length != 4) {
			throw new RuntimeException("Invalid amount of ports passed to DriveBase::DriveBase!");
		}

		m_driveTalons = Arrays.asList(ports).stream()
				.map(CANTalon::new)
				.collect(Collectors.toList());

		m_talonLeftMaster = m_driveTalons.get(0);
		m_talonLeftSlave = m_driveTalons.get(1);
		m_talonRightMaster = m_driveTalons.get(2);
		m_talonRightSlave = m_driveTalons.get(3);

		m_driveBase = UnfilteredRobotDrive.of(m_talonLeftMaster, m_talonRightMaster);

		m_driveTalons.forEach(DriveBase::configureTalonVoltage);

		configureTalonFront(m_talonLeftMaster, DriveSide.LEFT);
		configureTalonFront(m_talonRightMaster, DriveSide.RIGHT);

		configureTalonBack(m_talonLeftSlave);
		configureTalonBack(m_talonRightSlave);

		m_talonLeftMaster.setInverted(false);
		m_talonLeftSlave.setInverted(false);
		m_talonRightMaster.setInverted(true);
		m_talonRightSlave.setInverted(true);

		m_usePID = usePID;
	}

	public DriveBase(final Integer... ports) {
		this(false, ports);
	}

	public List<CANTalon> getTalons() {
		return m_driveTalons;
	}

	public RobotDrive getDriveTrain() {
		return m_driveBase;
	}

	public void drive(final double left, final double right) {
		// this is setting the setpoint
		m_driveBase.tankDrive(
				left * getTargetSpeed(DriveSide.LEFT), // left joy stick * 1024
				right * getTargetSpeed(DriveSide.RIGHT)
		);

		m_talonLeftSlave.set(m_talonLeftMaster.getDeviceID());
		m_talonRightSlave.set(m_talonRightMaster.getDeviceID());
	}

	public void setPIDEnabled(final boolean usePID) {
		m_usePID = usePID;

		final TalonControlMode desiredMasterControlMode = 
				usePID ? TalonControlMode.Speed : TalonControlMode.PercentVbus;

		m_talonLeftMaster.changeControlMode(desiredMasterControlMode);
		m_talonRightMaster.changeControlMode(desiredMasterControlMode);
	}

	public double getTargetSpeed(final DriveSide side) {
		return m_usePID ? side.targetSpeed : 1.0;
	}
}
