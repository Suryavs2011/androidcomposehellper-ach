package com.saykarsd.androidcomposehellper.functions

import android.app.Activity
import android.content.res.Configuration
import java.util.Locale

fun SetAppLanguageAtRuntime(langCode: String, activity: Activity) {
val locale = Locale.forLanguageTag(langCode)
Locale.setDefault(locale)
val config = Configuration(activity.resources.configuration)
config.setLocale(locale)
activity.applyOverrideConfiguration(config)
activity.recreate()
}

