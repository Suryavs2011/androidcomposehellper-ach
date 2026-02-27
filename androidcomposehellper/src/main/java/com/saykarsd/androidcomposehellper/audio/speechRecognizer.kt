package com.saykarsd.androidcomposehellper.audio

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.util.Locale

object SpeechRecognizer {

fun create(
context:Context,
language:String=Locale.getDefault().toLanguageTag(),
languageModel:String=RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
enablePartialResults:Boolean=true,
preferOffline:Boolean=false,
maxResults:Int=5,
maskOffensiveWords:Boolean=false,
enableFormatting:Boolean=true,
formattingMode:String=RecognizerIntent.FORMATTING_OPTIMIZE_QUALITY,
enableLanguageDetection:Boolean=true,
silenceMinimumMillis:Long=1500L,
silenceCompleteMillis:Long=2000L,
silencePossiblyCompleteMillis:Long=1000L,
continuousDictation:Boolean=false,
onReady:(()->Unit)?=null,
onBeginning:(()->Unit)?=null,
onRmsChanged:((Float)->Unit)?=null,
onBufferReceived:((ByteArray?)->Unit)?=null,
onPartialResults:((List<String>)->Unit)?=null,
onResults:((List<String>)->Unit)?=null,
onEnd:(()->Unit)?=null,
onError:((Int,String)->Unit)?=null
):Triple<()->Unit,()->Unit,()->Unit>{

val speechRecognizer=SpeechRecognizer.createSpeechRecognizer(context)

val intent=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply{
putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,languageModel)
putExtra(RecognizerIntent.EXTRA_LANGUAGE,language)
putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,enablePartialResults)
putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE,preferOffline)
putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,maxResults)
putExtra(RecognizerIntent.EXTRA_MASK_OFFENSIVE_WORDS,maskOffensiveWords)
putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,silenceMinimumMillis)
putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,silenceCompleteMillis)
putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,silencePossiblyCompleteMillis)
if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU&&enableFormatting){
putExtra(RecognizerIntent.EXTRA_ENABLE_FORMATTING,formattingMode)
}
if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.UPSIDE_DOWN_CAKE&&enableLanguageDetection){
putExtra(RecognizerIntent.EXTRA_ENABLE_LANGUAGE_DETECTION,true)
}
}

var isDestroyed=false

speechRecognizer.setRecognitionListener(object:RecognitionListener{

override fun onReadyForSpeech(params:Bundle?){
onReady?.invoke()
}

override fun onBeginningOfSpeech(){
onBeginning?.invoke()
}

override fun onRmsChanged(rmsdB:Float){
onRmsChanged?.invoke(rmsdB)
}

override fun onBufferReceived(buffer:ByteArray?){
onBufferReceived?.invoke(buffer)
}

override fun onEndOfSpeech(){
onEnd?.invoke()
if(continuousDictation&&!isDestroyed){
speechRecognizer.startListening(intent)
}
}

override fun onError(error:Int){
val message=when(error){
SpeechRecognizer.ERROR_AUDIO->"Audio error"
SpeechRecognizer.ERROR_CLIENT->"Client error"
SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS->"Permission denied"
SpeechRecognizer.ERROR_NETWORK->"Network error"
SpeechRecognizer.ERROR_NETWORK_TIMEOUT->"Network timeout"
SpeechRecognizer.ERROR_NO_MATCH->"No match"
SpeechRecognizer.ERROR_RECOGNIZER_BUSY->"Recognizer busy"
SpeechRecognizer.ERROR_SERVER->"Server error"
SpeechRecognizer.ERROR_SPEECH_TIMEOUT->"Speech timeout"
else->"Unknown error"
}
onError?.invoke(error,message)
if(continuousDictation&&!isDestroyed){
speechRecognizer.startListening(intent)
}
}

override fun onResults(results:Bundle?){
val list=results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?: emptyList()
onResults?.invoke(list)
}

override fun onPartialResults(partialResults:Bundle?){
val list=partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?: emptyList()
onPartialResults?.invoke(list)
}

override fun onEvent(eventType:Int,params:Bundle?){}
})

val start:()->Unit={
if(!isDestroyed){
speechRecognizer.startListening(intent)
}
}

val stop:()->Unit={
if(!isDestroyed){
speechRecognizer.stopListening()
}
}

val destroy:()->Unit={
if(!isDestroyed){
isDestroyed=true
speechRecognizer.stopListening()
speechRecognizer.cancel()
speechRecognizer.destroy()
}
}

return Triple(start,stop,destroy)
}
}
