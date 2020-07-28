package mumble.mbpush.MBPushMethods.MBPushAsyncTasks;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import org.json.JSONArray;

import java.lang.ref.WeakReference;
import java.util.Map;

import mumble.mbpush.Common.MBCommonMethods;
import mumble.mbpush.Common.MBConstants.MBAPIConstants;
import mumble.mbpush.Common.MBConstants.MBApiManagerConfig;
import mumble.mbpush.Common.MBConstants.MBUserConstants;
import mumble.mbpush.MBPushMethods.MBPushResultsListener.MBPushUnregisterTopicsListener;
import mumble.mburger.sdk.kt.Common.MBApiManager.MBAMActivityUtils;
import mumble.mburger.sdk.kt.Common.MBApiManager.MBAPIManager4;
import mumble.mburger.sdk.kt.Common.MBApiManager.MBApiManagerUtils;

public class MBPushAsyncTask_UnregisterTopics extends AsyncTask<Void, Void, Void> {

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
     * Topics to unregister
     */
    @NonNull
    private JSONArray topics;

    /**
     * If you wish to change the action that accompanies the API result
     */
    private String action = MBAPIConstants.ACTION_UNREGISTER_TOPICS;

    /**
     * If you wish to use a listener to retrieve the data instead of the ApiListener
     */
    private MBPushUnregisterTopicsListener listener;

    private int result = MBApiManagerConfig.COMMON_INTERNAL_ERROR;
    private String error;
    private Map<String, Object> map;

    public MBPushAsyncTask_UnregisterTopics(Context context, String device_id, JSONArray topics) {
        this.weakContext = new WeakReference<>(context);
        this.device_id = device_id;
        this.topics = topics;
    }

    public MBPushAsyncTask_UnregisterTopics(Context context, String custom_action, String device_id, JSONArray topics) {
        this.weakContext = new WeakReference<>(context);
        this.action = custom_action;
        this.device_id = device_id;
        this.topics = topics;
    }

    public MBPushAsyncTask_UnregisterTopics(Context context, MBPushUnregisterTopicsListener listener, String device_id, JSONArray topics) {
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
                    listener.onTopicsUnregisteredError(error);
                } else {
                    listener.onTopicsUnregistered();
                }
            }
        }
    }

    public void putValuesAndCall() {
        ContentValues valuesHeaders = new ContentValues();
        valuesHeaders.put("X-MPush-Token", MBUserConstants.pushKey);

        ContentValues values = new ContentValues();
        values.put("topics", topics.toString());
        values.put("device_id", device_id);

        map = MBAPIManager4.Companion.callApi(weakContext.get(),
                MBApiManagerConfig.API_UNREGISTER_TOPICS, values, mumble.mburger.sdk.kt.Common.MBApiManager.MBApiManagerConfig.Companion.getMODE_POST(),
                false, false, MBApiManagerConfig.endpoint_push, MBApiManagerConfig.SERVER_HOSTNAME_PUSH,
                true, valuesHeaders, null);

    }
}
