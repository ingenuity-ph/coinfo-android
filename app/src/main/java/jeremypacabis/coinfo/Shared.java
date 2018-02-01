package jeremypacabis.coinfo;

import android.content.Context;

import jeremypacabis.coinfo.api.ApiClient;

public class Shared {

    public static ApiClient apiClient;

    public static void init(Context context) {
        final String basUrl = context
                .getResources()
                .getString(R.string.live_url);

        apiClient = new ApiClient(basUrl);
    }
}
