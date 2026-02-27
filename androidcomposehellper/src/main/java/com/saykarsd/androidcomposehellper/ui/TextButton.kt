package com.saykarsd.androidcomposehellper.ui

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextButton(text: String, isButtonEnabled: Boolean, customAccessibilityActions: List<CustomAccessibilityAction> = emptyList(), onClick: () -> Unit, onLongClick: () -> Unit = {}, onDoubleClick: () -> Unit = {}, fontSize: Int = 22, fontStyle: FontStyle = FontStyle.Italic, textAlign: TextAlign = TextAlign.Center, modifier: Modifier = Modifier
) {
Button(
onClick = onClick,
enabled = isButtonEnabled,
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom = 0.dp)
.combinedClickable(
onClick = onClick,
onLongClick = onLongClick,
onDoubleClick = onDoubleClick
).semantics(mergeDescendants = false) {
contentDescription=text
role= Role.Button
customActions = customAccessibilityActions
}
.then(modifier)
) {
Text(text = text,
fontSize = fontSize.sp,
textAlign = textAlign,
fontStyle = fontStyle
)
}
}

