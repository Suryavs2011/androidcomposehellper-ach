plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.compose)
alias(libs.plugins.maven.publish)
}

android {
namespace = "com.saykarsd.androidcomposehellper"
compileSdk = 36

defaultConfig {
minSdk = 30
consumerProguardFiles("consumer-rules.pro")
}
buildFeatures {
compose = true
}

buildTypes {
release {
proguardFiles(
getDefaultProguardFile("proguard-android-optimize.txt"),
"proguard-rules.pro"
)
}
}

publishing {
singleVariant("release") {
withSourcesJar()
withJavadocJar()
}
}

compileOptions {
sourceCompatibility = JavaVersion.VERSION_21
targetCompatibility = JavaVersion.VERSION_21
}
}

kotlin {
compilerOptions {
jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
}
}

dependencies {

api(libs.compose.material3.adaptive.navigation)
api(libs.compose.ui.test)
api(libs.serialization)
api(libs.compose.material3.adaptive)
api(libs.compose.material3.window.sizeClass)
api(libs.document.file)
api(libs.lifecycle.service)
api(libs.lifecycle.runtime.compose)
api(libs.biometric)
api(libs.core.ktx)
implementation(platform(libs.compose.bom))
api(libs.compose.ui)
api(libs.compose.ui.preview)
api(libs.compose.material3)
api(libs.compose.foundation)
api(libs.activity.compose)
api(libs.compose.animation)
api(libs.compose.material.icons.extended)
api(libs.compose.constraintlayout)
api(libs.compose.viewmodel)
api(libs.compose.paging)
api(libs.compose.navigation)
api(libs.compose.maps)
debugImplementation(libs.compose.ui.tooling)
debugImplementation(libs.compose.ui.test.manifest)
testImplementation(libs.junit4)
androidTestImplementation(libs.androidx.junit)
androidTestImplementation(libs.espresso.core)
androidTestImplementation(platform(libs.compose.bom))
androidTestImplementation(libs.compose.ui.test.junit4)
}

publishing {
publications {
create<MavenPublication>("release") {
groupId = "com.saykarsd.androidcomposehellper"
artifactId = "androidcomposehellper-ach"
version = "1.0.0"

afterEvaluate {
from(components["release"])
}
}
}
}


