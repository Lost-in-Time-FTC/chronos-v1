package robot.system;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import robot.config.Hardware;
import robot.state.IOController;

public abstract class System {
    public Hardware hardware;
    public Telemetry telemetry;

    public System(Hardware hardware, Telemetry telemetry) {
        this.hardware = hardware;
        this.telemetry = telemetry;
    }
}
