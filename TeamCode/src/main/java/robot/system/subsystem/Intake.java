package robot.system.subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import robot.config.Hardware;
import robot.config.PID;
import robot.system.System;

public class Intake extends System {
    private PID pid = new PID(0.005, 0, 0);
    private boolean isRotateUp = true;
    private boolean isRotateDown = false;

    public Intake(Hardware hardware, Telemetry telemetry) {
        super(hardware, telemetry);
    }

    public void extend() {
        int targetPosition = 120;
        int currentPosition = hardware.getIntakeCurrentPosition();
        double power = pid.out(targetPosition, currentPosition);
        hardware.intake.setPower(power);
    }

    public void retract() {
        int targetPosition = -20;
        int currentPosition = hardware.getIntakeCurrentPosition();
        double power = pid.out(targetPosition, currentPosition);
        hardware.intake.setPower(power);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule to run after 2 seconds
        scheduler.schedule(() -> {
            hardware.intake.setPower(-0.25);
        }, 1, TimeUnit.SECONDS);

        scheduler.shutdown();
    }

    public boolean isExtended() {
        int targetPosition = 120;
        return hardware.getIntakeCurrentPosition() >= targetPosition - 5;
    }

    public boolean isRetracted() {
        int zeroPositionThreshold = -20;
        return hardware.getIntakeCurrentPosition() <= zeroPositionThreshold + 5;
    }

    public void pincerOpen() {
        double targetPosition = 0.4;
        hardware.intakePincer.setPosition(targetPosition);
    }

    public void pincerClose() {
        double targetPosition = 0;
        hardware.intakePincer.setPosition(targetPosition);
    }

    public void twistHorizontal() {
        double targetPosition = 0.5;
        hardware.intakeTwist.setPosition(targetPosition);
    }

    public void twistVertical() {
        double targetPosition = 0.15;
        hardware.intakeTwist.setPosition(targetPosition);
    }

    public void rotateUp() {
        if (isRotateUp) { return; }

        int powerMilliSeconds = 1500;

        hardware.intakeRotateL.setPower(1);
        hardware.intakeRotateR.setPower(-1);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule to run after x milliseconds
        scheduler.schedule(() -> {
            hardware.intakeRotateL.setPower(0);
            hardware.intakeRotateR.setPower(0);
            isRotateUp = true;
            isRotateDown = false;
        }, powerMilliSeconds, TimeUnit.MILLISECONDS);

        scheduler.shutdown();
    }

    public void rotateDown() {
        if (isRotateDown) { return;}

        int powerMilliSeconds = 1500;

        hardware.intakeRotateL.setPower(-1);
        hardware.intakeRotateR.setPower(1);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule to run after x milliseconds
        scheduler.schedule(() -> {
            hardware.intakeRotateL.setPower(0);
            hardware.intakeRotateR.setPower(0);
            isRotateDown = true;
            isRotateUp = false;
        }, powerMilliSeconds, TimeUnit.MILLISECONDS);

        scheduler.shutdown();
    }

    public boolean isRotateUp() {
        return this.isRotateUp;
    }

    public boolean isRotateDown() {
        return this.isRotateDown;
    }
}
