package com.saykarsd.androidcomposehellper.functions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun GetDateTime() : String {
val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
return dateTime
}

