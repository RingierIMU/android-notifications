[![Build Status on Travis:](https://travis-ci.org/OneAfricaMedia/android-notifications.svg?branch=master)](https://travis-ci.org/OneAfricaMedia/android-notifications)
[![Release](https://img.shields.io/github/release/OneAfricaMedia/android-notifications.svg)](https://github.com/OneAfricaMedia/android-notifications/releases)

# Notifications

A helper library for use with the OAM cloud messaging microservice

# Installation

To use this library in your android project, just simply add the following dependency into your build.gradle

```sh
dependencies {
    compile 'com.oneafricamedia.android:notifications:0.0.1'
}
```

# Usage

First of all you should have a look at the sourcecode, an example app is provided.

You need to register with Firebase and follow their instructions on setting up your application.

Instead of implementing your own services, you include this library as stated above and extend the ones included:

```java
public class MyAppFcmInstanceIDListenerService extends FcmInstanceIDListenerService {…}

public class MyAppFcmMessageListenerService extends FcmMessageListenerService {…}
```

Then you register them in your Manifest:

```xml
<service android:name=".services.MyFcmInstanceIDListenerService">
 <intent-filter>
 <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
 </intent-filter>
</service>

<service android:name=".services.MyFcmMessageListenerService">
 <intent-filter>
 <action android:name="com.google.firebase.MESSAGING_EVENT" />
 </intent-filter>
</service>
```

You can (and should) override the default methods then and use the appropriate methods in the super class:

```java
@Override
public void onTokenRefresh() {
 super.onTokenRefresh(new BackendBundle("http://www.site.com/GCM/", "auth", "http://www.site.com/API/", "auth", "0"));
}

@Override
public void onMessageReceived(RemoteMessage message) {
    Map<String, Object> map = new HashMap();
    Map<String, Class> activityMap = new HashMap<>();
    Intent intent = new Intent();


 // This is where we prepare our Notification
 // Please see the example app within the component sources

 // This is where we show it and maybe handle the callback
 //super.onMessageReceived(message, map);
 super.onMessageReceived(message, map, new FcmNotification.OnCompletionListener() {
    @Override
    public void onSuccess() {
            Log.d("LogTag", "This thing came back OK.");
    }

    @Override
    public void onFailure() {
        Log.e("LogTag", "Everything went down the drain!!!");
    }
    });
}

```

# How to handle events sent by the component

Include EventBus, like so:

```sh
dependencies {
    compile compile "org.greenrobot:eventbus:3.0.0"
}
```

Register to the default EventBus channel, like so:

```java
EventBus.getDefault().register(this);
```

Listen for the corresponding events, like so:

```sh
@Subscribe(threadMode = ThreadMode.MAIN)
public void onEvent(MarketingMessageReceived marketingMessageReceived) {
  TextView t = (TextView) findViewById(R.id.textViewMain);
  t.setText(marketingMessageReceived.message);
}
```

# How to work with a callback

The component comes with a simple callback that you can use to figure out wether a notification was shown correctly, a system message triggered the correct stuff and wether it succeeded or not.
You have to implement FcmNotification.OnCompletionListener in case of Firebase, it works just like an OnClickListener().

# Change Logs

### v0.0.1

Initial version

# License

Apache 2.0