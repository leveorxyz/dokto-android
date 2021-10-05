plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("module-plugin")
}

android {
    defaultConfig {
        applicationId = "com.toybeth.docto"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    addTestDependencies()

    // retrofit
    addRetrofit()

    // mockwebserver
    testImplementation("com.squareup.okhttp3:mockwebserver:4.3.1")
}
