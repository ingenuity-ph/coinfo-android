package jeremypacabis.coinfo.api.models;

import com.google.gson.annotations.SerializedName;

public class Change {

    @SerializedName("24h")
    public String _24h;

    @SerializedName("1h")
    public String _1h;

    @SerializedName("7d")
    public String _7d;
}