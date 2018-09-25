package mumble.mbpush;

import mumble.mbpush.Common.MBConstants.MBUserConstants;

public class MBPush {

    public static void init(String token) {
        MBUserConstants.pushKey = token;
    }

}
