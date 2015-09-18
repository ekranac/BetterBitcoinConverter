package activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.ziga.bitcoinconverter.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import helpers.APIHelper;
import models.JSONResponse;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.input) EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Show keyboard on start
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        APIHelper.getApiData(this);

        SharedPreferences preferences = getSharedPreferences(APIHelper.PREFERENCES_KEY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String conversions = preferences.getString(APIHelper.CONVERSIONS_KEY, "");
        if(!conversions.equals("")) // If there's data from before (only not present when the user opens the app for the first time and isn't connected to the network)
        {
            JSONResponse response = gson.fromJson(conversions, JSONResponse.class);
            input.setText(response.USD.last_five_minutes.toString() + response.USD.symbol);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
