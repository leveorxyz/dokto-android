plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

android {
    defaultConfig {
        applicationId = "com.toybethsystems.dokto"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":base"))
    implementation(project(":twilio"))
    implementation(project(":material-stepper"))

    addTestDependencies()

    // mockwebserver
    testImplementation("com.squareup.okhttp3:mockwebserver:4.3.1")
    implementation("com.google.accompanist:accompanist-pager:0.21.1-beta")
    implementation("com.tbuonomo:dotsindicator:4.2")
}
