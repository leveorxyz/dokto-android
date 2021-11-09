plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    defaultConfig {
        applicationId = "com.toybeth.dokto"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":base"))
    implementation(project(":twilio"))

    addTestDependencies()

    // mockwebserver
    testImplementation("com.squareup.okhttp3:mockwebserver:4.3.1")
}
