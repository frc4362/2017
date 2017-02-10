package com.gemsrobotics.team4362.frc2017;


import com.gemsrobotics.team4362.frc2017.Robot.Hardware;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;


public enum OI {
	Controls;
	
	public final Joystick Stick = new Joystick(0);

	private final Button recalibrationButton = new JoystickButton(Stick, 3);

	OI() {
		recalibrationButton.whenPressed(new InstantCommand() {
			protected void execute() {
				Hardware.Nav.reset();
			}
		});
	}
}
