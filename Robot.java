package com.gemsrobotics.team4362.frc2017;


import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.ctre.CANTalon.FeedbackDevice;
import com.gemsrobotics.team4362.frc2017.Robot.Hardware;
import com.gemsrobotics.team4362.frc2017.commands.Drive;
import com.gemsrobotics.team4362.frc2017.subsystems.DriveTrain;
import com.gemsrobotics.team4362.frc2017.subsystems.OmniPositioningSystem;
import com.gemsrobotics.team4362.frc2017.subsystems.TwoWheelShooter;
import com.gemsrobotics.team4362.frc2017.subsystems.TwoWheelShooter.ShooterPreset;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * https://i.redd.it/mf55wyr77icy.png
 * 
 * @author Ethan
 */
public class Robot extends IterativeRobot {
	public static final class Hardware {
		public static final AHRS Nav = new AHRS(SerialPort.Port.kMXP);
		public static final DriveTrain Chassis = new DriveTrain(1, 15, 0, 14);
		public static final TwoWheelShooter Shooter = new TwoWheelShooter(21, 25);
		public static final OmniPositioningSystem Positioner = new OmniPositioningSystem(2, 3, 0, 1);
	}
	
	final Command[] teleopCommands = new Command[] {
			new Drive().withDeadbandX_Y(0.2).withDeadbandZ(0.1)
	};

	final SendableChooser<Boolean> shooterEnabled = new SendableChooser<Boolean>() {{
		addDefault("off", false);
		addObject("on", true);
	}};

	public void teleopInit() {
		Stream.of(teleopCommands).forEach(Scheduler.getInstance()::add);
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		if (Options.ENABLE_LOG_NAV_ANGLE) {
			SmartDashboard.putNumber("Nav Angle", Hardware.Nav.getAngle());
		}

		if (Options.ENABLE_SD_SHOOTER) {
			Hardware.Shooter.set(ShooterPreset.ofBool(shooterEnabled.getSelected()));
		}

		if (Options.ENABLE_LOG_ENCODER_SPEED) {
			IntStream.range(0, 4).forEach(
				i -> {
					SmartDashboard.putNumber(
							Hardware.Chassis.motorNamesForLogging[i], 
							Hardware.Chassis.getTalons()[i].getSpeed()
					);	
				}
			);
		}

		if (Options.ENABLE_SENSOR_PRESENCE_LOGGING) {
			Stream.of(FeedbackDevice.values()).forEach(
				sensor -> Stream.of(Hardware.Chassis.getTalons()).forEach(
					talon -> SmartDashboard.putString(
						talon.toString() + " has " + sensor.name(), 
						talon.isSensorPresent(sensor).name()
					)
				)
			);
		}
	}
}
