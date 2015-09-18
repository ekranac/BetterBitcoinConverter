package models;

import com.google.gson.annotations.SerializedName;

public class JSONConversion
{
    @SerializedName("5m")
    public Double last_five_minutes;
    public Double last;
    public Double buy;
    public Double sell;
    public String symbol;
}
