package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.Elevator1Constants;
import frc.robot.subsystems.Elevator1;
import frc.robot.subsystems.Elevator2;

public class CommandElevatorToStage extends Command {
    private Elevator1 m_Elevator1 = new Elevator1(Elevator1Constants.attached);
    private Elevator2 m_Elevator2 = new Elevator2(Elevator1Constants.attached);

    private double pos;

    public CommandElevatorToStage() {
        // if (Constants.ScoringConstants.ScoringStage.canElev())
        // {
            addRequirements(m_Elevator1, m_Elevator2);
        // }
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command

        // if (Constants.ScoringConstants.ScoringStage.canElev()){
        this.pos = Constants.ScoringConstants.ScoringStage.getElevatorRotations();
        System.out.println(pos);
        m_Elevator1.motorToPosMM(pos);
        m_Elevator2.motorToPosMM(pos);
        // }
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
