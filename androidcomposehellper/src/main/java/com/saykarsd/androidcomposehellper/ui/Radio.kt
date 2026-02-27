package com.saykarsd.androidcomposehellper.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun Radio(
options: List<String>,
selectedOption: String,
enabled: Boolean = true,
onOptionSelected: (String) -> Unit
) {
Column(
horizontalAlignment = Alignment.CenterHorizontally,
verticalArrangement = Arrangement.Top
) {
options.forEach { option ->
Row(
horizontalArrangement = Arrangement.Center,
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.padding(top = 15.dp, start = 12.dp, end = 12.dp, bottom = 0.dp)
) {
RadioButton(
selected = (option == selectedOption),
onClick = { onOptionSelected(option) },
enabled = enabled,
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.semantics(mergeDescendants = false) {
contentDescription = option
role = Role.RadioButton
}
.clickable(
onClick = { onOptionSelected(option) }
)
)
Spacer(modifier = Modifier.width(10.dp))
TextView(text = option, modifier = Modifier.height(60.dp))
}
}
}
}


