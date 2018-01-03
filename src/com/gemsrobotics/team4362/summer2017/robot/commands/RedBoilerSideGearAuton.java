package com.gemsrobotics.team4362.summer2017.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

import static com.gemsrobotics.util.command.CommandBuilders.commandOf;

import com.gemsrobotics.team4362.summer2017.robot.Hardware;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.FeedTrigger;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.PassiveGearIntake;
import com.gemsrobotics.team4362.summer2017.robot.subsystems.TwoWheelShooter.ShooterPreset;

public class RedBoilerSideGearAuton extends CommandGroup {
	public enum Alliance {
		// need to verify these times
		RED(1), BLUE(-1);
		
		private final int m_multiplier;
		
		Alliance(final int multiplier) {
			m_multiplier = multiplier;
		}
		
		public int getMultiplier() {
			return m_multiplier;
		}
	}

    public RedBoilerSideGearAuton(final Alliance alliance) {
	    	addSequential(commandOf(() -> Hardware.getInstance().getPassiveIntake().set(PassiveGearIntake.Position.OPENED)));

	    	addSequential(commandOf(Hardware.getInstance().getGuard()::open));

	    	addSequential(new SingleShoot());
	    	
	    	addSequential(commandOf(() -> Hardware.getInstance().getTrigger().set(FeedTrigger.Position.UP)));

	    	addSequential(commandOf(Hardware.getInstance().getGuard()::close));

	    	addSequential(commandOf(() -> Hardware.getInstance().getShooter().set(ShooterPreset.OFF)));

	    	addSequential(commandOf(() -> Hardware.getInstance().getPassiveIntake().set(PassiveGearIntake.Position.CLOSED)));

	    	addSequential(new Wait(250));

	    	// this is when we start moving
	    	addSequential(new DriveDistance(Hardware.getInstance().getDrive(), 8 * -alliance.getMultiplier(), 0.4 * -alliance.getMultiplier()));
	    	
	    	addSequential(new TurnDegrees(90 * -alliance.getMultiplier()));
	    	
	    	addSequential(new DriveDistance(Hardware.getInstance().getDrive(), 70 * alliance.getMultiplier(), 0.7 * -alliance.getMultiplier()));

//	    	addSequential(new DriveDistance(alliance.getDistance(), 0.28)); 
//
//	    	addSequential(new TurnDegrees(alliance.getSecondTurn() * -alliance.getMultiplier(), 0.1)); // point itself in the direction of the lift
//
//	    	addSequential(new CenterOnLift(3000));
//
//	    	addSequential(commandOf(() -> Hardware.GearServos.set(GearHopper.Position.OPENED)));
//	    	
//	    	addSequential(new Wait(250));
//
//	    	addSequential(new PlaceOnLiftMove());
    }
}
 