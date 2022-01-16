plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

android {
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"https://api.stripe.com/\"")
            buildConfigField("String", "STRIPE_PUBLISHABLE_KEY", "\"pk_test_TYooMQauvdEDq54NiTphI7jx\"")
            buildConfigField("String", "STRIPE_API_KEY", "\"c2tfdGVzdF80ZUMzOUhxTHlqV0Rhcmp0VDF6ZHA3ZGM6\"")
        }
    }
}

dependencies {
    implementation(project(":base"))
    implementation("com.stripe:stripe-android:18.2.0")
}