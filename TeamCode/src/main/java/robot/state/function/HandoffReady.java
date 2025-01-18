package robot.state.function;

import robot.state.IOController;
import robot.state.StateFunction;


public class HandoffReady implements StateFunction {
    public static void run(IOController ioController) {
        ioController.getOuttake().pincerClose();
        ioController.getIntake().pincerOpen();
        ioController.setState(IOController.State.BUCKET_PLACEMENT);
    }
}
