pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // jitpack can be added if you decide to use MPAndroidChart or other libs
        // maven { url = uri("https://jitpack.io") }
    }
}
rootProject.name = "AgwandaPredicts"
include(":app")
