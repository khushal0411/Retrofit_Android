package com.nuv.retrofitapplication
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nuv.retrofitapplication.constant.Constants

class NotificationReceivers: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val content = intent?.getStringExtra("S")
        val bitmapFactory =BitmapFactory.decodeResource(context?.resources,R.drawable.ccccc)
        val channelId = intent?.getStringExtra(Constants.CHANNEL_ID)
        val intent1 = Intent(context, MoviesHomeScreenActivity::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0)
        val builder = NotificationCompat.Builder(
            context!!, channelId!!
        )
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle(channelId)
            .setContentText(content)
            .setColorized(true)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmapFactory))
            .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
        NotificationManagerCompat.from(context).notify(123,builder.build())
    }
}