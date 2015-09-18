package activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.ziga.bitcoinconverter.R;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import interfaces.APIInterface;
import models.JSONResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://blockchain.info";

    @Bind(R.id.input) EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Show keyboard on start
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        APIInterface api = adapter.create(APIInterface.class);
        api.getFeed(new Callback<JSONResponse>() {
            @Override
            public void success(JSONResponse jsonResponse, Response response) {
                // input.setText(jsonResponse.USD.buy.toString());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
