package com.saykarsd.androidcomposehellper.overlays

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.Composable

object ComposeOverlayWindow {
private var overlayWindow: OverlayWindow? = null
fun show(
context: Context,
width: Int = WindowManager.LayoutParams.WRAP_CONTENT,
height: Int = WindowManager.LayoutParams.WRAP_CONTENT,
type: Int = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
gravity: Int = Gravity.TOP or Gravity.START,
title: String = "Overlay",
flags: Int = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
format: Int = PixelFormat.TRANSLUCENT,
content: @Composable () -> Unit
) {
if (overlayWindow != null) return
overlayWindow = OverlayWindow(context).apply {
addOverlay(
width = width,
height = height,
type = type,
windowGravity = gravity,
windowTitle = title,
flags = flags,
format = format,
content = content
)
}
}
fun hide() {
overlayWindow?.removeOverlay()
overlayWindow = null
}
}
