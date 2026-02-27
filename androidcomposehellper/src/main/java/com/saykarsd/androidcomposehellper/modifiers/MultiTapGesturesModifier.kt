package com.saykarsd.androidcomposehellper.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.multiTapGestures(
onOneSingle: () -> Unit = {},
onOneDouble: () -> Unit = {},
onOneTriple: () -> Unit = {},
onTwoSingle: () -> Unit = {},
onTwoDouble: () -> Unit = {},
onTwoTriple: () -> Unit = {},
onThreeSingle: () -> Unit = {},
onThreeDouble: () -> Unit = {},
onThreeTriple: () -> Unit = {},
onFourSingle: () -> Unit = {},
onFourDouble: () -> Unit = {},
onFourTriple: () -> Unit = {}
): Modifier = pointerInput(Unit) {
awaitPointerEventScope {
var tapCount = 0
var lastTapTime = 0L
val tapDelay = 300L
while (true) {
val down = awaitPointerEvent()
val fingerCount = down.changes.count { it.pressed }
val up = awaitPointerEvent()
if (up.changes.all { it.changedToUp() }) {
val currentTime = System.currentTimeMillis()
tapCount =
if (currentTime - lastTapTime <= tapDelay)
tapCount + 1
else
1
lastTapTime = currentTime
when (fingerCount) {
1 -> when (tapCount) {
1 -> onOneSingle()
2 -> onOneDouble()
3 -> onOneTriple()
}
2 -> when (tapCount) {
1 -> onTwoSingle()
2 -> onTwoDouble()
3 -> onTwoTriple()
}
3 -> when (tapCount) {
1 -> onThreeSingle()
2 -> onThreeDouble()
3 -> onThreeTriple()
}
4 -> when (tapCount) {
1 -> onFourSingle()
2 -> onFourDouble()
3 -> onFourTriple()
}
}
}
}
}
}
