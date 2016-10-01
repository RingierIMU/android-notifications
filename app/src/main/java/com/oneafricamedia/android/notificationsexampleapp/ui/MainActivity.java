package com.oneafricamedia.android.notificationsexampleapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.oneafricamedia.android.notifications.events.MarketingMessageReceived;
import com.oneafricamedia.android.notifications.events.UpdateListingLookupsMessage;
import com.oneafricamedia.android.notificationsexampleapp.R;
import com.oneafricamedia.android.notificationsexampleapp.events.AlertEnquiry;
import com.oneafricamedia.android.notificationsexampleapp.events.AlertListing;
import com.oneafricamedia.android.notificationsexampleapp.events.ShitHitTheFan;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MarketingMessageReceived marketingMessageReceived) {
        TextView t = (TextView) findViewById(R.id.textViewMain);
        t.setText(marketingMessageReceived.message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateListingLookupsMessage updateListingLookupsMessage) {
        TextView t = (TextView) findViewById(R.id.textViewMain);
        t.setText(updateListingLookupsMessage.message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShitHitTheFan shitHitTheFan) {
        TextView t = (TextView) findViewById(R.id.textViewMain);
        t.setText(shitHitTheFan.message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AlertEnquiry alertEnquiry) {
        TextView t = (TextView) findViewById(R.id.textViewMain);
        t.setText(alertEnquiry.message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AlertListing alertListing) {
        TextView t = (TextView) findViewById(R.id.textViewMain);
        t.setText(alertListing.message);
    }


}
