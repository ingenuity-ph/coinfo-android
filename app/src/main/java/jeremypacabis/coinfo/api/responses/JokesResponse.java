package jeremypacabis.coinfo.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JokesResponse {

    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("icon_url")
    @Expose
    public String icon_url;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("value")
    @Expose
    public String value;

}
