package com.saykarsd.androidcomposehellper.audio

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
fun TTS(
speechRate: Float = 1.0f,
speech: Float = 1.0f,
volume: Float = 1.0f,
qMode: Int = TextToSpeech.QUEUE_FLUSH,
locale: Locale=Locale.US,
ttsEngines: List<String> = emptyList()
): (String) -> Unit {
val context = LocalContext.current
var isReady by remember { mutableStateOf(false) }
val tts = remember(ttsEngines) {
val tempTts = TextToSpeech(context, null)
val engines = tempTts.engines.map { it.name }
tempTts.shutdown()
val selectedEngine = ttsEngines.firstOrNull {
engineName ->
engineName in engines && run {
val temp = TextToSpeech(context, null, engineName)
val result = temp.setLanguage(locale)
temp.shutdown()
result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
}
}
TextToSpeech(context, { status ->
isReady = status == TextToSpeech.SUCCESS
}, selectedEngine)
}
DisposableEffect(tts) {
onDispose {
tts.stop()
tts.shutdown()
}
}
return speak@{ text ->
if (!isReady) return@speak
if (isReady) {
val bundle = Bundle().apply {
putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, volume)
}
tts.language = locale
tts.setSpeechRate(speechRate)
tts.setPitch(speech)
tts.speak(
text,
qMode,
bundle,
null
)
}
}
}


