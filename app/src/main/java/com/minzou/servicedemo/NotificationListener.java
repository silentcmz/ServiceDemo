package com.minzou.servicedemo;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by Minzou on 2016/5/15.
 */
public class NotificationListener extends NotificationListenerService{

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i("zpf", "open"+"-----"+sbn.toString());
        if (!Utils.isWorked(getApplicationContext(),Constant.SERVICE1)){
            startService(new Intent(getApplicationContext(), Service1.class));
            Log.i("zpf", "shut"+"-----start service 1");
        }
//        Intent intent = new Intent("android.intent.action.Minzou");
////        intent.putExtras(mNotification.extras);
//        if (android.os.Build.VERSION.SDK_INT >= 12) {
//            intent.setFlags(32);//3.1以后的版本需要设置Intent.FLAG_INCLUDE_STOPPED_PACKAGES
//        }
//        sendBroadcast(intent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("zpf", "shut"+"-----"+sbn.toString());
        startService(new Intent(getApplicationContext(), Service1.class));
        if (!Utils.isWorked(getApplicationContext(),Constant.SERVICE1)){
            startService(new Intent(getApplicationContext(), Service1.class));

            Log.i("zpf", "shut"+"-----start service 1");
        }

    }
}
