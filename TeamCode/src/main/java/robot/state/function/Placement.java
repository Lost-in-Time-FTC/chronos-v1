package robot.state.function;

import robot.state.GamepadToggle;
import robot.state.IOController;
import robot.state.StateFunction;
import robot.system.subsystem.Outtake;

public class Placement implements StateFunction {
    public static void run(IOController ioController) {
        ioController.getOuttake().setToTargetPosition(ioController.getOuttake().getLevel());

        if (ioController.getCurrentGamepad2().dpad_up && !ioController.getPreviousGamepad2().dpad_up) {
            switch (ioController.getOuttake().getLevel()) {
                case GROUND:
                    ioController.getOuttake().setLevel(Outtake.OuttakeLevel.LOW);
                    break;
                case LOW:
                    ioController.getOuttake().setLevel(Outtake.OuttakeLevel.HIGH);
                    break;
                case HIGH:
                    break;
            }
        }

        if (ioController.getCurrentGamepad2().dpad_down && !ioController.getPreviousGamepad2().dpad_down) {
            switch (ioController.getOuttake().getLevel()) {
                case GROUND:
                    break;
                case LOW:
                    ioController.getOuttake().setLevel(Outtake.OuttakeLevel.GROUND);
                    break;
                case HIGH:
                    ioController.getOuttake().setLevel(Outtake.OuttakeLevel.LOW);
                    break;
            }
        }

        /* TODO:
            Y (toggle) -> During the PLACEMENT state, toggling this button will allow the linkage
            outtake servos to cycle between LINKAGE_FRONT and LINKAGE_BACK values, for either
            placing something in front or in the back of the robot. When it is at LINKAGE_FRONT, the
            outtake rotation servos are set to a specific LINKAGE_FRONT value. When it is at
            LINKAGE_BACK, the outtake rotation servos are set to a specific LINKAGE_BACK value.

           TODO:
            Left Bumper (toggle) -> This is for placing on the submersible rungs. The linkage
            outtake position must be set to LINKAGE_FRONT, when the left bumper is pressed
            initially, the robot state changes to SUBMERSIBLE_FRONT and the outtake rotation servos
            rotate to a specific SUBMERSIBLE_FRONT value for placement. When the bumper is pressed
            again, it will change back to PLACEMENT state and rotate the outtake rotate servos to
            the original position.

           TODO:
            Right Bumper (toggle) -> This toggles between opening and closing the outtake pincer.
         */
    }
}
