plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

android {
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"https://api.stripe.com/\"")
            buildConfigField("String", "STRIPE_PUBLISHABLE_KEY", "\"${getProperty("STRIPE_PUBLISHABLE_KEY")}\"")
            buildConfigField("String", "STRIPE_API_KEY", "\"${getProperty("STRIPE_API_KEY")}\"")
        }
    }
}

dependencies {
    implementation(project(":base"))
    implementation("com.stripe:stripe-android:17.2.0")
}