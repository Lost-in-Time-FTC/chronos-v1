package robot.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import robot.config.Hardware;
import robot.state.IOController;
import robot.state.function.Placement;
import robot.system.subsystem.Drive;
import robot.system.subsystem.Intake;
import robot.system.subsystem.Outtake;

@TeleOp(name = "Tele", group = "Iterative OpMode")
public class Tele extends OpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    private Hardware hardware;
    private IOController ioController;
    private Drive drive;
    private Intake intake;
    private Outtake outtake;

    @Override
    public void init() {
        hardware = new Hardware(hardwareMap);

        drive = new Drive(hardware, telemetry, gamepad1);

        intake = new Intake(hardware, telemetry);
        outtake = new Outtake(hardware, telemetry);
        ioController = new IOController(intake, outtake, this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        drive.run();
//        try {
//            ioController.getPreviousGamepad2().copy(ioController.getCurrentGamepad2());
//            ioController.getCurrentGamepad2().copy(this.gamepad2);
//        } catch (Exception ignored) {}
//
//        Placement.run(ioController);

        ioController.loop();

//        hardware.linkageR.setPosition(-.9); // R backward 0.5
//        hardware.linkageL.setPosition(0.5); // L forward 0.5
//        outtake.rotate(Outtake.OuttakeRotate.TRANSFER);

        telemetry.addData("Status", "Run Time: %s", runtime.toString());
        telemetry.addData("State", ioController.getState());
        telemetry.addData("Intake Position", hardware.intake.getCurrentPosition());
        telemetry.addData("Intake Power", hardware.intake.getPower());
        telemetry.addData("Intake RotateL power", hardware.intakeRotateL.getPower());
        telemetry.addData("Intake RotateR power", hardware.intakeRotateR.getPower());
        telemetry.addData("Rotate Down", intake.isRotateDown());
        telemetry.addData("linkageR Pos", hardware.linkageR.getPosition());
        telemetry.addData("linkageL Pos", hardware.linkageL.getPosition());
        telemetry.addData("OuttakeRotatePos", hardware.getOuttakeRotatePosition());
        telemetry.addData("OuttakeRotateL Power", hardware.outtakeRotateL.getPower());
        telemetry.addData("OuttakeRotateR Power", hardware.outtakeRotateR.getPower());
        telemetry.addData("OuttakeL Pos", hardware.outtakeL.getCurrentPosition());
        telemetry.addData("Outtake Level", outtake.getLevel());
        telemetry.addData("Outtake Power L", hardware.outtakeL.getPower());
        telemetry.addData("Outtake Power R", hardware.outtakeR.getPower());
        telemetry.update();
    }
}
