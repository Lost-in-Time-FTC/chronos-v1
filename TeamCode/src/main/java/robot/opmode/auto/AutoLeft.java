package robot.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import robot.config.Hardware;
import robot.system.subsystem.Intake;
import robot.system.subsystem.Outtake;

@Autonomous(name = "Auto Left", group = "Iterative OpMode")
public class AutoLeft extends OpMode {
    private Hardware hardware;
    private Intake intake;
    private Outtake outtake;

    private ElapsedTime timer;
    private int autoState;

    @Override
    public void init() {
        hardware = new Hardware(hardwareMap);

        intake = new Intake(hardware, telemetry);
        outtake = new Outtake(hardware, telemetry);

        timer = new ElapsedTime();
        autoState = 0; // Start at the first state

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        timer.reset(); // Reset the timer at the start
    }

    @Override
    public void loop() {
        intake.retract();
        outtake.rotate(outtake.getRotatePosition());
        outtake.setToTargetPosition(outtake.getLevel());

        switch (autoState) {
            case 0:
                outtake.pincerClose();
                outtake.twistHorizontal();
                outtake.setRotatePosition(Outtake.OuttakeRotate.SUBMERSIBLE);
                outtake.setLevel(Outtake.OuttakeLevel.HIGH_RUNG);
                outtake.linkageForward();

                hardware.moveForward(0.5);
                if (timer.seconds() > 1.5) {
                    hardware.stopDrive();
                    timer.reset();
                    autoState++;
                }
                break;

            case 1:
                outtake.pincerOpen();
                outtake.setLevel(Outtake.OuttakeLevel.WALL);
//                outtake.setRotatePosition(Outtake.OuttakeRotate.TRANSFER);


                if (timer.seconds() > 1.0) {
                    telemetry.addData("Running Down", true);
                    outtake.twistHorizontal();
                    outtake.setRotatePosition(Outtake.OuttakeRotate.TRANSFER);
                    outtake.setLevel(Outtake.OuttakeLevel.GROUND);
                    outtake.linkageBackward();
                    timer.reset();
                    autoState++;
                }
                break;

            case 2:
                hardware.moveBackward(0.5);
                if (timer.seconds() > 2.0) {
                    hardware.stopDrive();
                    timer.reset();
                    autoState++;
                }
                break;

            case 3:
                hardware.strafeLeft(0.5);

                if (timer.seconds() > 2.0) {
                    hardware.stopDrive();
                    timer.reset();
                    autoState++;
                }
                break;
        }

        telemetry.addData("Auto State", autoState);
        telemetry.addData("Time", timer.seconds());
        telemetry.update();
    }
}
