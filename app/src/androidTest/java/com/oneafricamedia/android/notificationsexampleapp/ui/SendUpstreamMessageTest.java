package com.oneafricamedia.android.notificationsexampleapp.ui;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import com.oneafricamedia.android.notificationsexampleapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SendUpstreamMessageTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void unlockScreen() {
        final MainActivity activity = mActivityTestRule.getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }

    @Test
    public void sendUpstreamMessageTest() {
        ViewInteraction appCompatEditText1 = onView(withId(R.id.textViewChatMain))
                .inRoot(withDecorView(is(mActivityTestRule.getActivity().getWindow().getDecorView())));
        appCompatEditText1.perform(click());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.textViewChatMain))
                .inRoot(withDecorView(is(mActivityTestRule.getActivity().getWindow().getDecorView())));
        appCompatEditText2.perform(replaceText("Hello World!"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(withId(R.id.buttonSendUpstreamMessageMainSaveBlob))
                .inRoot(withDecorView(is(mActivityTestRule.getActivity().getWindow().getDecorView())));
        appCompatEditText3.perform(click());
    }

}
