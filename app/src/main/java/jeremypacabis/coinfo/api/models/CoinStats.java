package jeremypacabis.coinfo.api.models;

import com.google.gson.annotations.SerializedName;

public class CoinStats {

    @SerializedName("coinheat")
    public int coinheat;

    @SerializedName("price")
    public String price;

    @SerializedName("change")
    public Change change;
}