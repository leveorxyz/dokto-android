plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

android {
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "PAYPAL_CLIENT_ID", "\"${getProperty("PAYPAL_CLIENT_ID")}\"")
        }
    }
}

dependencies {
    implementation(project(":base"))
    api("com.paypal.checkout:android-sdk:0.3.1")
}