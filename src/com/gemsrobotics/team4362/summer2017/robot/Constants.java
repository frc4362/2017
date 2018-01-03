package com.gemsrobotics.team4362.summer2017.robot;

public final class Constants {
	private Constants() {}

	public static final double 
		COUNTS_PER_ROTATION = 1024,
		WHEEL_RADIUS = 2,
		WHEEL_DIAMETER = 2 * WHEEL_RADIUS,
		WHEEL_CIRCUMFERENCE_INCHES = Math.PI * WHEEL_DIAMETER,
		COUNTS_PER_INCH = COUNTS_PER_ROTATION / WHEEL_CIRCUMFERENCE_INCHES;
}
