package com.gemsrobotics.team4362.summer2017.robot.commands;

import java.util.List;

import com.ctre.CANTalon;
import com.gemsrobotics.team4362.summer2017.robot.Constants;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.DriveBase;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistance extends Command {
	private static final double THRESHOLD = Constants.COUNTS_PER_INCH * 1.5;

	private CANTalon m_talonLeft, m_talonRight;
	private DriveBase m_driveTrain;
	private double m_distance, m_speedLeft, m_speedRight;
	private double m_startPosLeft, m_startPosRight, m_destinationLeft, m_destinationRight;

    public DriveDistance(final DriveBase driveTrain, double distanceInches, double speed) {
    	distanceInches *= -1;
    	speed *= -1;
    	
    	m_driveTrain = driveTrain;

    	final List<CANTalon> talons = driveTrain.getTalons();
    	m_talonLeft = talons.get(0);
    	m_talonRight = talons.get(2);

    	m_distance = distanceInches * Constants.COUNTS_PER_INCH * 4;
    	m_speedLeft = speed;
    	m_speedRight = -speed;
    }

    // left side increases as it goes further
    // this will be negative as long as it still needs to go forward
    private double getLeftError() {
    	return m_destinationLeft - m_talonLeft.getPosition();
    }

    // right side increases as it goes further
    // this will be positive as long as it still needs to go forward
    private double getRightError() {
    	return m_destinationRight - m_talonRight.getPosition();
    }

    protected void initialize() {
    	m_startPosLeft = m_talonLeft.getPosition();
    	m_startPosRight = m_talonRight.getPosition();

    	m_destinationLeft = m_startPosLeft - m_distance;
    	m_destinationRight = m_startPosRight + m_distance;
    }

    protected void execute() {
    	SmartDashboard.putNumber("left error", getLeftError());
    	SmartDashboard.putNumber("right error", getRightError());

    	SmartDashboard.putNumber("left encoder pos", m_talonLeft.getPosition());
    	SmartDashboard.putNumber("right encoder pos", m_talonRight.getPosition());

    	m_driveTrain.drive(
    			m_speedLeft * Math.signum(getLeftError()) * Math.signum(m_speedLeft), 
    			m_speedRight * Math.signum(getRightError()) * -Math.signum(m_speedRight)
    	);
    }

    protected boolean isFinished() {
    	final boolean doneLeft = Math.abs(getLeftError()) < THRESHOLD;
    	final boolean doneRight = Math.abs(getRightError()) < THRESHOLD;

//    	if (doneLeft) {
//    		m_speedLeft = -0.04 * Math.signum(m_speedLeft);
//    	}
//
//    	if (doneRight) {
//    		m_speedRight = -0.04 * Math.signum(m_speedRight);
//    	}

        return doneLeft && doneRight;
    }
    
    protected void end() {
    	m_talonLeft.set(0);
    	m_talonRight.set(0);
    }
}
