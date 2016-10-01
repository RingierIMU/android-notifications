package com.oneafricamedia.android.notifications;

import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;

import com.oneafricamedia.android.notifications.events.UpdateListingLookupsMessage;
import com.oneafricamedia.android.notifications.util.NotificationComponentUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SendUpdateListingsMessage {

    @Test
    public void testValidMessage() throws Exception {
        NotificationComponentUtil notificationComponentUtil = new NotificationComponentUtil(null);

        assertEquals(notificationComponentUtil.handleEvents("UPDATE_LISTINGS", withActivityAndEventMap()), true);
    }

    @Test
    public void testInvalidMessage() throws Exception {
        NotificationComponentUtil notificationComponentUtil = new NotificationComponentUtil(null);

        assertEquals(notificationComponentUtil.handleEvents("UPDATE_LISTINGSS", withActivityAndEventMap()), false);
    }

    private Map<String, Object> withActivityAndEventMap() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> activityMap = new HashMap<>();
        Map<String, Object> eventMap = new HashMap<>();

        activityMap.put("ALERT_LISTING", Activity.class);
        map.put(NotificationComponentUtil.ACTIVITY_MAP, activityMap);

        eventMap.put("UPDATE_LISTINGS", new UpdateListingLookupsMessage("Please update the listings lookups now."));
        map.put(NotificationComponentUtil.EVENT_MAP, eventMap);

        map.put(NotificationComponentUtil.SMALL_ICON, 0);
        map.put(NotificationComponentUtil.TITLE, "Notification title");
        map.put(NotificationComponentUtil.TEXT, "Notification text");

        return map;
    }

}
