package mumble.mbpush.Common.MBConstants;

public class MBApiManagerConfig {

    public static final String endpoint_push = "https://app.mpush.cloud";
    public static final String SERVER_HOSTNAME_PUSH = "app.mpush.cloud";

    public static final int MODE_POST = -1;
    public static final int MODE_GET = -2;
    public static final int MODE_DELETE = -3;
    public static final int MODE_PUT = -4;

    /**
     * API
     */
    public static final String API = "/api";

    /**
     * PUSH API
     */
    public static final String API_TOKENS_PUSH = "/api/tokens";
    public static final String API_REGISTER_TOPICS = "/api/register";
    public static final String API_UNREGISTER_TOPICS = "/api/unregister";
    public static final String API_UNREGISTER_ALL_TOPICS = "/api/unregister-all";

    /**
     * ERRORS
     */
    public static final int STATUS_CODE_OK = 0;
    public static final int STATUS_CODE_LOGOUT = 55;
    public static final int RESULT_OK = 200;
    public static final int RESULT_REFRESH = 401;
    public static final int COMMON_IOERROR = -1000;
    public static final int COMMON_TIMEOUT = -1001;
    public static final int COMMON_INTERNAL_ERROR = -1003;
    public static final int COMMON_NOINTERNET = -1004;

    /**
     * APIMANAGER CONSTANTS
     */
    public static final String AM_RESULT = "result";
    public static final String AM_PAYLOAD = "payload";
    public static final String AM_RESPONSE = "response";
    public static final String AM_ERROR = "error";
}
