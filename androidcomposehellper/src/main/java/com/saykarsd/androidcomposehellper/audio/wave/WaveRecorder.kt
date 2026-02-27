package com.saykarsd.androidcomposehellper.audio.wave

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.media.audiofx.NoiseSuppressor
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.saykarsd.androidcomposehellper.audio.wave.bitPerSample
import com.saykarsd.androidcomposehellper.audio.wave.calculateAmplitude
import com.saykarsd.androidcomposehellper.audio.wave.calculateDurationInMillis
import com.saykarsd.androidcomposehellper.audio.wave.calculateMinBufferSize
import com.saykarsd.androidcomposehellper.audio.wave.channelCount
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.math.BigDecimal
import java.util.LinkedList
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class WaveRecorder {
private var fileUri: Uri? = null
private var filePath: String? = null
private lateinit var context: Context

constructor(fileUri: Uri, context: Context) {
this.fileUri = fileUri
this.context = context
}

constructor(filePath: String) {
this.filePath = filePath
}

/**
* Configuration for recording audio file.
*/
@Deprecated(
"Use configureWaveSettings to set recording configuration. Access to this property will not be available in the future."
)
var waveConfig: WaveConfig = WaveConfig()

private var silenceDetectionConfig: SilenceDetectionConfig = SilenceDetectionConfig(1500)

var onAmplitudeListener: ((Int) -> Unit)? = null

var onAudioChunkCaptured: ((ByteArray) -> Unit)? = null

var onStateChangeListener: ((RecorderState) -> Unit)? = null

var onTimeElapsed: ((Long) -> Unit)? = null

var onTimeElapsedInMillis: ((Long) -> Unit)? = null

var noiseSuppressorActive: Boolean = false

var audioSessionId: Int = -1
private set

var silenceDetection: Boolean = false

private var isPaused = AtomicBoolean(false)
private var isSkipping = AtomicBoolean(false)
private lateinit var audioRecorder: AudioRecord
private var noiseSuppressor: NoiseSuppressor? = null
private var silenceDuration = BigDecimal.ZERO
private var currentState: RecorderState = RecorderState.STOP

fun configureWaveSettings(block: WaveConfig.() -> Unit): WaveRecorder {
waveConfig.apply(block)
return this
}

fun configureSilenceDetection(block: SilenceDetectionConfig.() -> Unit): WaveRecorder {
silenceDetectionConfig.apply(block)
return this
}

@OptIn(DelicateCoroutinesApi::class)
fun startRecording() {
if (!isAudioRecorderInitialized()) {
initializeAudioRecorder()
GlobalScope.launch(Dispatchers.IO) {
if (waveConfig.audioEncoding == AudioFormat.ENCODING_PCM_FLOAT) {
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
writeFloatAudioDataToStorage()
} else {
throw UnsupportedOperationException("Float audio is not supported on this version of Android. You need Android Android 6.0 or above")
}
} else
writeByteAudioDataToStorage()
}
}
}

@SuppressLint("MissingPermission")
private fun initializeAudioRecorder() {
audioRecorder = AudioRecord(
MediaRecorder.AudioSource.MIC,
waveConfig.sampleRate,
waveConfig.channels,
waveConfig.audioEncoding,
AudioRecord.getMinBufferSize(
waveConfig.sampleRate,
waveConfig.channels,
waveConfig.audioEncoding
)
)

audioSessionId = audioRecorder.audioSessionId
audioRecorder.startRecording()

if (noiseSuppressorActive) {
noiseSuppressor = NoiseSuppressor.create(audioRecorder.audioSessionId)
}
}

private suspend fun writeByteAudioDataToStorage() {
val bufferSize = calculateMinBufferSize(waveConfig)
val data = ByteArray(bufferSize)
val outputStream: OutputStream = if (fileUri != null) {
context.contentResolver.openOutputStream(fileUri ?: return) ?: return
} else {
val file = File(filePath!!)
FileOutputStream(file)
}
val dataOutputStream = DataOutputStream(outputStream)
val fileWriter = FileWriter(dataOutputStream, onAudioChunkCaptured)

val bufferSizeToKeep =
(waveConfig.sampleRate * channelCount(waveConfig.channels) * (bitPerSample(waveConfig.audioEncoding) / 8) * silenceDetectionConfig.bufferDurationInMillis / 1000).toInt()

val lastSkippedData = LinkedList<ByteArray>()

var fileDurationInMillis = BigDecimal.ZERO

while (audioRecorder.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
val operationStatus = audioRecorder.read(data, 0, bufferSize)

if (operationStatus != AudioRecord.ERROR_INVALID_OPERATION) {
val amplitude = getByteArrayAmplitude(data)

if (isPaused.get())
updateState(RecorderState.PAUSE)
else {

if (silenceDetection) {
handleByteSilenceState(
amplitude,
lastSkippedData,
data,
bufferSizeToKeep
)
}

if (!isSkipping.get()) {
updateState(RecorderState.RECORDING)
lastSkippedData.forEach {
fileDurationInMillis += calculateDurationInMillis(it, waveConfig)
}
fileWriter.writeDataToStream(lastSkippedData, data)
fileDurationInMillis += calculateDurationInMillis(data, waveConfig)
}
}

updateListeners(amplitude, fileDurationInMillis.toLong())
}
}
updateState(RecorderState.STOP)
cleanup(dataOutputStream)
}

@RequiresApi(Build.VERSION_CODES.M)
private suspend fun writeFloatAudioDataToStorage() {
val bufferSize = calculateMinBufferSize(waveConfig)
val data = FloatArray(bufferSize)
val outputStream: OutputStream = if (fileUri != null) {
context.contentResolver.openOutputStream(fileUri ?: return) ?: return
} else {
val file = File(filePath!!)
FileOutputStream(file)
}
val dataOutputStream = DataOutputStream(outputStream)
val fileWriter = FileWriter(dataOutputStream, onAudioChunkCaptured)

val bufferSizeToKeep =
(waveConfig.sampleRate * channelCount(waveConfig.channels) * silenceDetectionConfig.bufferDurationInMillis / 1000).toInt()

val lastSkippedData = LinkedList<FloatArray>()

var fileDurationInMillis = BigDecimal.ZERO

while (audioRecorder.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
val operationStatus = audioRecorder.read(data, 0, bufferSize, AudioRecord.READ_BLOCKING)

if (AudioRecord.ERROR_INVALID_OPERATION != operationStatus) {
val amplitude = getFloatArrayAmplitude(data)

if (isPaused.get())
updateState(RecorderState.PAUSE)
else {

if (silenceDetection) {
handleFloatSilenceState(
amplitude,
lastSkippedData,
data,
bufferSizeToKeep
)
}

if (!isSkipping.get()) {
updateState(RecorderState.RECORDING)
lastSkippedData.forEach {
fileDurationInMillis += calculateDurationInMillis(it, waveConfig)
}
fileWriter.writeDataToStream(lastSkippedData, data)
fileDurationInMillis += calculateDurationInMillis(data, waveConfig)
}
}
updateListeners(amplitude, fileDurationInMillis.toLong())
}
}
updateState(RecorderState.STOP)
cleanup(outputStream)
}

private fun getByteArrayAmplitude(data: ByteArray): Int {
return if (onAmplitudeListener != null || silenceDetection) calculateAmplitude(
data = data,
audioFormat = waveConfig.audioEncoding
) else 0
}

private fun getFloatArrayAmplitude(data: FloatArray): Int {
return if (onAmplitudeListener != null || silenceDetection) calculateAmplitude(
data = data,
) else 0
}

private suspend fun handleByteSilenceState(
amplitude: Int,
lastSkippedData: LinkedList<ByteArray>,
data: ByteArray,
bufferSizeToKeep: Int
) {
if (amplitude < silenceDetectionConfig.minAmplitudeThreshold) {
silenceDuration += calculateDurationInMillis(data, waveConfig)
if (silenceDuration.toLong() >= silenceDetectionConfig.preSilenceDurationInMillis) {
if (!isSkipping.get()) {
isSkipping.set(true)
updateState(RecorderState.SKIPPING_SILENCE)
}
lastSkippedData.addLast(data.copyOf())
if (lastSkippedData.sumOf { it.size } > bufferSizeToKeep) {
lastSkippedData.removeFirst()
}
}
} else {
silenceDuration = BigDecimal.ZERO
isSkipping.set(false)
}
}

private suspend fun handleFloatSilenceState(
amplitude: Int,
lastSkippedData: LinkedList<FloatArray>,
data: FloatArray,
bufferSizeToKeep: Int
) {
if (amplitude < silenceDetectionConfig.minAmplitudeThreshold) {
silenceDuration += calculateDurationInMillis(data, waveConfig)
if (silenceDuration.toLong() >= silenceDetectionConfig.preSilenceDurationInMillis) {

if (!isSkipping.get()) {
isSkipping.set(true)
updateState(RecorderState.SKIPPING_SILENCE)
}
lastSkippedData.addLast(data.copyOf())
if (lastSkippedData.sumOf { it.size } > bufferSizeToKeep) {
lastSkippedData.removeFirst()
}
}
} else {
silenceDuration = BigDecimal.ZERO
isSkipping.set(false)
}
}

private suspend fun updateListeners(amplitude: Int, fileDurationInMillis: Long) {
withContext(Dispatchers.Main) {
onAmplitudeListener?.let {
it(amplitude)
}
onTimeElapsed?.let {
it(TimeUnit.MILLISECONDS.toSeconds(fileDurationInMillis))
}
onTimeElapsedInMillis?.let {
it(fileDurationInMillis)
}
}
}


private suspend fun updateState(state: RecorderState) {

if (currentState != state) {
currentState = state
withContext(Dispatchers.Main) {
onStateChangeListener?.let {
it(state)
}
}
}
}

private fun cleanup(outputStream: OutputStream) {
outputStream.close()
noiseSuppressor?.release()
}

fun changeFilePath(newFilePath: String) {
if (isAudioRecorderInitialized() && audioRecorder.recordingState == AudioRecord.RECORDSTATE_RECORDING)
throw IllegalStateException("Cannot change filePath when still recording.")
else
filePath = newFilePath
}

fun changeFilePath(newFileUri: Uri) {
if (isAudioRecorderInitialized() && audioRecorder.recordingState == AudioRecord.RECORDSTATE_RECORDING)
throw IllegalStateException("Cannot change filePath when still recording.")
else
fileUri = newFileUri
}

fun stopRecording() {
if (isAudioRecorderInitialized()) {
audioRecorder.stop()
audioRecorder.release()
isPaused.set(false)
isSkipping.set(false)
silenceDuration = BigDecimal.ZERO
audioSessionId = -1
if (fileUri != null) {
WaveHeaderWriter(fileUri!!, context, waveConfig).writeHeader()
} else {
WaveHeaderWriter(filePath!!, waveConfig).writeHeader()
}

}

}

private fun isAudioRecorderInitialized(): Boolean =
this::audioRecorder.isInitialized && audioRecorder.state == AudioRecord.STATE_INITIALIZED

fun pauseRecording() {
isPaused.set(true)
}

fun resumeRecording() {
silenceDuration = BigDecimal.ZERO
isSkipping.set(false)
isPaused.set(false)
}

}
