package com.saykarsd.androidcomposehellper.networking

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.saykarsd.androidcomposehellper.notifications.SendToastNotification
import com.saykarsd.androidcomposehellper.permissions.CheckIsPermissionGranted

fun MakePhoneCall(context: Context, phoneNumberWithCC: String, errorMessage: String) {
if (phoneNumberWithCC.isBlank()) {
SendToastNotification(context, errorMessage)
return
}
if (CheckIsPermissionGranted(context, Manifest.permission.CALL_PHONE)) {
try {
val intent = Intent(Intent.ACTION_CALL).apply {
data=Uri.parse("tel:$phoneNumberWithCC")
addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}
context.startActivity(intent)
}
catch (e: Exception) {
SendToastNotification(context, e.message ?: errorMessage)
}
}
else {
SendToastNotification(context, errorMessage)
}
}

