package robot.state.function;

import robot.state.GamepadToggle;
import robot.state.IOController;
import robot.state.StateFunction;

public class Idle implements StateFunction {
    public static void run(IOController ioController) {
        if (ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.A)) {
            ioController.getIntake().extend();
            ioController.getIntake().pincerOpen();
            ioController.getIntake().twistVertical();
            ioController.getIntake().rotateDown();
            ioController.setState(IOController.State.PICKUP);
        }
    }
}
