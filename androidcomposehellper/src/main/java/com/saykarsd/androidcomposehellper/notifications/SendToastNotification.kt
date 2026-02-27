package com.saykarsd.androidcomposehellper.notifications

import android.content.Context
import android.widget.Toast

fun SendToastNotification(context: Context, text: String) {
Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

