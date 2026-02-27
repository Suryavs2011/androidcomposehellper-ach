package com.saykarsd.androidcomposehellper.text

import android.content.Context
import androidx.compose.ui.platform.Clipboard
import com.saykarsd.androidcomposehellper.notifications.SendToastNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun PasteText(textNotFoundMessage: String, clipboard: Clipboard, scope: CoroutineScope, context: Context, callback: (String) -> Unit) {
scope.launch {
val clip = clipboard.getClipEntry()
if (clip!=null) {
val text = clip.clipData.getItemAt(0).text?.toString()?:""
callback(text)
}
else {
SendToastNotification(context, textNotFoundMessage)
}
}
}

