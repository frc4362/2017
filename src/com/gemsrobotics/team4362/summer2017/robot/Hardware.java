package com.gemsrobotics.team4362.summer2017.robot;

import java.util.Objects;

import com.ctre.CANTalon;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.Climber;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.DriveBase;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.FeedTrigger;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.FloorGearIntake;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.Guard;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.PassiveGearIntake;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.TwoWheelShooter;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SerialPort;

public final class Hardware {
	private static Hardware INSTANCE = null;

	private final DriveBase m_driveTrain;
	private final AHRS m_MXP;
	private final FloorGearIntake m_floorIntake;
	private final CANTalon m_floorIntakeTalon;
	private final Compressor m_compressor;
	private final PassiveGearIntake m_passiveIntake;
	private final DoubleSolenoid m_shifter;
	private final Climber m_climber;
	private final TwoWheelShooter m_shooter;
	private final FeedTrigger m_trigger;
	private final Guard m_guard;

	private Hardware() {
		m_MXP = new AHRS(SerialPort.Port.kMXP);
		m_floorIntake = new FloorGearIntake(31);
		m_floorIntakeTalon = new CANTalon(27);
		m_compressor = new Compressor(1);
		m_passiveIntake = new PassiveGearIntake(5, 6);
		m_shifter = new DoubleSolenoid(1, 0, 1);
		m_climber = new Climber(9, 10);
		m_shooter = new TwoWheelShooter(new int[] { 25, 19 }, 23, 43);
		m_trigger = new FeedTrigger(2, 3);
		m_guard = new Guard(4);
		m_driveTrain = new DriveBase(57, 58, 59, 60);
	}

	public static Hardware getInstance() {
		if (Objects.isNull(INSTANCE)) {
			INSTANCE = new Hardware();
		}

		return INSTANCE;
	}

	public DriveBase getDrive() {
		return m_driveTrain;
	}
	
	public AHRS getMXP() {
		return m_MXP;
	}
	
	public FloorGearIntake getFloorIntake() {
		return m_floorIntake;
	}
	
	public CANTalon getFloorIntakeTalon() {
		return m_floorIntakeTalon;
	}
	
	public Compressor getCompressor() {
		return m_compressor;
	}
	
	public PassiveGearIntake getPassiveIntake() {
		return m_passiveIntake;
	}

	public DoubleSolenoid getShifter() {
		return m_shifter;
	}
	
	public Climber getClimber() {
		return m_climber;
	}
	
	public TwoWheelShooter getShooter() {
		return m_shooter;
	}

	public FeedTrigger getTrigger() {
		return m_trigger;
	}
	
	public Guard getGuard() {
		return m_guard;
	}
}
