package com.saykarsd.androidcomposehellper.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat

fun CheckIsPermissionGranted(context: Context, permission: String): Boolean {
return ContextCompat.checkSelfPermission(context, permission)==PackageManager.PERMISSION_GRANTED
}

