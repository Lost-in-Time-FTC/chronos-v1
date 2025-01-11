package robot.system.subsystem;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import robot.config.Hardware;
import robot.config.PID;
import robot.state.IOController;
import robot.system.System;

public class Outtake extends System {
    public enum OuttakeLevel {
        GROUND(0),
        LOW(50),
        HIGH(100);

        private final int value;

        OuttakeLevel(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum OuttakeRotate {
        TRANSFER(0),
        SUBMERSIBLE(50),
        WALL(4);

        private final int value;

        OuttakeRotate(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private PID pid = new PID(0.005, 0, 0);
    private OuttakeLevel level = OuttakeLevel.GROUND;

    public Outtake(Hardware hardware, Telemetry telemetry) { super(hardware, telemetry); }

    public OuttakeLevel getLevel() {
        return level;
    }

    public void setToTargetPosition(OuttakeLevel level) {
        int targetPosition = level.getValue();
        int currentPosition = hardware.outtakeL.getCurrentPosition();
        double power = pid.out(targetPosition, currentPosition);
        hardware.outtakeL.setPower(power);
        hardware.outtakeR.setPower(power);
    }

    public void pincerOpen() {
        double targetPosition = 0;
        hardware.outtakePincer.setPosition(targetPosition);
    }

    public void pincerClose() {
        double targetPosition = 1;
        hardware.outtakePincer.setPosition(targetPosition);
    }

    public void twistHorizontal() {
        double targetPosition = 0;
        hardware.outtakeTwist.setPosition(targetPosition);
    }

    public void twistVertical() {
        double targetPosition = 0.5;
        hardware.outtakeTwist.setPosition(targetPosition);
    }

    public void rotate(OuttakeRotate position) {
        int targetPosition = position.getValue();
        int currentPosition = hardware.getOuttakeRotatePosition();
        double power = pid.out(targetPosition, currentPosition);
        hardware.outtakeRotateR.setPower(power);
        hardware.outtakeRotateL.setPower(power);
    }

    public boolean isRotateTransferPosition() {
        return hardware.outtakeR.getCurrentPosition() == OuttakeRotate.TRANSFER.getValue();
    }

    public boolean isRotateSubmersiblePosition() {
        return hardware.outtakeR.getCurrentPosition() == OuttakeRotate.SUBMERSIBLE.getValue();
    }

    public boolean isRotateWallPosition() {
        return hardware.outtakeR.getCurrentPosition() == OuttakeRotate.WALL.getValue();
    }

    public void linkageForward() {
        double targetPosition = 0.75;
        hardware.linkageL.setPosition(targetPosition);
        hardware.linkageR.setPosition(targetPosition);
    }

    public void linkageBackward() {
        double targetPosition = 0;
        hardware.linkageL.setPosition(targetPosition);
        hardware.linkageR.setPosition(targetPosition);
    }
}
