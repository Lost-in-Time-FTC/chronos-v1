package robot.state.function;

import robot.state.IOController;
import robot.state.StateFunction;
import robot.system.subsystem.Outtake;


public class HandoffInitializing implements StateFunction {
    public static void run(IOController ioController) {
        ioController.getIntake().rotateUp();
        ioController.getIntake().twistHorizontal();
        ioController.getOuttake().linkageForward();
        ioController.getOuttake().pincerOpen();
        ioController.getOuttake().twistHorizontal();
        ioController.getOuttake().rotate(Outtake.OuttakeRotate.TRANSFER);

        if (ioController.getOuttake().isRotated()) {
            ioController.setState(IOController.State.HANDOFF_READY);
        }
    }
}
