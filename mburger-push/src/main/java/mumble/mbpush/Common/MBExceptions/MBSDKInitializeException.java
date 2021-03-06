package mumble.mbpush.Common.MBExceptions;

import mumble.mbpush.Common.MBConstants.MBConstants;

/**
 * Exception called when doing an operation before SDK initilization (put in API KEY).
 *
 * @author Enrico Ori
 * @version {@value MBConstants#version}
 */
public class MBSDKInitializeException extends RuntimeException {
    public MBSDKInitializeException(String message) {
        super(message);
    }
}