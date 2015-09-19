package helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import interfaces.APIInterface;
import models.JSONResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class APIHelper
{
    public static final String BASE_URL = "https://blockchain.info";
    public static final String PREFERENCES_KEY = "com.ziga.bitcoinconverter.preferences";
    public static final String CONVERSIONS_KEY = "com.ziga.bitcoinconverter.preferences.conversions";

    public static void getApiData(final Activity activity)
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        APIInterface api = adapter.create(APIInterface.class);
        api.getFeed(new Callback<JSONResponse>()
        {
            @Override
            public void success(JSONResponse jsonResponse, Response response)
            {
                saveConversionsData(activity, jsonResponse);
            }

            @Override
            public void failure(RetrofitError error)
            {
                Log.e("ERROR", error.toString());
            }
        });
    }


    public static void saveConversionsData(Activity activity, JSONResponse jsonResponse)
    {
        SharedPreferences preferences = activity.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String response = gson.toJson(jsonResponse);
        editor.putString(CONVERSIONS_KEY, response);
        editor.commit();
    }
}
