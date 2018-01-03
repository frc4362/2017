package com.gemsrobotics.team4362.summer2017.robot.commands;

import com.gemsrobotics.team4362.summer2017.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public final class ClimberListener extends Command {
	private Climber m_climber;
	private XboxController m_controller;
	
    public ClimberListener(final Climber climber, final XboxController controller) {
    	m_climber = climber;
    	m_controller = controller;
    }

    protected void execute() {
    	double speedCanditate = m_controller.getY(Hand.kRight);

    	if (speedCanditate > -0.2) {
    		speedCanditate = 0;
    	}

    	m_climber.set(-speedCanditate);
    }

    protected boolean isFinished() {
        return false;
    }
}
