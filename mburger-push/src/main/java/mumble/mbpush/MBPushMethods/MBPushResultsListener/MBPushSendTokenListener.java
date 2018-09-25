package mumble.mbpush.MBPushMethods.MBPushResultsListener;

import mumble.mbpush.Common.MBConstants.MBConstants;
import mumble.mbpush.MBPushMethods.MBPushAsyncTasks.MBPushAsyncTask_SendToken;

/**
 * Interface to use with {@link MBPushAsyncTask_SendToken (Context)}, and similar, methods,
 * send the push token
 *
 * @author Enrico Ori
 * @version {@value MBConstants#version}
 */
public interface MBPushSendTokenListener {
    void onTokenSent();
    void onTokenSentError(String error);
}
