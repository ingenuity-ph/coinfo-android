package jeremypacabis.coinfo.api.models;

import com.google.gson.annotations.SerializedName;

public class Notice {

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("hour")
    public int hour;

    @SerializedName("price")
    public String price;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("id")
    public int id;

    @SerializedName("day")
    public int day;

    @SerializedName("notice")
    public String notice;

    @SerializedName("coin")
    public String coin;
}