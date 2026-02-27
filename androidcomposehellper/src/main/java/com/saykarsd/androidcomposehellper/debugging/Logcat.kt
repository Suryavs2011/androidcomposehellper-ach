package com.saykarsd.androidcomposehellper.debugging
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import kotlin.system.exitProcess

object Logcat{
fun init(applicationContext: Context){
val defaultHandler=Thread.getDefaultUncaughtExceptionHandler()
Thread.setDefaultUncaughtExceptionHandler{thread,throwable->
val appName=applicationContext.applicationInfo.loadLabel(applicationContext.packageManager).toString()
val packageInfo = applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0)
val versionName = packageInfo.versionName
val versionCode = packageInfo.longVersionCode
val crashMessage=throwable.localizedMessage?:"Unknown crash"
val fullLog=throwable.stackTraceToString()
val logText=buildString{
appendLine("========== APP CRASH REPORT ==========")
appendLine("Application: $appName")
appendLine("Message: $crashMessage")
appendLine()
appendLine("---------- DEVICE INFO ----------")
appendLine("Manufacturer: ${Build.MANUFACTURER}")
appendLine("Brand: ${Build.BRAND}")
appendLine("Model: ${Build.MODEL}")
appendLine("Device: ${Build.DEVICE}")
appendLine("Product: ${Build.PRODUCT}")
appendLine("Android Version: ${Build.VERSION.RELEASE}")
appendLine("SDK Level: ${Build.VERSION.SDK_INT}")
appendLine()
appendLine("---------- APP INFO ----------")
appendLine("Version Name: $versionName")
appendLine("Version Code: $versionCode")
appendLine("Package Name: ${applicationContext.packageName}")
appendLine()
appendLine("---------- STACKTRACE ----------")
appendLine(fullLog)
appendLine("======================================")
}
try{
val clipboard=applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
val clip=ClipData.newPlainText("Crash Log",logText)
clipboard.setPrimaryClip(clip)
}catch(_:Exception){}
defaultHandler?.uncaughtException(thread,throwable)
android.os.Process.killProcess(android.os.Process.myPid())
exitProcess(1)
}
}
}
