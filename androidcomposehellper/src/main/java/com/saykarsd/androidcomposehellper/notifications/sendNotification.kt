package com.saykarsd.androidcomposehellper.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

fun SendNotification(context: Context, notificationChannelID: String, notificationName: CharSequence, notificationMessageText: CharSequence, notificationIcon: Int, notificationID: Int, notificationImportance: Int = NotificationManager.IMPORTANCE_MIN): Notification {
val notificationManager = context.getSystemService(NotificationManager::class.java)
val getOldNotificationChannel = notificationManager.getNotificationChannel(notificationChannelID)
if (getOldNotificationChannel==null) {
val channel = NotificationChannel(
notificationChannelID,
notificationName,
notificationImportance
)
notificationManager.createNotificationChannel(channel)
}
val notification = Notification.Builder(
context,
notificationChannelID
)
.setContentTitle(notificationName)
.setContentText(notificationMessageText)
.setSmallIcon(notificationIcon)
.build()
notificationManager.notify(notificationID, notification)
return notification
}

