import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class CommonModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // apply plugin common to all projects
        project.plugins.apply("kotlin-android")
        project.plugins.apply("kotlin-parcelize")
        project.plugins.apply("androidx.navigation.safeargs.kotlin")

        // configure the android block
        val androidExtensions = project.extensions.getByName("android")
        if (androidExtensions is BaseExtension) {
            androidExtensions.apply {
                compileSdkVersion(30)
                buildToolsVersion = "30.0.3"

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
                
                ((androidExtensions as ExtensionAware)
                    .extensions
                    .getByName("kotlinOptions") as KotlinJvmOptions)
                    .jvmTarget = JavaVersion.VERSION_1_8.toString()

                project.tasks.withType(KotlinCompile::class.java).configureEach {
                    kotlinOptions {
                        jvmTarget = JavaVersion.VERSION_1_8.toString()
                    }
                }
                testOptions {
                    unitTests.isReturnDefaultValues = true
                }

                defaultConfig {
                    minSdk = 21
                    targetSdk = 29
                    versionCode = 1
                    versionName = "1.0"
                    multiDexEnabled = true
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                viewBinding.isEnabled = true
                buildFeatures.compose = true

                composeOptions {
                    kotlinCompilerExtensionVersion = "1.0.3"
                }

                when (this) {
                    is LibraryExtension -> {
                        defaultConfig {
                            // apply the pro guard rules for library
                            consumerProguardFiles("consumer-rules.pro")
                        }
                    }

                    is AppExtension -> {
                        buildTypes {
                            getByName("debug") {
                                buildConfigField(
                                    "String",
                                    "BASE_URL",
                                    "\"https://raw.githubusercontent.com/ToybethSystems/Dokto-Android/master/\""
                                )
                                buildConfigField("String", "DB_NAME", "\"app_db\"")
                            }
                            getByName("release") {
                                isMinifyEnabled = false
                                proguardFiles(
                                    getDefaultProguardFile("proguard-android-optimize.txt"),
                                    "proguard-rules.pro"
                                )
                                buildConfigField(
                                    "String",
                                    "BASE_URL",
                                    "\"https://raw.githubusercontent.com/ToybethSystems/Dokto-Android/master/\""
                                )
                                buildConfigField("String", "DB_NAME", "\"app_db\"")
                            }
                        }
                    }
                }
            }
        }

        // dependencies common to all projects
        project.dependencies {
            add(
                "implementation",
                "org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION"
            )
            add("implementation", "androidx.core:core-ktx:1.6.0")
            add("implementation", "androidx.appcompat:appcompat:1.3.1")
            add("implementation", "androidx.multidex:multidex:2.0.1")
            add("implementation", "androidx.fragment:fragment-ktx:1.3.6")
            add("implementation", "androidx.constraintlayout:constraintlayout:2.1.1")

            // material component
            add("implementation", "com.google.android.material:material:1.4.0")

            // gson
            add("implementation", "com.google.code.gson:gson:2.8.6")

            // rxjava2
            add("implementation", "io.reactivex.rxjava3:rxjava:3.1.1")
            add("implementation", "io.reactivex.rxjava3:rxandroid:3.0.0")

            // logging
            add("implementation", "com.orhanobut:logger:2.2.0")
            add("implementation", "com.squareup.okhttp3:logging-interceptor:4.9.1")

            // Navigation component
            add(
                "implementation",
                "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_COMPONENT_VERSION"
            )
            add(
                "implementation",
                "androidx.navigation:navigation-ui-ktx:$NAVIGATION_COMPONENT_VERSION"
            )

            // Jetpack compose
            add("implementation", "androidx.compose.ui:ui:$JETPACK_COMPOSE_VERSION")
            add("implementation", "androidx.compose.ui:ui-tooling:$JETPACK_COMPOSE_VERSION")
            add("implementation", "androidx.compose.foundation:foundation:$JETPACK_COMPOSE_VERSION")
            add("implementation", "androidx.compose.material:material:$JETPACK_COMPOSE_VERSION")
            add(
                "implementation",
                "androidx.compose.material:material-icons-core:$JETPACK_COMPOSE_VERSION"
            )
            add(
                "implementation",
                "androidx.compose.material:material-icons-extended:$JETPACK_COMPOSE_VERSION"
            )
            add(
                "implementation",
                "androidx.compose.runtime:runtime-livedata:$JETPACK_COMPOSE_VERSION"
            )
            add(
                "implementation",
                "androidx.compose.runtime:runtime-rxjava2:$JETPACK_COMPOSE_VERSION"
            )

            // Hilt
            add("implementation", "com.google.dagger:hilt-android:$HILT_VERSION")
            add("kapt", "com.google.dagger:hilt-compiler:$HILT_VERSION")
        }
    }
}