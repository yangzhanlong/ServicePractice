package com.example.user.servicepractice;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.SCREEN_OFF".equals(action)) {
            Log.v("Receiver", "锁屏了");
        } else if ("android.intent.action.SCREEN_ON".equals(action)) {
            Log.v("Receiver", "解锁了");
        }
    }
}
