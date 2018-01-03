package com.gemsrobotics.util;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;

// provides drive methods compatible with control modes other than PercentVBus
public final class UnfilteredRobotDrive extends RobotDrive {
	public static UnfilteredRobotDrive of(final CANTalon left, final CANTalon right) {
		return new UnfilteredRobotDrive(left, right);
	}

	private UnfilteredRobotDrive(
			final CANTalon frontLeftMotor,
			final CANTalon frontRightMotor
	) {
		super(frontLeftMotor, frontRightMotor);
	}

	@Override
	public void tankDrive(double leftValue, double rightValue) {
  		if (!kTank_Reported) {
  			HAL.report(tResourceType.kResourceType_RobotDrive, getNumMotors(),
  					tInstances.kRobotDrive_Tank);
  			kTank_Reported = true;
	    }

	    setLeftRightMotorOutputs(leftValue, rightValue);
  	}

	@Override
    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        if (m_rearLeftMotor == null || m_rearRightMotor == null) {
            throw new NullPointerException("Null motor provided");
        }

        if (m_frontLeftMotor != null) {
            m_frontLeftMotor.set(leftOutput * m_maxOutput);
        }
        m_rearLeftMotor.set(leftOutput * m_maxOutput);

        if (m_frontRightMotor != null) {
            m_frontRightMotor.set(rightOutput * m_maxOutput);
        }
        m_rearRightMotor.set(rightOutput * m_maxOutput);

        if (m_safetyHelper != null) {
            m_safetyHelper.feed();
        }
    }
}
