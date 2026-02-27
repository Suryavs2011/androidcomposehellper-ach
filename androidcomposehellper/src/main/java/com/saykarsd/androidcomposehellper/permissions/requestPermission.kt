package com.saykarsd.androidcomposehellper.permissions

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun RequestPermission(context: Context, onPermissionStatusChange: (Boolean) -> Unit) : (String) -> Unit {
val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
isGranted->
onPermissionStatusChange(isGranted)
}
return {
pn ->
if (!CheckIsPermissionGranted(context, pn)) {
permissionLauncher.launch(pn)
}
}
}

