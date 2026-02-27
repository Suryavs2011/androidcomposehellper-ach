package com.saykarsd.androidcomposehellper.audio.wave

import android.media.AudioFormat

data class WaveConfig(
var sampleRate: Int = 16000,
var channels: Int = AudioFormat.CHANNEL_IN_MONO,
var audioEncoding: Int = AudioFormat.ENCODING_PCM_16BIT
)

internal fun bitPerSample(audioEncoding: Int) = when (audioEncoding) {
AudioFormat.ENCODING_PCM_8BIT -> 8
AudioFormat.ENCODING_PCM_16BIT -> 16
AudioFormat.ENCODING_PCM_32BIT -> 32
AudioFormat.ENCODING_PCM_FLOAT -> 32
else -> throw IllegalArgumentException("Unsupported audio format for encoding $audioEncoding")
}

internal fun channelCount(channels: Int) = when (channels) {
AudioFormat.CHANNEL_IN_MONO -> 1
AudioFormat.CHANNEL_IN_STEREO -> 2
else -> throw IllegalArgumentException("Unsupported audio channel")
}