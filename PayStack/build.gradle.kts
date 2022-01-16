plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

android {
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"https://api.paystack.co/\"")
            buildConfigField("String", "PAYSTACK_SECRET_KEY", "\"${getProperty("PAYSTACK_SECRET_KEY")}\"")
            buildConfigField("String", "PAYSTACK_PUBLIC_KEY", "\"${getProperty("PAYSTACK_PUBLIC_KEY")}\"")

        }
    }
}

dependencies {
    implementation(project(":base"))
    implementation("com.github.GwonHyeok:StickySwitch:0.0.16")
    implementation("co.paystack.android:paystack:3.1.3")
}
