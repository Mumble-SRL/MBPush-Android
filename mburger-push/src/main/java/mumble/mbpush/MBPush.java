package mumble.mbpush;

import android.content.Context;

import mumble.mbpush.Common.MBCommonMethods;
import mumble.mbpush.Common.MBConstants.MBUserConstants;
import mumble.mbpush.MBPushData.MBTopic;

public class MBPush {

    public static void init(String token) {
        MBUserConstants.pushKey = token;
    }

    public static MBTopic projectPushTopic(Context context) {
        return new MBTopic("project.all", "All users", false);
    }

    public static MBTopic devicePushTopic(Context context) {
        String device_id = MBCommonMethods.getDeviceId(context);
        return new MBTopic(device_id, "Device: " + device_id, true);
    }

}
