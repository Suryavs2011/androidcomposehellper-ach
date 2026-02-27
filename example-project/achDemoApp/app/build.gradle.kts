plugins {
alias(libs.plugins.android.application)
alias(libs.plugins.kotlin.compose)
}

android {
namespace = "com.saykarsd.achdemoapp"
compileSdk {
version = release(36) {
minorApiLevel = 1
}
}

defaultConfig {
applicationId = "com.saykarsd.achdemoapp"
minSdk = 30
targetSdk = 36
versionCode = 1
versionName = "1.0"

testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

buildTypes {
release {
isMinifyEnabled = false
proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
}
}
compileOptions {
sourceCompatibility = JavaVersion.VERSION_11
targetCompatibility = JavaVersion.VERSION_11
}
buildFeatures {
compose = true
}
}

dependencies {
implementation(libs.android.compose.hellper)
implementation(libs.androidx.core.ktx)
implementation(platform(libs.androidx.compose.bom))
testImplementation(libs.junit)
androidTestImplementation(libs.androidx.junit)
androidTestImplementation(platform(libs.androidx.compose.bom))
androidTestImplementation(libs.androidx.compose.ui.test.junit4)
debugImplementation(libs.androidx.compose.ui.tooling)
debugImplementation(libs.androidx.compose.ui.test.manifest)
}