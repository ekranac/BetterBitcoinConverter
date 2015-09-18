package interfaces;

import org.json.JSONObject;

import models.JSONResponse;
import retrofit.Callback;
import retrofit.http.GET;

public interface APIInterface {
    @GET("/ticker")
    public void getFeed(Callback<JSONResponse> response);
}
