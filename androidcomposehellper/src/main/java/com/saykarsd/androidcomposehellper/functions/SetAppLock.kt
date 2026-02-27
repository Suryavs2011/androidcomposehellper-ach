package com.saykarsd.androidcomposehellper.functions

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.saykarsd.androidcomposehellper.notifications.SendToastNotification

fun SetBiometricAppLock(tryAgainMessage: String = "Try ones more time!", title: String, subTitle: String, activity: FragmentActivity, onSuccess: () -> Unit, onError: () -> Unit) {
val biometricManager = BiometricManager.from(activity)
val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
val canceled = setOf(
BiometricPrompt.ERROR_CANCELED,
BiometricPrompt.ERROR_NEGATIVE_BUTTON,
BiometricPrompt.ERROR_USER_CANCELED
)
if (biometricManager.canAuthenticate(authenticators) != BiometricManager.BIOMETRIC_SUCCESS) {
onError()
return
}
val executor = ContextCompat.getMainExecutor(activity)
val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
override fun onAuthenticationSucceeded( result: BiometricPrompt.AuthenticationResult ) {
super.onAuthenticationSucceeded(result)
onSuccess()
}
override fun onAuthenticationError( errorCode: Int, errString: CharSequence ) {
super.onAuthenticationError(errorCode, errString)
if (errorCode !in canceled) {
onError()
}
}
override fun onAuthenticationFailed() {
super.onAuthenticationFailed()
SendToastNotification(activity, tryAgainMessage)
}
})
val promptInfo = BiometricPrompt.PromptInfo.Builder()
.setTitle(title)
.setSubtitle(subTitle)
.setAllowedAuthenticators(authenticators)
.build()
biometricPrompt.authenticate(promptInfo)
}

