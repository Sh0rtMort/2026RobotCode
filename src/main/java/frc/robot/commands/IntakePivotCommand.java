package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake;

public class IntakePivotCommand extends Command{

    //TODO: make things simple, its not worth making multiple commands when something like this can be done

    //using a command group with a passable variable allows for less redundant usage
    private intake intake;
    private double setpoint;

    //look up a video on how to tune these
    private PIDController pidController = new PIDController(0, 0, 0);

    public IntakePivotCommand(intake intake, double setpoint) {
        this.intake = intake;
        this.setpoint = setpoint;

        //this is how much the intake can be off by before ending
        pidController.setTolerance(0);

        addRequirements(intake);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double speed = pidController.calculate(intake.getPivotPosition(), setpoint);

        intake.setPivotSpeedPass(speed);

        // SmartDashboard.putNumber("intake PID Error", pidController.getErrorTolerance());
    }

    @Override
    public void end(boolean interrupted) {
        intake.setPivotSpeedPass(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
