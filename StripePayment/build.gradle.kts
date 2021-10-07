plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

dependencies {
    implementation(project(":base"))
    implementation("com.stripe:stripe-android:17.2.0")
}