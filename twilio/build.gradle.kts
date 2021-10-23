plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

android {
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"https://api.paystack.co/\"")
            buildConfigField("String", "TWILIO_ACCESS_TOKEN", "\"${getProperty("TWILIO_ACCESS_TOKEN")}\"")

        }
    }
}

dependencies {
    implementation(project(":base"))

    implementation("com.twilio:video-android-ktx:7.0.1")
    implementation("com.twilio:conversations-android:2.0.0")
    implementation("com.twilio:audioswitch:1.1.2")
}
