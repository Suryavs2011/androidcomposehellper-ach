package com.saykarsd.androidcomposehellper.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.isEditable
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextBox(
text: String,
customAccessibilityActions: List<CustomAccessibilityAction> = emptyList(),
value: String,
onValueChanged: (String) -> Unit,
onGo: () -> Unit = {},
singleLine: Boolean = true,
enabled: Boolean = true,
isPasswordField: Boolean = false,
readOnly: Boolean = false,
minLines: Int = 1,
maxLines: Int = 10,
keyboardType: KeyboardType = KeyboardType.Text,
fontSize: Int = 24,
fontStyle: FontStyle = FontStyle.Italic,
textAlign: TextAlign = TextAlign.Center,
modifier: Modifier = Modifier
) {
OutlinedTextField(
value = value,
onValueChange = onValueChanged,
label = { TextView(text = text, textAlign = TextAlign.Left) },
placeholder = {TextView(text = text)},
singleLine = singleLine,
maxLines = maxLines,
visualTransformation = if (!isPasswordField) VisualTransformation.None else PasswordVisualTransformation(mask = '*'),
keyboardOptions = KeyboardOptions.Default.copy(
imeAction = ImeAction.Go,
showKeyboardOnFocus = false,
autoCorrectEnabled = true,
capitalization = KeyboardCapitalization.Sentences,
keyboardType = keyboardType
),
readOnly = readOnly,
enabled = enabled,
minLines = minLines,
keyboardActions = KeyboardActions(
onGo = {
onGo()
}
),
textStyle = TextStyle.Default.copy(
fontStyle = fontStyle,
fontSize = fontSize.sp,
textAlign = textAlign
),
modifier = Modifier
.wrapContentSize(align = Alignment.Center)
.padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom = 0.dp)
.semantics(mergeDescendants = false) {
contentDescription=text
isEditable=true
customActions=customAccessibilityActions
}
.then(modifier)
)
}

