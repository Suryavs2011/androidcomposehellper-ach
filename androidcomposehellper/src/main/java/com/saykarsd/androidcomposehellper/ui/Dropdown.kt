package com.saykarsd.androidcomposehellper.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy

@Composable
fun Dropdown(
label: String,
options: List<String>,
selectedOption: String,
onOptionSelected: (String) -> Unit,
enabled: Boolean = true
) {
var expanded by rememberSaveable { mutableStateOf(false) }
Column(
horizontalAlignment = Alignment.CenterHorizontally,
verticalArrangement = Arrangement.Top
) {
Box(
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.padding(top = 15.dp, start = 12.dp, end = 12.dp, bottom = 0.dp)
.semantics(mergeDescendants = false) {
contentDescription = label
role = Role.DropdownList
}
.clickable(
onClick = { expanded = true }
)
) {
TextView(text = selectedOption ?: label)
DropdownMenu(
properties = PopupProperties(
dismissOnBackPress = false,
dismissOnClickOutside = false,
usePlatformDefaultWidth = true,
excludeFromSystemGesture = false,
clippingEnabled = true,
focusable = true,
securePolicy = SecureFlagPolicy.SecureOff
),
expanded = expanded,
onDismissRequest = { expanded = false }
) {
options.forEach { option ->
DropdownMenuItem(
enabled = enabled,
text = { TextView(text = option) },
onClick = {
onOptionSelected(option)
expanded = false
}
)
}
}
}
}
}

