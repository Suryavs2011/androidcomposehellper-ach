package com.saykarsd.androidcomposehellper.files

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlin.contracts.contract

@Composable
fun SaveFileInCustomLocation(fileBody: String) : (String) -> Unit {
val context = LocalContext.current
val fileSaver = rememberLauncherForActivityResult(
contract = ActivityResultContracts.CreateDocument("*/*"),
onResult = { uri: Uri? ->
uri?.let {
context.contentResolver.openOutputStream(it)?.use { output ->
output.write(fileBody.toByteArray())
output.flush()
}
}
}
)
return {
fn ->
fileSaver.launch(fn)
}
}

