package com.oneafricamedia.android.notifications;

import com.oneafricamedia.android.notifications.events.MarketingMessageReceived;
import com.oneafricamedia.android.notifications.events.UpdateListingLookupsMessage;

import org.junit.Test;

public class CreateEvents {

    @Test
    public void createEvents() throws Exception {
        new UpdateListingLookupsMessage("Test");
        new MarketingMessageReceived("Test");
    }

}
