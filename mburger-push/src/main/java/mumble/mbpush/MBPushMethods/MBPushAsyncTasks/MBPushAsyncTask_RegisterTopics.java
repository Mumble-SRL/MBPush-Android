package mumble.mbpush.MBPushMethods.MBPushAsyncTasks;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

import mumble.mbpush.Common.MBCommonMethods;
import mumble.mbpush.Common.MBConstants.MBAPIConstants;
import mumble.mbpush.Common.MBConstants.MBApiManagerConfig;
import mumble.mbpush.Common.MBConstants.MBUserConstants;
import mumble.mbpush.MBPushData.MBTopic;
import mumble.mbpush.MBPushMethods.MBPushResultsListener.MBPushRegisterTopicsListener;
import mumble.mburger.sdk.kt.Common.MBApiManager.MBAMActivityUtils;
import mumble.mburger.sdk.kt.Common.MBApiManager.MBAPIManager4;
import mumble.mburger.sdk.kt.Common.MBApiManager.MBApiManagerUtils;

public class MBPushAsyncTask_RegisterTopics extends AsyncTask<Void, Void, Void> {

    /**
     * Context reference used to send data to Activity/Fragment
     */
    @NonNull
    private WeakReference<Context> weakContext;

    /**
     * Android device id
     */
    @NonNull
    private String device_id;

    /**
     * Topics to register on
     */
    @NonNull
    private ArrayList<MBTopic> topics;

    /**
     * If you wish to change the action that accompanies the API result
     */
    private String action = MBAPIConstants.ACTION_REGISTER_TOPICS;

    /**
     * If you wish to use a listener to retrieve the data instead of the ApiListener
     */
    private MBPushRegisterTopicsListener listener;

    private int result = MBApiManagerConfig.COMMON_INTERNAL_ERROR;
    private String error;
    private Map<String, Object> map;

    public MBPushAsyncTask_RegisterTopics(Context context, String device_id, ArrayList<MBTopic> topics) {
        this.weakContext = new WeakReference<>(context);
        this.device_id = device_id;
        this.topics = topics;
    }

    public MBPushAsyncTask_RegisterTopics(Context context, String custom_action, String device_id, ArrayList<MBTopic> topics) {
        this.weakContext = new WeakReference<>(context);
        this.action = custom_action;
        this.device_id = device_id;
        this.topics = topics;
    }

    public MBPushAsyncTask_RegisterTopics(Context context, MBPushRegisterTopicsListener listener, String device_id, ArrayList<MBTopic> topics) {
        this.weakContext = new WeakReference<>(context);
        this.listener = listener;
        this.device_id = device_id;
        this.topics = topics;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        putValuesAndCall();
        if (MBApiManagerUtils.Companion.hasMapOkResults(map, false)) {
            result = MBApiManagerConfig.RESULT_OK;
        } else {
            if (map.containsKey(MBApiManagerConfig.AM_RESULT)) {
                result = (int) map.get(MBApiManagerConfig.AM_RESULT);
            } else {
                result = MBApiManagerConfig.COMMON_INTERNAL_ERROR;
            }

            if (map.containsKey(MBApiManagerConfig.AM_ERROR)) {
                error = (String) map.get(MBApiManagerConfig.AM_ERROR);
            } else {
                error = MBCommonMethods.getErrorMessageFromResult(weakContext.get(), result);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (weakContext != null) {
            if (listener == null) {
                Intent i = new Intent(action);
                i.putExtra("result", result);
                i.putExtra("error", error);
                MBAMActivityUtils.Companion.sendBroadcastMessage(weakContext.get(), i);
            } else {
                if (error != null) {
                    listener.onTopicsRegisteredError(error);
                } else {
                    listener.onTopicsRegistered();
                }
            }
        }
    }

    public void putValuesAndCall() {
        ContentValues valuesHeaders = new ContentValues();
        valuesHeaders.put("X-MPush-Token", MBUserConstants.pushKey);

        StringBuilder builder = new StringBuilder();
        StringBuilder builderTopics = new StringBuilder("[");
        builder.append("\"device_id\":\"" + device_id + "\",");

        for (int i = 0; i < topics.size(); i++) {
            MBTopic topic = topics.get(i);
            StringBuilder builderT = new StringBuilder("{");
            builderT.append("\"code\":\"" + topic.getTopic() + "\",");
            builderT.append("\"single\":" + topic.isSingle() + "\",");
            if (topic.getTitle() != null) {
                builderT.append("\"title\":\"" + topic.getTitle() + "\"");
            } else {
                builderT.append("\"title\":\"" + topic.getTopic() + "\"");
            }

            builderT.append("}");

            if ((i + 1) != topics.size()) {
                builderT.append(",");
            }

            builderTopics.append(builderT);
        }

        builderTopics.append("]");
        builder.append("\"topics\":" + builderTopics);

        ContentValues values = new ContentValues();

        map = MBAPIManager4.Companion.callApi(weakContext.get(),
                MBApiManagerConfig.API_REGISTER_TOPICS, values, mumble.mburger.sdk.kt.Common.MBApiManager.MBApiManagerConfig.Companion.getMODE_POST(),
                false, false, MBApiManagerConfig.endpoint_push, MBApiManagerConfig.SERVER_HOSTNAME_PUSH,
                true, valuesHeaders, builder.toString());
    }
}
