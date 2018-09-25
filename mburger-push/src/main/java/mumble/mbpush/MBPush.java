package mumble.mbpush;

import mumble.mbpush.Common.MBConstants.MBUserConstants;

public class MBPush {

    public static void initPush(String token) {
        MBUserConstants.pushKey = token;
    }

}
