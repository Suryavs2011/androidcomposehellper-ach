package com.saykarsd.androidcomposehellper.audio.wave

import java.io.DataOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.LinkedList

internal class FileWriter(
private val outputStream: DataOutputStream,
private val onAudioChunkCaptured: ((ByteArray) -> Unit)?
) {
fun writeDataToStream(
lastSkippedData: LinkedList<ByteArray>, data: ByteArray
) {
val totalSize = lastSkippedData.sumOf { it.size } + data.size
val byteBuffer = ByteBuffer.allocate(totalSize).order(ByteOrder.LITTLE_ENDIAN)

lastSkippedData.forEach { byteArray ->
byteBuffer.put(byteArray)
}
byteBuffer.put(data)
lastSkippedData.clear()

outputStream.write(byteBuffer.array())
onAudioChunkCaptured?.let {
it(byteBuffer.array())
}
}

fun writeDataToStream(
lastSkippedData: LinkedList<FloatArray>, data: FloatArray
) {
val totalFloats = lastSkippedData.sumOf { it.size } + data.size
val totalSize = totalFloats * 4
val byteBuffer = ByteBuffer.allocate(totalSize).order(ByteOrder.LITTLE_ENDIAN)

lastSkippedData.forEach { floatArray ->
floatArray.forEach { floatValue ->
byteBuffer.putFloat(floatValue)
}
}
data.forEach { floatValue ->
byteBuffer.putFloat(floatValue)
}
lastSkippedData.clear()

outputStream.write(byteBuffer.array())

onAudioChunkCaptured?.let {
it(byteBuffer.array())
}
}

}