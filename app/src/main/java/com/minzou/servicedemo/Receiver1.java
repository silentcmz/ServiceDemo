package com.minzou.servicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * DO NOT do anything in this Receiver!<br/>
 * <p/>
 * Created by Mars on 12/24/15.
 */
public class Receiver1 extends BroadcastReceiver {

    private static final String TAG = "BootBroadcastReceiver";

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Boot this system , BootBroadcastReceiver onReceive()");
        Log.i(TAG, Utils.isWorked(context,"com.minzou.servicedemo.Service1") + "");
        if (intent.getAction() != null){

            Log.i(TAG, intent.getAction());
            if (!Utils.isWorked(context,Constant.SERVICE1)){
                context.startService(new Intent(context, Service1.class));
                Log.e(TAG, intent.getAction());
            }
            String action = intent.getAction();
            if (action.equals(ACTION_BOOT)
//                    || action.equals("android.net.conn.CONNECTIVITY_CHANGE")
                    || action.equals("android.intent.action.SCREEN_OFF")
//                    || action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")
//                    || action.equals("android.intent.action.ACTION_POWER_CONNECTED")
                    || action.equals("android.intent.action.USER_PRESENT")) {
//            Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
                Log.i(TAG, "BootBroadcastReceiver onReceive(), Do thing!");
//                Intent i = new Intent(context, Service1.class);
//                i.setAction(Service1.LOCK_ACTION);
//                context.startService(i);
                Intent i = new Intent("wo.shi.suo");
                context.sendBroadcast(i);
            }

//        if (action.equals("android.intent.action.SCREEN_ON")) {
//            System.out.println("—— SCREEN_ON ——");
//            Intent i = new Intent(context, Service1.class);
//            i.setAction(Service1.LOCK_ACTION);
//            context.startService(i);
//        } else if (action.equals("android.intent.action.SCREEN_OFF")) {
//            System.out.println("—— SCREEN_OFF ——");
//            Intent i = new Intent(context, Service1.class);
//            i.setAction(Service1.LOCK_ACTION);
//            context.startService(i);
//        }

//        if (action.equals(Intent.ACTION_TIME_CHANGED) && !CommonUtils.isServiceRun(context, "com.thomas.lockdemo.Service1")) {
//            Log.i(TAG, "BootBroadcastReceiver onReceive(), Do thing!111111111111111111111111111111111111");
//            Intent i = new Intent(context, Service1.class);
//            i.setAction(Service1.LOCK_ACTION);
//            context.startService(i);
//        }
//            if (action.equals("android.intent.action.USER_PRESENT")) {
////            Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
//                Log.i(TAG, intent.getAction());
//                Intent i = new Intent(context, Service1.class);
//                i.setAction(Service1.LOCK_ACTION);
//                context.startService(i);
//            }
//            if (action.equals("android.intent.action.Minzou")) {
//                Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
//                Log.i(TAG, intent.getAction());
//                Intent i = new Intent(context, Service1.class);
//                i.setAction(Service1.LOCK_ACTION);
//                context.startService(i);
//            }
//        if (action.equals("com.leeliwei.test.receiver.action.test")) {
//            Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
//            Log.i("this1111111111", "this222222222222222222222222");
//            Intent i = new Intent(context, Service1.class);
//            i.setAction(Service1.LOCK_ACTION);
//            context.startService(i);
//        }
        }
    }
}
