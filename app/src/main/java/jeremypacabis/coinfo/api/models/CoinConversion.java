package jeremypacabis.coinfo.api.models;

import com.google.gson.annotations.SerializedName;

public class CoinConversion {

    @SerializedName("result")
    public double result;

    @SerializedName("currency")
    public String currency;

    @SerializedName("coin")
    public String coin;
}