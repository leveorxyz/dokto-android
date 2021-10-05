import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.addRetrofit() {
    dependencies {
        add("implementation", "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION")
        add("implementation", "com.squareup.retrofit2:adapter-rxjava3:$RETROFIT_VERSION")
        add("implementation", "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION")

        add("implementation", "com.squareup.okhttp3:okhttp:4.7.2")
        add("implementation", "com.squareup.okhttp3:logging-interceptor:4.9.1")
    }
}

fun Project.addTestDependencies() {
    dependencies {
        add("testImplementation", "junit:junit:4.13.2")
        add("testImplementation", "androidx.test.ext:junit:1.1.3")
        add("androidTestImplementation", "androidx.test.espresso:espresso-core:3.4.0")
        add("androidTestImplementation", "androidx.test.ext:junit:1.1.3")
        add("testImplementation", "org.mockito:mockito-core:3.10.0")
        add("testImplementation", "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
        add("testImplementation", "org.mockito:mockito-inline:3.9.0")
        add("testImplementation", "org.powermock:powermock-module-junit4:$POWERMOCK_VERSION")
        add("testImplementation", "org.powermock:powermock-core:$POWERMOCK_VERSION")
        add("testImplementation", "org.powermock:powermock-api-mockito2:$POWERMOCK_VERSION")
        add("testImplementation", "androidx.test:core:1.4.0")
        add("testImplementation", "androidx.test:runner:1.4.0")
        add("testImplementation", "androidx.test.ext:junit:1.1.3")
        add("testImplementation", "org.robolectric:robolectric:4.5.1")
    }
}