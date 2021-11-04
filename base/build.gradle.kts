plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

android {
    defaultConfig {

    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api("org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION")
    api("androidx.core:core-ktx:1.6.0")
    api("androidx.appcompat:appcompat:1.3.1")
    api("androidx.multidex:multidex:2.0.1")
    api("androidx.fragment:fragment-ktx:1.3.6")
    api("androidx.constraintlayout:constraintlayout:2.1.1")
    api("com.google.android.material:material:1.4.0")
    api("com.google.code.gson:gson:2.8.6")
    api("io.reactivex.rxjava3:rxjava:3.1.1")
    api("io.reactivex.rxjava3:rxandroid:3.0.0")
    api("com.orhanobut:logger:2.2.0")
    api("com.squareup.okhttp3:logging-interceptor:4.9.1")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")

    // Navigation component
    api("androidx.navigation:navigation-fragment-ktx:$NAVIGATION_COMPONENT_VERSION")
    api("androidx.navigation:navigation-ui-ktx:$NAVIGATION_COMPONENT_VERSION")

    // Jetpack compose
    api("androidx.compose.ui:ui:$JETPACK_COMPOSE_VERSION")
    api("androidx.compose.ui:ui-tooling:$JETPACK_COMPOSE_VERSION")
    api("androidx.compose.foundation:foundation:$JETPACK_COMPOSE_VERSION")
    api("androidx.compose.material:material:$JETPACK_COMPOSE_VERSION")
    api("androidx.compose.material:material-icons-core:$JETPACK_COMPOSE_VERSION")
    api("androidx.compose.material:material-icons-extended:$JETPACK_COMPOSE_VERSION")
    api("androidx.compose.runtime:runtime-livedata:$JETPACK_COMPOSE_VERSION")
    api("androidx.compose.runtime:runtime-rxjava2:$JETPACK_COMPOSE_VERSION")
    api("androidx.activity:activity-compose:$JETPACK_COMPOSE_VERSION")

    // Retrofit
    api("com.squareup.retrofit2:retrofit:$RETROFIT_VERSION")
    api("com.squareup.retrofit2:adapter-rxjava3:$RETROFIT_VERSION")
    api("com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION")
    api("com.squareup.okhttp3:okhttp:$OKHTTP3_VERSION")
    api("com.squareup.okhttp3:logging-interceptor:$OKHTTP3_VERSION")

    // Coroutine
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // Startup
    api("androidx.startup:startup-runtime:1.1.0")

    // ssp, sdp
    api("com.intuit.sdp:sdp-android:1.0.6")
    api("com.intuit.ssp:ssp-android:1.0.6")

}