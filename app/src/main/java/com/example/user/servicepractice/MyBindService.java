package com.example.user.servicepractice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class MyBindService extends Service {

    // 把我定义的中间人对象返回
    @Override
    public IBinder onBind(Intent intent) {
        Log.v("Service", "onBind");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        Log.v("Service", "onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.v("Service", "onDestroy");
        super.onDestroy();
    }

    private void money (int money) {
        if (money > 1000) {
            Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
        }
    }

    private void play() {
        Toast.makeText(getApplicationContext(), "play", Toast.LENGTH_SHORT).show();
    }

    // 定义中间人对象(IBinder)
    public class MyBinder extends Binder implements Iservice{
        public void CallMoney(int money) {
            money(money);
        }

        public void CallPlay() {
            play();
        }
    }
}
