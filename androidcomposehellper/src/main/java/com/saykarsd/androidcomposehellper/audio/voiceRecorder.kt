package com.saykarsd.androidcomposehellper.audio

import android.media.AudioFormat
import android.os.Environment
import com.saykarsd.androidcomposehellper.audio.wave.WaveRecorder
import java.io.File

object VoiceRecordingController {
private var recorder: WaveRecorder? = null
fun StartRecorder(folderPath: String, fileNameWithoutFormatExtension: String) {
val fp = File(Environment.getExternalStorageDirectory(), folderPath)
val file = File(fp, "$fileNameWithoutFormatExtension.wav")
if (!fp.exists()) {
fp.mkdirs()
}
recorder= WaveRecorder(filePath = file.absolutePath).apply {
noiseSuppressorActive=true
configureWaveSettings {
sampleRate = 48000
channels = AudioFormat.CHANNEL_IN_MONO
audioEncoding = AudioFormat.ENCODING_PCM_32BIT
}
}
recorder?.startRecording()
}
fun PauseRecorder() {
recorder?.pauseRecording()
}
fun ResumeRecorder() {
recorder?.resumeRecording()
}
fun StopRecorder() {
recorder?.stopRecording()
recorder=null
}
}

