package com.minzou.servicedemo;

import android.app.ActivityManager;
import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minzou on 16/5/20.
 */
public class Utils {

    /**
     * 判断服务(Service)是否已经启动
     * <p>
     * 判断步骤:
     * 1 利用ActivityManager得到所有正在运行的Service的集合.
     * 2 判断该集合是否包含某一服务
     * <p>
     * 注意事项:
     * 1 不需要什么权限
     * 2 在调用getRunningServices(int maxNum)方法时将maxNum的值设大一些.
     * 有这么一种可能maxNum=30时返回的集合不包括需要判断的服务,
     * 但设置maxNum=100时所获取到的集合里包括了需要判断的服务
     * 3 ActivityManager还有一方法getRecentTasks(maxNum, flags)可判断最近的任务
     * 4 ActivityManager可以获取运行中的BroadcastReceiver和ContentProvider么?
     * 不可以.
     */
    public static boolean isWorked(Context context, String className) {
        ActivityManager myManager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(400);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(className)) {
                return true;
            }
        }
        return false;
    }


    public static List<String> getPictures(final String strPath) {
        List<String> list = new ArrayList<String>();
        File file = new File(strPath);
        File[] allfiles = file.listFiles();
        if (allfiles == null) {
            return null;
        }
        for(int k = 0; k < allfiles.length; k++) {
            final File fi = allfiles[k];
            if(fi.isFile()) {
                int idx = fi.getPath().lastIndexOf(".");
                if (idx <= 0) {
                    continue;
                }
                String suffix = fi.getPath().substring(idx);
                if (suffix.toLowerCase().equals(".jpg") ||
                        suffix.toLowerCase().equals(".jpeg") ||
                        suffix.toLowerCase().equals(".bmp") ||
                        suffix.toLowerCase().equals(".png") ||
                        suffix.toLowerCase().equals(".gif") ) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }
}
