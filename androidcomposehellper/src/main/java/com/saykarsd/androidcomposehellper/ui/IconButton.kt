package com.saykarsd.androidcomposehellper.ui

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun IconBTN(contentdescription: String, icon: ImageVector, isButtonEnabled: Boolean, customAccessibilityActions: List<CustomAccessibilityAction> = emptyList(), onClick: () -> Unit, onLongClick: () -> Unit = {}, onDoubleClick: () -> Unit = {}, modifier: Modifier = Modifier
) {
IconButton(
onClick = onClick,
enabled = isButtonEnabled,
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom = 0.dp)
.combinedClickable(
onClick = onClick,
onLongClick = onLongClick,
onDoubleClick = onDoubleClick
)
.semantics(mergeDescendants = false) {
contentDescription=contentdescription
role= Role.Button
customActions = customAccessibilityActions
}
.then(modifier)
) {
Icon(icon, contentdescription)
}
}

