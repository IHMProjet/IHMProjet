package Viewui.Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Button;

import androidx.core.app.NotificationCompat;

import com.example.ihmproject.R;
import com.flag.myapplication.car.utils.Xutils;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.Objects;
import java.util.Stack;

import Viewui.BaseActivity;

public class ChannelNotification extends Application {
    public static final String CHANNEL_ID = "channel1";
    public static final String CHANNELID ="channel2";
    private static NotificationManager notificationManager;
    private Notification notification;
    private Button button;


    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //是否输出debug日志
        x.Ext.setDebug(BuildConfig.DEBUG);
        //数据库配置
        Xutils.initDbConfiginit();
        createNotificationChannel("channel", "Channel pour créer des notifications", NotificationManager.IMPORTANCE_DEFAULT);
        createNotificationChannel2("channel2","important information",NotificationManager.IMPORTANCE_HIGH);
    }

    private void createNotificationChannel(String nameOfChannel, String description, int importanceDefault) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, description, importanceDefault);
            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);


        }
    }
    private void createNotificationChannel2(String nameOfChannel, String description, int importanceDefault) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNELID, description, importanceDefault);
            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);


        }
    }

    void createNotification(Context context, Class<?> cls){

            Intent intent = new Intent(context, cls);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Attention!")
                    .setContentText("Il y a un nouveau incident!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);



    }




}