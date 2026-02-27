package com.saykarsd.androidcomposehellper.permissions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
fun checkAndRequestManageExternalStoragePermission(context: Context) {
if (!Environment.isExternalStorageManager()) {
val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
data = Uri.parse("package:${context.packageName}")
}
context.startActivity(intent)
}
else {

}
}

