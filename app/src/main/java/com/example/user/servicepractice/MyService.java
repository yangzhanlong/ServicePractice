package com.example.user.servicepractice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service {

    // 录音机实例
    private MediaRecorder mediaRecorder;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // 获取telephonemanager的实例
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        // 注册电话的监听
        tm.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);

        super.onCreate();
        Log.v("Service", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("Service", "onDestroy");
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // 具体判断一下电话的状态
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    // 停止录音
                    if (mediaRecorder != null) {
                        Log.v("Service", "Stop record");
                        mediaRecorder.stop();
                        mediaRecorder.reset(); // You can reuse the object by going back to setAudioSource() step
                        mediaRecorder.release(); // Now the object cannot be reused
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.v("Service", "Recording...");
                    mediaRecorder.start(); // 开始
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.v("Service", "Ready to record");
                    // 创建MediaRecorder 实例
                    mediaRecorder = new MediaRecorder();
                    // 设置音频的来源
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    // 设置输出的格式
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    // 设置音频的编码方式
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    // 设置存放的路径
                    mediaRecorder.setOutputFile("/mnt/sdcard/luyin.mp4");
                    // 准备录
                    try {
                        mediaRecorder.prepare();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }
}
