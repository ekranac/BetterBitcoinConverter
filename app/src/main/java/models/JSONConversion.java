package models;

import com.google.gson.annotations.SerializedName;

public class JSONConversion
{
    @SerializedName("15m")
    public Double last_fifteen_minutes;
    public Double last;
    public Double buy;
    public Double sell;
    public String symbol;
}
