package com.saykarsd.androidcomposehellper.permissions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun checkAndRequestDisplayOverlayPermission(context: Context) {
if (!Settings.canDrawOverlays(context)) {
context.startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${context.packageName}")))
}
else {

}
}

