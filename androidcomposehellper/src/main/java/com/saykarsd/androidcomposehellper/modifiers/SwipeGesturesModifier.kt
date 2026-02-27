package com.saykarsd.androidcomposehellper.modifiers

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs

fun Modifier.swipeGestures(
onSwipeLeft: () -> Unit = {},
onSwipeRight: () -> Unit = {},
onSwipeUp: () -> Unit = {},
onSwipeDown: () -> Unit = {}
): Modifier = pointerInput(Unit) {
detectDragGestures { change, dragAmount ->
val x = dragAmount.x
val y = dragAmount.y
val threshold = 50f
when {
abs(x) > abs(y) && x > threshold -> onSwipeRight()
abs(x) > abs(y) && x < -threshold -> onSwipeLeft()
abs(y) > abs(x) && y > threshold -> onSwipeDown()
abs(y) > abs(x) && y < -threshold -> onSwipeUp()
}
change.consume()
}
}