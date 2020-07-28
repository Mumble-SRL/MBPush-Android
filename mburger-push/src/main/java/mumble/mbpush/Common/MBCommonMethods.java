package mumble.mbpush.Common;

import android.content.Context;
import android.provider.Settings;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import mumble.mbpush.Common.MBConstants.MBApiManagerConfig;
import mumble.mbpush.Common.MBConstants.MBConstants;
import mumble.mbpush.R;

/**
 * A list of static methods used througout the SDK
 *
 * @author Enrico Ori
 * @version {@value MBConstants#version}
 */
public class MBCommonMethods {

    /**
     * Checks if a JSON has a key and is not null
     */
    public static boolean isJSONOk(JSONObject json, String key) {
        if (json.has(key)) {
            if (!json.isNull(key)) {
                return true;
            }
        }

        return false;
    }

    public static String getErrorMessageFromResult(Context context, int result) {
        switch (result) {
            case MBApiManagerConfig.COMMON_INTERNAL_ERROR:
                return context.getString(R.string.internal_error);

            case MBApiManagerConfig.COMMON_IOERROR:
                return context.getString(R.string.internal_error);

            case MBApiManagerConfig.COMMON_NOINTERNET:
                return context.getString(R.string.no_internet_error);

            case MBApiManagerConfig.COMMON_TIMEOUT:
                return context.getString(R.string.timeout_operation_error);

            default:
                return context.getString(R.string.internal_error);
        }
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
