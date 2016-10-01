package com.oneafricamedia.android.notificationsexampleapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.oneafricamedia.android.notifications.events.MarketingMessageReceived;
import com.oneafricamedia.android.notificationsexampleapp.R;
import com.oneafricamedia.android.notificationsexampleapp.events.AlertListing;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AlertActivity extends AppCompatActivity {

    public static final String ALERT_ID = "alert_id";

    public static Intent getIntent(Context context, long alertId) {
        Intent intent = new Intent(context, AlertActivity.class);
        intent.putExtra(AlertActivity.ALERT_ID, alertId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        if (getIntent() != null && getIntent().hasExtra("payload")) {
            Toast.makeText(this, "Got this payload: " + getIntent().getStringExtra("payload"),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No ID found.", Toast.LENGTH_LONG).show();
        }

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MarketingMessageReceived marketingMessageReceived) {
        TextView t = (TextView) findViewById(R.id.textViewAlert);
        t.setText(marketingMessageReceived.message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AlertListing alertListing) {
        TextView t = (TextView) findViewById(R.id.textViewMain);
        t.setText(alertListing.message);
    }

}
