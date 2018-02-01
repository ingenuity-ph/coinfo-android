package jeremypacabis.coinfo.listeners;

import jeremypacabis.coinfo.api.models.Authentication;

/**
 * Created by memengski on 8/8/17.
 */

public interface OnRefreshTokenListener {
    void onRefreshToken(Authentication authentication);
}
