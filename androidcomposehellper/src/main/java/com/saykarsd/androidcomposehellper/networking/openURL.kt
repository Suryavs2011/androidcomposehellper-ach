package com.saykarsd.androidcomposehellper.networking

import android.content.Context
import android.content.Intent
import android.net.Uri

fun OpenURL(url: String, context: Context) {
val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
context.startActivity(intent)
}

