package pedroPathing.constants;


import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = .001989436789;
        ThreeWheelConstants.strafeTicksToInches = .001989436789;
        ThreeWheelConstants.turnTicksToInches = .001989436789;
        ThreeWheelConstants.leftY = 7.4;
        ThreeWheelConstants.rightY = -7.4;
        ThreeWheelConstants.strafeX = -6.6;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "fR";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "bL";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "fL";
        ThreeWheelConstants.leftEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.rightEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.REVERSE;
    }
}
