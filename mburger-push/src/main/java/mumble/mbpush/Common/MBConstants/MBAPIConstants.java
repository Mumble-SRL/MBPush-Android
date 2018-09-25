package mumble.mbpush.Common.MBConstants;

import mumble.mbpush.MBPushMethods.MBPushAsyncTasks.MBPushAsyncTask_RegisterTopics;
import mumble.mbpush.MBPushMethods.MBPushAsyncTasks.MBPushAsyncTask_SendToken;
import mumble.mbpush.MBPushMethods.MBPushAsyncTasks.MBPushAsyncTask_UnregisterAllTopics;
import mumble.mbpush.MBPushMethods.MBPushAsyncTasks.MBPushAsyncTask_UnregisterTopics;

public class MBAPIConstants {

    /**Used with the API {@link MBPushAsyncTask_SendToken MBPushAsyncTask_SendToken}*/
    public static final String ACTION_SEND_TOKEN = "mumble.mburger.ACTION_SEND_TOKEN";

    /**Used with the API {@link MBPushAsyncTask_RegisterTopics MBPushAsyncTask_RegisterTopics}*/
    public static final String ACTION_REGISTER_TOPICS = "mumble.mburger.ACTION_REGISTER_TOPICS";

    /**Used with the API {@link MBPushAsyncTask_UnregisterTopics MBPushAsyncTask_UnregisterTopics}*/
    public static final String ACTION_UNREGISTER_TOPICS = "mumble.mburger.ACTION_UNREGISTER_TOPICS";

    /**Used with the API {@link MBPushAsyncTask_UnregisterAllTopics MBPushAsyncTask_UnregisterAllTopics}*/
    public static final String ACTION_UNREGISTER_ALL_TOPICS = "mumble.mburger.ACTION_UNREGISTER_ALL_TOPICS";

}
