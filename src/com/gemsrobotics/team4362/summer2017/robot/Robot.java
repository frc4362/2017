package com.gemsrobotics.team4362.summer2017.robot;

import java.util.Arrays;
import java.util.List;

import com.gemsrobotics.team4362.summer2017.robot.commands.RedBoilerSideGearAuton;
import com.gemsrobotics.team4362.summer2017.robot.commands.BlueBoilerSideGearAuton;
import com.gemsrobotics.team4362.summer2017.robot.commands.CenterGearAuton;
import com.gemsrobotics.team4362.summer2017.robot.commands.ClimberListener;
import com.gemsrobotics.team4362.summer2017.robot.commands.Drive;
import com.gemsrobotics.team4362.summer2017.robot.commands.FarSideGearAuton;
import com.gemsrobotics.team4362.summer2017.robot.commands.IntakeListener;
import com.gemsrobotics.team4362.summer2017.robot.commands.LogDriveTalons;
import com.gemsrobotics.util.ExtendedIterativeRobot;
import com.gemsrobotics.util.joy.Gemstick;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.gemsrobotics.util.command.CommandBuilders.autonOf;

/**
 * https://i.redd.it/mf55wyr77icy.png
 * @author Ethan
 */
public final class Robot extends ExtendedIterativeRobot {
	public final Gemstick 
		m_driveStickLeft  = new Gemstick("Left Stick",  0),
		m_driveStickRight = new Gemstick("Right Stick", 1);
	public final XboxController 
		m_controller = new XboxController(2);

	private final SendableChooser<Command> autonChooser = new SendableChooser<Command>() {{
		addDefault("none", autonOf());
		addObject("center gear auton", new CenterGearAuton());
		addObject("BLU far side gear", new FarSideGearAuton(FarSideGearAuton.Alliance.BLUE));
		addObject("RED far side gear", new FarSideGearAuton(FarSideGearAuton.Alliance.RED));
		addObject("BLU boiler side auto", new BlueBoilerSideGearAuton(BlueBoilerSideGearAuton.Alliance.BLUE));
		addObject("RED boiler side auto", new RedBoilerSideGearAuton(RedBoilerSideGearAuton.Alliance.RED));
	}};

	@Override
	public List<Command> getTeleopCommands() {
		return Arrays.<Command>asList(
				new Drive(
						Hardware.getInstance().getDrive(), 
						m_driveStickLeft,
						m_driveStickRight
				),
				new LogDriveTalons(Hardware.getInstance().getDrive()),
				new IntakeListener(
						m_controller,
						Hardware.getInstance().getFloorIntakeTalon()
				),
				new ClimberListener(
						Hardware.getInstance().getClimber(),
						m_controller
				)
		);
	}

	public void robotInit() {
		SmartDashboard.putData("spicy auton selector", autonChooser);
	}

	public void autonomousInit() {
		Hardware.getInstance().getDrive().setPIDEnabled(true);
		Scheduler.getInstance().add(autonChooser.getSelected());
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("nav val", Hardware.getInstance().getMXP().getAngle());
	}

	@Override
	public void teleopStart() {
		OperatorInterface.getInstance().bindControls(
				m_driveStickLeft, 
				m_driveStickRight, 
				m_controller
		);

		Hardware.getInstance().getDrive().setPIDEnabled(false);
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
}
