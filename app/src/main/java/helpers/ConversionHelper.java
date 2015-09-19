package helpers;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import com.ziga.bitcoinconverter.R;

import activities.MainActivity;
import models.JSONConversion;
import models.JSONResponse;

public class ConversionHelper
{
    public static JSONConversion getConversionModel(int currencyId, JSONResponse response)
    {
        JSONConversion conversion = null;

        switch (currencyId)
        {
            case R.id.unit_usd:
                conversion = response.USD;
                break;

            case R.id.unit_isk:
                conversion = response.ISK;
                break;

            case R.id.unit_hkd:
                conversion = response.HKD;
                break;

            case R.id.unit_twd:
                conversion = response.TWD;
                break;

            case R.id.unit_chf:
                conversion = response.CHF;
                break;

            case R.id.unit_eur:
                conversion = response.EUR;
                break;

            case R.id.unit_dkk:
                conversion = response.DKK;
                break;

            case R.id.unit_clp:
                conversion = response.CLP;
                break;

            case R.id.unit_cad:
                conversion = response.CAD;
                break;

            case R.id.unit_cny:
                conversion = response.CNY;
                break;

            case R.id.unit_thb:
                conversion = response.THB;
                break;

            case R.id.unit_aud:
                conversion = response.AUD;
                break;

            case R.id.unit_sgd:
                conversion = response.SGD;
                break;

            case R.id.unit_krw:
                conversion = response.KRW;
                break;

            case R.id.unit_jpy:
                conversion = response.JPY;
                break;

            case R.id.unit_pln:
                conversion = response.PLN;
                break;

            case R.id.unit_gbp:
                conversion = response.GBP;
                break;

            case R.id.unit_sek:
                conversion = response.SEK;
                break;

            case R.id.unit_nzd:
                conversion = response.NZD;
                break;

            case R.id.unit_brl:
                conversion = response.BRL;
                break;

            case R.id.unit_rub:
                conversion = response.RUB;
                break;

        }
        
        return conversion;
    }

    public static void convertValues(Activity activity)
    {
        TextView tvUnit = (TextView) activity.findViewById(R.id.tv_unit);
        TextView tvConvertedValue = (TextView) activity.findViewById(R.id.tv_converted_value);
        EditText input = (EditText) activity.findViewById(R.id.input);
        Double inputValue = Double.parseDouble(input.getText().toString());

        if(!tvUnit.getText().toString().equals(activity.getResources().getString(R.string.symbol_btc))) // If not bitcoin
        {
            tvConvertedValue.setText(Double.toString(ConversionHelper.getValueInBTC(inputValue, MainActivity.selectedCurrency)) + " " + activity.getResources().getString(R.string.symbol_btc));
        }
        else
        {
            tvConvertedValue.setText(Double.toString(ConversionHelper.getValueInSelectedCurrency(inputValue, MainActivity.selectedCurrency)) + " " + MainActivity.selectedCurrency.symbol);
        }
    }


    public static Double getValueInSelectedCurrency(Double inputValue, JSONConversion conversion)
    {
        // BTC -> Selected currency
        return inputValue*conversion.last_fifteen_minutes;
    }

    public static Double getValueInBTC(Double inputValue, JSONConversion conversion)
    {
        // Selected currency -> BTC
        return inputValue/conversion.last_fifteen_minutes;
    }
}
