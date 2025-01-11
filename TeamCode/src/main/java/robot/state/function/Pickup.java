package robot.state.function;

import robot.state.GamepadToggle;
import robot.state.IOController;
import robot.state.StateFunction;

public class Pickup implements StateFunction {
    public static void run(IOController ioController) {
        if (ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.X)) {
            ioController.getIntake().pincerOpen();
        } else {
            ioController.getIntake().pincerClose();
        }

        if (ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.B)) {
            ioController.getIntake().twistHorizontal();
        } else {
            ioController.getIntake().twistVertical();
        }

        if (!ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.A)) {
            ioController.getIntake().retract();

            if (ioController.getIntake().isRetracted()) {
                ioController.setState(IOController.State.HANDOFF_INITIALIZING);
            }
        }
    }
}
