plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

android {
    buildTypes {
        getByName("debug") {
            buildConfigField(
                "String",
                "FLUTTERWAVE_PUBLIC_KEY",
                "\"FLWPUBK_TEST-6a2413e244406e1c7a5084c2a6735892-X\""
            )
            buildConfigField(
                "String",
                "FLUTTERWAVE_ENCRYPTION_KEY",
                "\"FLWSECK_TEST4a243ca58156\""
            )
        }
    }
}

dependencies {
    implementation(project(":base"))
    implementation("com.github.flutterwave.rave-android:rave_android:2.1.37")
}