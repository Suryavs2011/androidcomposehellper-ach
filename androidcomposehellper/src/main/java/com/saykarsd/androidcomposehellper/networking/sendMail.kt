package com.saykarsd.androidcomposehellper.networking

import android.content.Context
import android.content.Intent

fun SendMail(eMailID: String, subject: String = "", body: String = "", context: Context) {
val intent = Intent(Intent.ACTION_SENDTO).apply {
putExtra(Intent.EXTRA_EMAIL, arrayOf(eMailID))
putExtra(Intent.EXTRA_SUBJECT, subject)
putExtra(Intent.EXTRA_TEXT, body)
}
if (intent.resolveActivity(context.packageManager) != null) {
context.startActivity(intent)
}
}

