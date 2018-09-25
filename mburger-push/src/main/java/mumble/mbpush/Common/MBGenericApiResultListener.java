package mumble.mbpush.Common;

/**
 * Interface to retrieve Nooko api result, must be implemented in Activities or Fragments
 * which are working with the API
 */
public interface MBGenericApiResultListener {
    void onApiResult(MBAPIResponse response);
}
