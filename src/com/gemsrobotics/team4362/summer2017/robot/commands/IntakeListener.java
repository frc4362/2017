package com.gemsrobotics.team4362.summer2017.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

import com.ctre.CANTalon;

public class IntakeListener extends Command {
	private final double EJECT_SPEED = 0.40;

	private int m_runTicks;
	private boolean m_pressed, m_lastPressed;

	private final XboxController m_controller;
	private final CANTalon m_intakeTalon;

	public IntakeListener(final XboxController controller, final CANTalon intake) {
		m_controller = controller;
		m_intakeTalon = intake;
	}

	public void initialize() {
		m_runTicks = 0;
	}

    protected void execute() {
    	m_pressed = m_controller.getBButton();

		if (m_runTicks > 0) {
			m_intakeTalon.set(EJECT_SPEED);
			m_runTicks--;
		} else {
    		m_intakeTalon.set((m_controller.getTriggerAxis(Hand.kLeft) - m_controller.getTriggerAxis(Hand.kRight)) / 2);
    	}

    	if (m_pressed && (m_pressed != m_lastPressed)) {
    		// charges it for one full second of eject
			m_runTicks += 20;
		}

		m_lastPressed = m_pressed;
    }

    protected boolean isFinished() {
        return false;
    }
}
