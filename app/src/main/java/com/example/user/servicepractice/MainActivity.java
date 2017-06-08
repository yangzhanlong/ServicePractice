package com.example.user.servicepractice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyConn conn;
    private Iservice myBinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 启动screen service
        Intent intent = new Intent(this, ScreenService.class);
        startService(intent);

        // 启动 mybinder service
        Intent intent1 = new Intent(this, MyBindService.class);
        // 连接服务
        conn = new MyConn();
        // 绑定服务
        bindService(intent1, conn, BIND_AUTO_CREATE);
    }

    // 点击按钮调用服务里面money的方法
    public void btn_money(View view) {
        myBinder.CallMoney(1000000);
    }

    // 监视服务的状态
    private class MyConn implements ServiceConnection {
        // 当服务连接成功调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取中间人对象
            myBinder = (Iservice) service;
        }

        // 失去连接
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(), "Disconnect", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        // 当 activity 销毁的时候 解绑服务
        Log.v("MainActivity", "onDestroy");
        unbindService(conn);
        super.onDestroy();
    }
}
