package com.gemsrobotics.team4362.summer2017.robot.commands;

import com.gemsrobotics.team4362.summer2017.robot.Hardware;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.PassiveGearIntake;

import static com.gemsrobotics.util.command.CommandBuilders.commandOf;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FarSideGearAuton extends CommandGroup {
	public enum Alliance {
		RED(1), BLUE(-1);

		private final int m_multiplier;

		Alliance(final int multiplier) {
			m_multiplier = multiplier;
		}

		public int getMultiplier() {
			return m_multiplier;
		}
	}

    public FarSideGearAuton(final Alliance alliance) {
    	addSequential(new AutonLoadGear());

		addSequential(new DriveDistance(Hardware.getInstance().getDrive(), -68, -0.21));

		addSequential(new TurnDegrees(46 * alliance.getMultiplier()));

		addSequential(commandOf(() -> Hardware.getInstance().getPassiveIntake().set(PassiveGearIntake.Position.OPENED)));

		addSequential(new DriveDistance(Hardware.getInstance().getDrive(), -5, -0.2));
    }
}
