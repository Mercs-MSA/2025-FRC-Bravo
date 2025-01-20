package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;
import frc.robot.Constants;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.Elevator1Constants;
import frc.robot.Constants;

public class CommandClimbToggle extends Command {
    private Climber m_Climber = new Climber(Elevator1Constants.attached);


    public CommandClimbToggle() {
        addRequirements(m_Climber);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command

        if (Constants.ScoringConstants.ScoringStage.canClimb()){
            if (Constants.isWithinTol(Constants.ClimberConstants.positionUp, m_Climber.GetPosition(), Constants.ClimberConstants.climberTol)){
                m_Climber.climberGoToPosition(ClimberConstants.positionDown);

            }

            else {
                m_Climber.climberGoToPosition(ClimberConstants.positionDown);

            }
        }
        
    }

    @Override 
    public void execute() {
        // This is where you put stuff that happens while the command is happening, it will loop here over and over
    }

    @Override 
    public void end(boolean interrupted) {
        // This is where you put stuff that happens when the command ends
    }

    @Override 
    public boolean isFinished() {
        
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        // In this example, it will just instantly come out as true and stop the command as soon as it's called.
        // System.out.println("isf");
        // System.out.println(Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol));
        // return Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol);
        return true;
    }


}
