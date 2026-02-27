package com.saykarsd.androidcomposehellper.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy

@Composable
fun DLG(title: String, message: String, onDismissRequest: () -> Unit = {}, doneBTNLabel: String, notDoneBTNLabel: String, doneBTNAction: () -> Unit, notDoneBTNAction: () -> Unit, modifier: Modifier= Modifier
) {
AlertDialog(
title = {TextView(title)},
text = {TextView(message)},
onDismissRequest = onDismissRequest,
confirmButton = {TextButton(text = doneBTNLabel, isButtonEnabled = true, onClick = doneBTNAction)},
dismissButton = {TextButton(text = notDoneBTNLabel, isButtonEnabled = true, onClick = notDoneBTNAction)},
properties = DialogProperties(
windowTitle = title,
dismissOnBackPress = false,
dismissOnClickOutside = false,
usePlatformDefaultWidth = true,
decorFitsSystemWindows = true,
securePolicy = SecureFlagPolicy.SecureOff
),
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom = 0.dp)
.semantics(mergeDescendants = false) {
paneTitle=title
liveRegion= LiveRegionMode.Polite
}
.then(modifier)
)
}

