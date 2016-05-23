package com.minzou.servicedemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.WindowManager;

/**
 * This Service is Persistent Service. Do some what you want to do here.<br/>
 *
 * Created by Mars on 12/24/15.
 */
public class Service1 extends Service{

    public static final String LOCK_ACTION = "lock";
    public static final String UNLOCK_ACTION = "unlock";
    private Context mContext;
    private WindowManager mWinMng;
//    private ScreenSaverView screenView;
    private StarLockView lockView;
    private IntentFilter intentFilter;
    private Receiver1 receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mWinMng = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        intentFilter = new IntentFilter();
        receiver = new Receiver1();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (TextUtils.equals(action, LOCK_ACTION))
            addView();
        else if (TextUtils.equals(action, UNLOCK_ACTION)) {
            removeView();
//            stopSelf();
        }
//        return START_REDELIVER_INTENT;
        flags = START_REDELIVER_INTENT;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
//        startService(new Intent(this, Service1.class));
    }

    public void addView() {
        if (lockView == null) {
//        if (screenView == null) {
            lockView = new StarLockView(mContext);
//            screenView = new ScreenSaverView(mContext);

            WindowManager.LayoutParams param = new WindowManager.LayoutParams();
            param.type = WindowManager.LayoutParams.TYPE_TOAST
            ;
            param.format = PixelFormat.RGBA_8888;
            param.flags = 1280;
            // mParam.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            param.width = WindowManager.LayoutParams.MATCH_PARENT;
            param.height = WindowManager.LayoutParams.MATCH_PARENT;

            mWinMng.addView(lockView, param);
//            mWinMng.addView(screenView, param);
        }
    }

    public void removeView() {
//        if (screenView != null) {
//            mWinMng.removeView(screenView);
//            screenView = null;
//        }
        if (lockView != null) {
            mWinMng.removeView(lockView);
            lockView = null;
        }
    }
}
