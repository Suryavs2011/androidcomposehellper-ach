package com.saykarsd.androidcomposehellper.text

import android.content.ClipData
import android.content.Context
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import com.saykarsd.androidcomposehellper.notifications.SendToastNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CopyText(text: String, textNotFound: String, clipboard: Clipboard, scope: CoroutineScope, context: Context) {
if (text.isNotBlank()) {
scope.launch {
clipboard.setClipEntry(ClipEntry(ClipData.newPlainText("plain_text", text)))
}
}
else {
SendToastNotification(context, textNotFound)
}
}

