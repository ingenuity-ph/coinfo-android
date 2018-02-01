package jeremypacabis.coinfo.api.models;

import com.google.gson.annotations.SerializedName;

public class ICO {

    @SerializedName("image")
    public String image;

    @SerializedName("start_time")
    public String startTime;

    @SerializedName("website")
    public String website;

    @SerializedName("name")
    public String name;

    @SerializedName("end_time")
    public String endTime;

    @SerializedName("description")
    public String description;

    @SerializedName("url")
    public String url;
}