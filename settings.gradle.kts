pluginManagement {
repositories {
google()
mavenCentral()
gradlePluginPortal()
}
}

dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
google()
mavenCentral()
}
}

rootProject.name = "android-compose-hellper"
include(":androidcomposehellper")
