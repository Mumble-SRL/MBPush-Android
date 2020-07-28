package mumble.mbpush.MBPushMethods.MBPushAsyncTasks;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.Map;

import mumble.mbpush.Common.MBCommonMethods;
import mumble.mbpush.Common.MBConstants.MBAPIConstants;
import mumble.mbpush.Common.MBConstants.MBApiManagerConfig;
import mumble.mbpush.Common.MBConstants.MBUserConstants;
import mumble.mbpush.MBPushMethods.MBPushResultsListener.MBPushSendTokenListener;
import mumble.mburger.sdk.kt.Common.MBApiManager.MBAMActivityUtils;
import mumble.mburger.sdk.kt.Common.MBApiManager.MBAPIManager4;
import mumble.mburger.sdk.kt.Common.MBApiManager.MBApiManagerUtils;

public class MBPushAsyncTask_SendToken extends AsyncTask<Void, Void, Void> {

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
     * Push token obtained from Firebase
     */
    @NonNull
    private String token;

    /**
     * If you wish to change the action that accompanies the API result
     */
    private String action = MBAPIConstants.ACTION_SEND_TOKEN;

    /**
     * If you wish to use a listener to retrieve the data instead of the ApiListener
     */
    private MBPushSendTokenListener listener;

    private int result = MBApiManagerConfig.COMMON_INTERNAL_ERROR;
    private String error;
    private Map<String, Object> map;

    public MBPushAsyncTask_SendToken(Context context, String device_id, String token) {
        this.weakContext = new WeakReference<>(context);
        this.device_id = device_id;
        this.token = token;
    }

    public MBPushAsyncTask_SendToken(Context context, String cutom_action, String device_id, String token) {
        this.weakContext = new WeakReference<>(context);
        this.action = cutom_action;
        this.device_id = device_id;
        this.token = token;
    }

    public MBPushAsyncTask_SendToken(Context context, MBPushSendTokenListener listener, String device_id, String token) {
        this.weakContext = new WeakReference<>(context);
        this.listener = listener;
        this.device_id = device_id;
        this.token = token;
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
                    listener.onTokenSentError(error);
                } else {
                    listener.onTokenSent();
                }
            }
        }
    }

    public void putValuesAndCall() {
        ContentValues valuesHeaders = new ContentValues();
        valuesHeaders.put("X-MPush-Token", MBUserConstants.pushKey);

        ContentValues values = new ContentValues();
        values.put("token", token);
        values.put("device_id", device_id);
        values.put("platform", "and");

        map = MBAPIManager4.Companion.callApi(weakContext.get(),
                MBApiManagerConfig.API_TOKENS_PUSH, values, mumble.mburger.sdk.kt.Common.MBApiManager.MBApiManagerConfig.Companion.getMODE_POST(), false,
                false, MBApiManagerConfig.endpoint_push, MBApiManagerConfig.SERVER_HOSTNAME_PUSH, true, valuesHeaders, null);
    }
}
