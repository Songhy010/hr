package biz.bizsolution.hrportal.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import java.util.List;

import androidx.core.app.NotificationCompat;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.activity.ActivityMain;
import me.leolin.shortcutbadger.ShortcutBadger;

public class FCMBroadcastReceiver extends BroadcastReceiver {

    private Notification notification;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.e("onReceive", "1");
            initCountBadge(context);

            Bundle extras = intent.getExtras();


            Log.e("Received: ", extras.getString("gcm.notification.type"));

            if (new ForegroundCheckTask().execute(context).get()) {
                sendNotification(context,extras.getString("body"),extras.getString("title"));
            }

        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }



    private void initCountBadge(Context context) {
        final String badgeCount = MyFunction.getInstance().getText(context, Global.NOTIFICATION_BADGE);
        int count = 1;
        if (!badgeCount.equals("")) {
            count = Integer.parseInt(badgeCount) + 1;
        }
        ShortcutBadger.applyCount(context,count);
        Log.e("Received Badge ", badgeCount);
        MyFunction.getInstance().saveText(context, Global.NOTIFICATION_BADGE, String.valueOf(count));
    }

    private class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            final Context context = params[0].getApplicationContext();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return isAppOnForeground(context);
            } else {
                return isAppOnForegroundPreLollipop(context);
            }
        }

        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }

        private boolean isAppOnForegroundPreLollipop(Context context) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
            String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();
            return foregroundTaskPackageName.toLowerCase().equals(context.getPackageName().toLowerCase());
        }
    }

    private void sendNotification(Context context, String message, String title) {
        try {
            // NotificationCompat.Builder notificationBuilder;
            Intent intent;
            intent = new Intent(context, ActivityMain.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0  /*Request code*/, intent, PendingIntent.FLAG_ONE_SHOT);
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

            // Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String CHANNEL_ID = "channel-01";
                String channelName = "Aircon Repairer";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, channelName, importance);
                mChannel.setShowBadge(true);
                mChannel.canShowBadge();

                mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        //.setSmallIcon(R.drawable.)
                        .setLargeIcon(largeIcon)
                        .setContentTitle(title).setNumber(2).setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentText(message);
                notification = mBuilder.build();
                mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotifyManager.createNotificationChannel(mChannel);
                mNotifyManager.notify(1, notification);
            } else {
                mBuilder = new NotificationCompat.Builder(context, "notify_001")
                        //.setSmallIcon(R.drawable.img_nav_ford)
                        .setLargeIcon(largeIcon)
                        .setContentTitle(title).setNumber(2).setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotifyManager.notify(1, mBuilder.build());
            }
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }
}
