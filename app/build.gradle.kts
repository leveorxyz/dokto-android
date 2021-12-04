plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    defaultConfig {
        applicationId = "com.toybethsystems.dokto"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":base"))
//    implementation(project(":PaypalPayment"))
    implementation(project(":StripePayment"))
    implementation(project(":FlutterwavePayment"))
    implementation(project(":PayStack"))
    implementation(project(":twilio"))
    implementation(project(":material-stepper"))

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.0.0")



    // mockwebserver
    testImplementation("com.squareup.okhttp3:mockwebserver:4.3.1")
    implementation("com.google.accompanist:accompanist-pager:0.21.1-beta")
    implementation("com.tbuonomo:dotsindicator:4.2")
}
