package com.saykarsd.androidcomposehellper.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextView(text: String, customAccessibilityActions: List<CustomAccessibilityAction> = emptyList(), fontSize: Int = 24, textAlign: TextAlign = TextAlign.Center, fontStyle: FontStyle = FontStyle.Normal, modifier: Modifier = Modifier
) {
Text(text = text,
fontSize = fontSize.sp,
textAlign = textAlign,
fontStyle = fontStyle,
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom =0.dp)
.semantics(mergeDescendants = false) {
contentDescription=text
customActions=customAccessibilityActions
}
.then(modifier)
)
}

