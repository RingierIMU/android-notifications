package com.oneafricamedia.android.notificationsexampleapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.oneafricamedia.android.notifications.events.MarketingMessageReceived;
import com.oneafricamedia.android.notifications.events.UpdateListingLookupsMessage;
import com.oneafricamedia.android.notificationsexampleapp.R;
import com.oneafricamedia.android.notificationsexampleapp.events.AlertEnquiry;
import com.oneafricamedia.android.notificationsexampleapp.events.AlertListing;
import com.oneafricamedia.android.notificationsexampleapp.events.ShitHitTheFan;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        final Button button = (Button) findViewById(R.id.buttonSendUpstreamMessageMain);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                fm.send(new RemoteMessage.Builder("315874173180@gcm.googleapis.com")
                        .setMessageId(UUID.randomUUID().toString())
                        .addData("ACTION", "SEND_FEEDBACK")
                        .addData("MESSAGE", ((EditText) findViewById(R.id.textViewChatMain))
                                .getText().toString())
                        .build());
            }
        });
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
