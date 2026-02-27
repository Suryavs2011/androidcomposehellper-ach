package com.saykarsd.androidcomposehellper.text
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object TextEncrypter {
private const val ALGORITHM = "AES"
private const val TRANSFORMATION = "AES"
fun Encode(data: String, passwordKey: String) : String {
val secretKey = SecretKeySpec(passwordKey.toByteArray(), ALGORITHM)
val cipher = Cipher.getInstance(TRANSFORMATION)
cipher.init(Cipher.ENCRYPT_MODE, secretKey)
val encoded = cipher.doFinal(data.toByteArray())
val encodedString = Base64.encodeToString(encoded, Base64.DEFAULT)
return encodedString
}
fun Decode(data: String, passwordKey: String) : String {
val secretKey = SecretKeySpec(passwordKey.toByteArray(), ALGORITHM)
val cipher = Cipher.getInstance(TRANSFORMATION)
cipher.init(Cipher.DECRYPT_MODE, secretKey)
val decoded = Base64.decode(data, Base64.DEFAULT)
val decodedString = cipher.doFinal(decoded)
return String(decodedString)
}
}

