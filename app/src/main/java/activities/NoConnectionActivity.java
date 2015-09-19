package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ziga.bitcoinconverter.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoConnectionActivity extends AppCompatActivity
{
    @OnClick(R.id.refresh_btn)
    public void refresh(View view)
    {
        // Start MainActivity again
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
        ButterKnife.bind(this);
    }
}
