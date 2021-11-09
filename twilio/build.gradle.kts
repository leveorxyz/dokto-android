plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

android {
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "TWILIO_ACCESS_TOKEN", "\"${getProperty("TWILIO_ACCESS_TOKEN")}\"")

        }
    }
}

dependencies {
    implementation(project(":base"))

    implementation("com.twilio:video-android-ktx:7.0.2")
    implementation("com.twilio:conversations-android:2.0.0")
    api("com.twilio:audioswitch:1.1.3")
    implementation("org.uniflow-kt:uniflow-android:1.0.5")
}
