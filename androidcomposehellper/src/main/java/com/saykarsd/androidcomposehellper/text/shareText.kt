package com.saykarsd.androidcomposehellper.text

import android.content.Context
import android.content.Intent
import com.saykarsd.androidcomposehellper.notifications.SendToastNotification

fun ShareText(textNotFoundMessage: String, text: String, context: Context) {
if (text.isNotBlank()) {
val intent = Intent(Intent.ACTION_SEND).apply {
type = "text/plain"
putExtra(Intent.EXTRA_TEXT, text)
}
context.startActivity(Intent.createChooser(intent, "Share"))
}
else {
SendToastNotification(context, textNotFoundMessage)
}
}

