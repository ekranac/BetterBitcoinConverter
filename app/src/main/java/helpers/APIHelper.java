package helpers;

import android.util.Log;

import interfaces.APIInterface;
import models.JSONResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class APIHelper
{
    public static final String BASE_URL = "https://blockchain.info";

    public static void getApiData()
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        APIInterface api = adapter.create(APIInterface.class);
        api.getFeed(new Callback<JSONResponse>() {
            @Override
            public void success(JSONResponse jsonResponse, Response response) {
                Log.i("USD", jsonResponse.USD.last_five_minutes.toString());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
