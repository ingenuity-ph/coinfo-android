package jeremypacabis.coinfo.api.models;

import com.google.gson.annotations.SerializedName;

public class MarketCap {

    @SerializedName("24h_volume")
    public long _24hVolume;

    @SerializedName("market_cap")
    public long marketCap;

    @SerializedName("change")
    public Change change;
}