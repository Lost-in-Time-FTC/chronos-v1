package robot.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import robot.config.Hardware;
import robot.state.IOController;
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
        ioController.loop();

        telemetry.addData("Status", "Run Time: %s", runtime.toString());
        telemetry.update();
    }
}
