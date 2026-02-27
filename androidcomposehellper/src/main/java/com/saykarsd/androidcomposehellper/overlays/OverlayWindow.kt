package com.saykarsd.androidcomposehellper.overlays

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class OverlayWindow(private val context: Context): LifecycleOwner,  ViewModelStoreOwner, SavedStateRegistryOwner {
override val lifecycle: Lifecycle = LifecycleRegistry(this)
override val viewModelStore: ViewModelStore = ViewModelStore()
private lateinit var savedStateRegistryController: SavedStateRegistryController
override val savedStateRegistry: SavedStateRegistry
get() = savedStateRegistryController.savedStateRegistry
private var windowManager: WindowManager? = null
private var composeView: ComposeView? = null
fun addOverlay(
width: Int = WindowManager.LayoutParams.WRAP_CONTENT,
height: Int = WindowManager.LayoutParams.WRAP_CONTENT,
type: Int = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
windowGravity: Int = Gravity.TOP or Gravity.START,
windowTitle: String = "Overlay",
flags: Int = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
format: Int = PixelFormat.TRANSLUCENT,
content: @Composable () -> Unit = {}
) {
if (composeView != null) return
savedStateRegistryController = SavedStateRegistryController.create(this)
savedStateRegistryController.performRestore(null)
(lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
composeView = ComposeView(context).apply {
setViewTreeLifecycleOwner(this@OverlayWindow)
setViewTreeSavedStateRegistryOwner(this@OverlayWindow)
setViewTreeViewModelStoreOwner(this@OverlayWindow)
setContent {
content()
}
}
val params = WindowManager.LayoutParams(width, height, type, flags, format).apply {
gravity = windowGravity
title = windowTitle
}
windowManager?.addView(composeView, params)
(lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_START)
(lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
}
fun removeOverlay() {
composeView?.let { windowManager?.removeView(it) }
composeView = null
(lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
}
}

