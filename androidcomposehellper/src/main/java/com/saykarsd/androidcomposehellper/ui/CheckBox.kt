package com.saykarsd.androidcomposehellper.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxView(contentdescription: String, checked: Boolean, enabled: Boolean, onCheckedStateChange: (Boolean) -> Unit, modifier: Modifier = Modifier
) {
Row(
horizontalArrangement = Arrangement.Center,
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom = 0.dp)
.then(modifier)
) {
Checkbox(
checked = checked,
onCheckedChange = onCheckedStateChange,
enabled = enabled,
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom = 0.dp)
.semantics(mergeDescendants = false) {
contentDescription = contentDescription
role = Role.Checkbox
}
.then(modifier)
)
TextView(contentdescription)
}
}

