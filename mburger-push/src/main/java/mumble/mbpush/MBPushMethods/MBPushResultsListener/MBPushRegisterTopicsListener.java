package mumble.mbpush.MBPushMethods.MBPushResultsListener;

import mumble.mbpush.Common.MBConstants.MBConstants;
import mumble.mbpush.MBPushMethods.MBPushAsyncTasks.MBPushAsyncTask_RegisterTopics;

/**
 * Interface to use with {@link MBPushAsyncTask_RegisterTopics (Context)}, and similar, methods,
 * send the push token
 *
 * @author Enrico Ori
 * @version {@value MBConstants#version}
 */
public interface MBPushRegisterTopicsListener {
    void onTopicsRegistered();

    void onTopicsRegisteredError(String error);
}
