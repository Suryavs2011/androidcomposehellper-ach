package com.saykarsd.androidcomposehellper.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun Img(
imageDescription: String = "",
customAccessibilityActions: List<CustomAccessibilityAction> = emptyList(),
painter: Painter,
contentScale: ContentScale = ContentScale.Fit,
alignment: Alignment = Alignment.Center,
alpha: Float = 1f,
colorFilter: ColorFilter? = null,
modifier: Modifier = Modifier
) {
Image(
contentDescription = imageDescription,
contentScale = contentScale,
colorFilter = colorFilter,
alpha = alpha,
alignment = alignment,
painter = painter,
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom = 0.dp)
.semantics(mergeDescendants = false) {
contentDescription=imageDescription
role= Role.Image
customActions=customAccessibilityActions
}
.then(modifier)
)
}

