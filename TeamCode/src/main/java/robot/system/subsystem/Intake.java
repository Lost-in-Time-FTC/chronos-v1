package robot.system.subsystem;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import robot.config.Hardware;
import robot.config.PID;
import robot.state.IOController;
import robot.system.System;

public class Intake extends System {
    private PID pid = new PID(0.005, 0, 0);
    private ElapsedTime rotateUpTimer = new ElapsedTime();
    private ElapsedTime rotateDownTimer = new ElapsedTime();

    public Intake(Hardware hardware, Telemetry telemetry) {
        super(hardware, telemetry);
    }

    public void extend() {
        int targetPosition = 50;
        int currentPosition = hardware.intake.getCurrentPosition();
        double power = pid.out(targetPosition, currentPosition);
        hardware.intake.setPower(power);
    }

    public void retract() {
        int targetPosition = 0;
        int currentPosition = hardware.intake.getCurrentPosition();
        double power = pid.out(targetPosition, currentPosition);
        hardware.intake.setPower(power);
    }

    public boolean isRetracted() {
        int zeroPositionThreshold = 0;
        return hardware.intake.getCurrentPosition() == zeroPositionThreshold;
    }

    public void pincerOpen() {
        double targetPosition = 0;
        hardware.intakePincer.setPosition(targetPosition);
    }

    public void pincerClose() {
        double targetPosition = 1;
        hardware.intakePincer.setPosition(targetPosition);
    }

    public void twistHorizontal() {
        double targetPosition = 0;
        hardware.intakeTwist.setPosition(targetPosition);
    }

    public void twistVertical() {
        double targetPosition = 0.5;
        hardware.intakeTwist.setPosition(targetPosition);
    }

    public void rotateUp() {
        int powerSeconds = 3;

        if (rotateUpTimer.seconds() >= powerSeconds) {
            hardware.intakeRotateL.setPower(0);
            hardware.intakeRotateR.setPower(0);
            return;
        }

        rotateUpTimer.reset();
        hardware.intakeRotateL.setPower(1);
        hardware.intakeRotateR.setPower(1);
    }

    public void rotateDown() {
        int powerSeconds = 3;

        if (rotateDownTimer.seconds() >= powerSeconds) {
            hardware.intakeRotateL.setPower(0);
            hardware.intakeRotateR.setPower(0);
            return;
        }

        rotateDownTimer.reset();
        hardware.intakeRotateL.setPower(-1);
        hardware.intakeRotateR.setPower(-1);
    }
}
