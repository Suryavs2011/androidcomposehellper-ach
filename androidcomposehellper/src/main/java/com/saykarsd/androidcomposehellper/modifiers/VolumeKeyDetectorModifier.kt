package com.saykarsd.androidcomposehellper.modifiers

import android.os.SystemClock
import android.view.KeyEvent
import androidx.compose.foundation.focusable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type

fun Modifier.volumeKeyDetector(
multiPressWindow: Long = 500L,
longPressThreshold: Long = 600L,
onVolumeUpSingle: () -> Unit = {},
onVolumeUpDouble: () -> Unit = {},
onVolumeUpTriple: () -> Unit = {},
onVolumeUpMultiple: (Int) -> Unit = {},
onVolumeUpLongPress: () -> Unit = {},
onVolumeDownSingle: () -> Unit = {},
onVolumeDownDouble: () -> Unit = {},
onVolumeDownTriple: () -> Unit = {},
onVolumeDownMultiple: (Int) -> Unit = {},
onVolumeDownLongPress: () -> Unit = {}
): Modifier = composed {
var upLastPressTime by remember { mutableStateOf(0L) }
var upPressCount by remember { mutableStateOf(0) }
var upDownTime by remember { mutableStateOf(0L) }
var downLastPressTime by remember { mutableStateOf(0L) }
var downPressCount by remember { mutableStateOf(0) }
var downDownTime by remember { mutableStateOf(0L) }
this
.focusable()
.onPreviewKeyEvent { event ->
val keyCode = event.nativeKeyEvent.keyCode
val currentTime = SystemClock.elapsedRealtime()
when (event.type) {
KeyEventType.KeyDown -> {
when (keyCode) {
KeyEvent.KEYCODE_VOLUME_UP -> {
if (upDownTime == 0L)
upDownTime = currentTime
val gap = currentTime - upLastPressTime
upPressCount =
if (gap < multiPressWindow) upPressCount + 1 else 1
upLastPressTime = currentTime
when (upPressCount) {
1 -> onVolumeUpSingle()
2 -> onVolumeUpDouble()
3 -> onVolumeUpTriple()
else -> onVolumeUpMultiple(upPressCount)
}
true
}
KeyEvent.KEYCODE_VOLUME_DOWN -> {
if (downDownTime == 0L)
downDownTime = currentTime
val gap = currentTime - downLastPressTime
downPressCount =
if (gap < multiPressWindow) downPressCount + 1 else 1
downLastPressTime = currentTime
when (downPressCount) {
1 -> onVolumeDownSingle()
2 -> onVolumeDownDouble()
3 -> onVolumeDownTriple()
else -> onVolumeDownMultiple(downPressCount)
}
true
}
else -> false
}
}
KeyEventType.KeyUp -> {
when (keyCode) {
KeyEvent.KEYCODE_VOLUME_UP -> {
val duration = currentTime - upDownTime
if (duration >= longPressThreshold) {
onVolumeUpLongPress()
}
upDownTime = 0L
true
}
KeyEvent.KEYCODE_VOLUME_DOWN -> {
val duration = currentTime - downDownTime
if (duration >= longPressThreshold) {
onVolumeDownLongPress()
}
downDownTime = 0L
true
}
else -> false
}
}
else -> false
}
}
}

