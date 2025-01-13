package robot.system.subsystem;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import robot.config.Hardware;
import robot.config.PID;
import robot.system.System;

@Config
public class Outtake extends System {
    public enum OuttakeLevel {
        GROUND(0),
        LOW(500),
        HIGH(700); // 1470 is max extension

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

    public static double Kp = 0.001;
    public static double Ki = 0;
    public static double Kd = 0;
    private PID pid = new PID(Kp, Ki, Kd);
    private PID pidRotate = new PID(0.00075, 0, 0);
    private OuttakeLevel level = OuttakeLevel.GROUND;

    public Outtake(Hardware hardware, Telemetry telemetry) { super(hardware, telemetry); }

    public OuttakeLevel getLevel() {
        return level;
    }

    public void setToTargetPosition(OuttakeLevel level) {
        this.level = level;
        int targetPosition = level.getValue();
        int currentPosition = hardware.getOuttakeCurrentPosition();
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
        double power = pidRotate.out(targetPosition, currentPosition);
        hardware.outtakeRotateR.setPower(power);
//        hardware.outtakeRotateL.setPower(power);

//        if (hardware.getOuttakeRotatePosition() == position.getValue()) {
//            hardware.outtakeRotateR.setPower(0);
//        } else if (hardware.getOuttakeRotatePosition() < position.getValue()) {
//            hardware.outtakeRotateR.setPower(0.1);
//        } else {
//            hardware.outtakeRotateR.setPower(-0.1);
//        }
    }

    public boolean isRotateTransferPosition() {
        return hardware.getOuttakeRotatePosition() == OuttakeRotate.TRANSFER.getValue();
    }

    public boolean isRotateSubmersiblePosition() {
        return hardware.getOuttakeRotatePosition() == OuttakeRotate.SUBMERSIBLE.getValue();
    }

    public boolean isRotateWallPosition() {
        return hardware.getOuttakeRotatePosition() == OuttakeRotate.WALL.getValue();
    }

    public void linkageForward() {
        double targetPosition = 0.5;
        hardware.linkageL.setPosition(targetPosition);
//        hardware.linkageR.setPosition(targetPosition);
    }

    public void linkageBackward() {
        double targetPosition = 0.5;
//        hardware.linkageL.setPosition(targetPosition);
        hardware.linkageR.setPosition(targetPosition);
    }
}
