package com.gemsrobotics.team4362.summer2017.robot;

import java.util.Objects;

import com.gemsrobotics.team4362.summer2017.robot.commands.DelayedClose;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.TwoWheelShooter.ShooterPreset;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.FeedTrigger;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.PassiveGearIntake.Position;
import com.gemsrobotics.util.joy.Gembutton;
import com.gemsrobotics.util.joy.Gemstick;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Scheduler;

public final class OperatorInterface {
	private static OperatorInterface INSTANCE = null;
	
	public static OperatorInterface getInstance() {
		if (Objects.isNull(INSTANCE)) {
			INSTANCE = new OperatorInterface();
		}

		return INSTANCE;
	}

	// controller buttons
	private Gembutton
		m_intakeUpButton,
		m_intakeDownButton,
		m_intakeEjectButton,
		m_gearHolderOpenButton,
		m_spinButton,
		m_shootButton;

	// drive stick buttons
	private Gembutton
		m_shiftUpButton,
		m_shiftDownButton;

	public void bindControls(
			final Gemstick driveStickLeft,
			final Gemstick driveStickRight,
			final XboxController controller
	) {
		m_intakeUpButton = new Gembutton(controller, 4);
		m_intakeDownButton = new Gembutton(controller, 3);
		m_intakeEjectButton = new Gembutton(controller, 2);

		m_intakeUpButton.whenPressed(Hardware.getInstance().getFloorIntake()::retract);
		m_intakeDownButton.whenPressed(Hardware.getInstance().getFloorIntake()::deploy);
		// there is a separate listener to handle actually ejecting when this is pressed
		m_intakeEjectButton.whenPressed(Hardware.getInstance().getFloorIntake()::deploy);

		m_gearHolderOpenButton = new Gembutton(controller, 10);

		m_gearHolderOpenButton.whenPressed(() -> {
			Hardware.getInstance().getPassiveIntake().set(Position.OPENED);
		});

		m_gearHolderOpenButton.whenReleased(() -> {
			Hardware.getInstance().getPassiveIntake().set(Position.CLOSED);
		});

		m_shiftUpButton = new Gembutton(driveStickRight, 1);
		m_shiftDownButton = new Gembutton(driveStickLeft, 1);

		m_shiftUpButton.whenPressed(() -> {
			Hardware.getInstance().getShifter().set(DoubleSolenoid.Value.kForward);
		});

		m_shiftDownButton.whenPressed(() -> {
			Hardware.getInstance().getShifter().set(DoubleSolenoid.Value.kReverse);
		});
		
		m_spinButton = new Gembutton(controller, 5);
		m_shootButton = new Gembutton(controller, 6);

		// pls read Gembutton.java to understand this
		// its not that hard, I promise

		// left trigger behavior
		m_spinButton.whileHeld(() -> Hardware.getInstance().getShooter().set(ShooterPreset.LONG));
		m_spinButton.whenReleasedUnless(m_shootButton::get, () -> Hardware.getInstance().getShooter().set(ShooterPreset.OFF));

		// right trigger behavior
		m_shootButton.whileHeld(Hardware.getInstance().getGuard()::open);
		m_shootButton.whileHeldIfElse(
				Hardware.getInstance().getShooter()::isOn,
				() -> {
					final double val = controller.getX(Hand.kLeft);

					if (val > 0.7) {
						Hardware.getInstance().getTrigger().set(FeedTrigger.Position.RIGHT_DOWN);
					} else if (val < -.7) {
						Hardware.getInstance().getTrigger().set(FeedTrigger.Position.LEFT_DOWN);
					} else {
						Hardware.getInstance().getTrigger().set(FeedTrigger.Position.DOWN);
					}
				},
				() -> Hardware.getInstance().getShooter().set(ShooterPreset.LONG)
		);

		m_shootButton.whenReleasedUnless(m_spinButton::get, () -> {
			Hardware.getInstance().getShooter().set(ShooterPreset.OFF);
		});

		m_shootButton.whenReleased(() -> {
			Hardware.getInstance().getTrigger().set(FeedTrigger.Position.UP);
			Scheduler.getInstance().add(new DelayedClose(controller, Hardware.getInstance().getGuard(), 5000));
		});
	}
}
