package activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ziga.bitcoinconverter.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import helpers.APIHelper;
import helpers.ConversionHelper;
import me.grantland.widget.AutofitTextView;
import models.JSONConversion;
import models.JSONResponse;

public class MainActivity extends AppCompatActivity
{
    public static JSONConversion selectedCurrency;

    @Bind(R.id.input) EditText input;
    @Bind(R.id.tv_unit) TextView tvUnit;
    @Bind(R.id.tv_converted_value) AutofitTextView tvConvertedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Show keyboard on start
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        APIHelper.getApiData(this);

        // Get jsonResponse object from SharedPreferences
        SharedPreferences preferences = getSharedPreferences(APIHelper.PREFERENCES_KEY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String conversions = preferences.getString(APIHelper.CONVERSIONS_KEY, "");

        if(!conversions.equals("")) // If there's data from before (only not present when the user opens the app for the first time and isn't connected to the network)
        {
            JSONResponse response = gson.fromJson(conversions, JSONResponse.class);
            selectedCurrency  = response.USD;
            tvUnit.setText(selectedCurrency.symbol);


            input.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {
                    // Preform conversion everytime the value in EditText is changed
                    if(!charSequence.toString().equals(""))
                    {
                        ConversionHelper.convertValues(MainActivity.this);
                    } else {
                        tvConvertedValue.setText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });
        }
        else // If user is running app for the first time without network connection- can't parse conversion data
        {
            Intent intent = new Intent(MainActivity.this, NoConnectionActivity.class);
            startActivity(intent);
        }

        tvUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change currency symbol
                if (!tvUnit.getText().toString().equals(getResources().getString(R.string.symbol_btc))) // If not bitcoin
                {
                    tvUnit.setText(getResources().getString(R.string.symbol_btc));
                } else {
                    tvUnit.setText(selectedCurrency.symbol);
                }

                // Do the actual conversion
                if (!input.getText().toString().equals(""))
                {
                    ConversionHelper.convertValues(MainActivity.this);
                }
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
        if(id!=R.id.currency_settings) // Parent button has an id insignificant to any functionality
        {
            // Get jsonResponse object from SharedPreferences
            SharedPreferences preferences = getSharedPreferences(APIHelper.PREFERENCES_KEY, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String conversions = preferences.getString(APIHelper.CONVERSIONS_KEY, "");

            if(!conversions.equals("")) // If there's data from before (only not present when the user opens the app for the first time and isn't connected to the network)
            {
                JSONResponse response = gson.fromJson(conversions, JSONResponse.class);
                selectedCurrency = ConversionHelper.getConversionModel(id, response);
                if(!tvUnit.getText().toString().equals(getResources().getString(R.string.symbol_btc)))
                {
                    tvUnit.setText(selectedCurrency.symbol);
                }

                if(!input.getText().toString().equals(""))
                {
                    ConversionHelper.convertValues(MainActivity.this);
                }

            }

        }

        return super.onOptionsItemSelected(item);
    }
}
