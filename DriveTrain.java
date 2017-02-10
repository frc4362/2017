package com.gemsrobotics.team4362.frc2017.subsystems;


import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;


@SuppressWarnings("unused")
public final class DriveTrain extends Subsystem {
	private final CANTalon m_leftFront, m_leftBack, m_rightFront, m_rightBack;
	private final RobotDrive m_driveTrain;
	private final CANTalon[] m_talons;
	
	public final String[] motorNamesForLogging = new String[]{ "Left Front", "Left Back", "Right Front", "Right Back" };
	
	private static final double
		CODES_PER_REV		  = 1440,
		INCHES_PER_FOOT 	  = 12,
		FT_PER_SEC_MAX 		  = 10,
		WHEEL_DIAMETER_INCHES = 6,
		WHEEL_CIRCUMFERENCE	  = Math.PI * WHEEL_DIAMETER_INCHES,
		SECONDS_PER_MINUTE    = 60,
		CRUISING_RPS 		  = ((FT_PER_SEC_MAX * INCHES_PER_FOOT) / (WHEEL_CIRCUMFERENCE));// * SECONDS_PER_MINUTE;

	public static final double TICKS_PER_INCH = 1440 / WHEEL_CIRCUMFERENCE;

	public enum SpeedScalePreset {
		FULL(CRUISING_RPS), // inches per second divided by the circumference of the wheel times 60 seconds/minute
		CONTROL(FULL.getTargetRPS() * 0.55),
		STOP(0);

		private final double m_targetRPS;

		SpeedScalePreset(final double RPS) {
			m_targetRPS = RPS;
		}

		public double getTargetRPS() {
			return m_targetRPS;
		}
	}

	private static final double
		kP = 1.3, 
		kI = 0.0,
		kD = 0.0,
		kF = 30.0; // used to be 30

	private static void configureTalon(final CANTalon device) {
		device.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		device.changeControlMode(TalonControlMode.Speed);
	
		device.configEncoderCodesPerRev((int) CODES_PER_REV); //drive encoder - e4t, pulses per revolution 1440
		device.configNominalOutputVoltage(+0.0f, -0.0f);
		device.configPeakOutputVoltage(+12.0f, -12.0f);

		// device.setProfile(0); // not sure what this does, that's why it's probably commented out.
		device.setP(kP);
		device.setI(kI);
		device.setD(kD);
		device.setF(kF);

		device.enableControl();
	}

	public DriveTrain(final int fl, final int bl, final int fr, final int br) {
		m_leftFront = new CANTalon(fl);
		m_leftFront.setInverted(true);
		m_leftFront.reverseSensor(true);
		configureTalon(m_leftFront);
		
		// leftback rightfront inverted is now false, normally is true
		
		m_leftBack = new CANTalon(bl);
		m_leftBack.setInverted(true);
		m_leftBack.reverseSensor(true);
		configureTalon(m_leftBack);

		m_rightFront = new CANTalon(fr);
		m_rightFront.setInverted(false);
		m_rightFront.reverseSensor(true);
		configureTalon(m_rightFront);

		m_rightBack = new CANTalon(br);
		configureTalon(m_rightBack);
		
		m_driveTrain = new RobotDrive(m_leftFront, m_leftBack, m_rightFront, m_rightBack) {  
			public void mecanumDrive_Cartesian(double x, double y, double rotation, double gyroAngle) {
				if (!kMecanumCartesian_Reported) {
			        HAL.report(tResourceType.kResourceType_RobotDrive, getNumMotors(),
			            tInstances.kRobotDrive_MecanumCartesian);
			        kMecanumCartesian_Reported = true;
			    }
			    @SuppressWarnings("LocalVariableName")
			    double xIn = x;
			    @SuppressWarnings("LocalVariableName")
			    double yIn = y;
			    // Negate y for the joystick.
			    yIn = -yIn;
			    // Compenstate for gyro angle.
			    double[] rotated = rotateVector(xIn, yIn, gyroAngle);
			    xIn = rotated[0];
			    yIn = rotated[1];
	
			    double[] wheelSpeeds = new double[kMaxNumberOfMotors];
			    wheelSpeeds[MotorType.kFrontLeft.value] = xIn + yIn + rotation;
			    wheelSpeeds[MotorType.kFrontRight.value] = -xIn + yIn - rotation;
			    wheelSpeeds[MotorType.kRearLeft.value] = -xIn + yIn + rotation;
			    wheelSpeeds[MotorType.kRearRight.value] = xIn + yIn - rotation;
	
			    // this one line cost us 9 hours
			    // kill it
			    // P L E A S E
			    // normalize(wheelSpeeds);
			    m_frontLeftMotor.set(wheelSpeeds[MotorType.kFrontLeft.value]);
			    m_frontRightMotor.set(wheelSpeeds[MotorType.kFrontRight.value]);
			    m_rearLeftMotor.set(wheelSpeeds[MotorType.kRearLeft.value]);
			    m_rearRightMotor.set(wheelSpeeds[MotorType.kRearRight.value]);
	
			    if (m_safetyHelper != null) {
			      m_safetyHelper.feed();
			    }
			}
		};
		m_driveTrain.setSafetyEnabled(true);
		
		m_talons = new CANTalon[] { m_leftFront, m_leftBack, m_rightFront, m_rightBack };
	}

    public void initDefaultCommand() {
    	//setDefaultCommand(new Drive());
    }

    public RobotDrive getDriveTrain() {
    	return m_driveTrain;
    }
    
	public CANTalon[] getTalons() {
		return m_talons;
	}
}
