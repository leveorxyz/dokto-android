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
        project.plugins.apply("kotlin-kapt")
        project.plugins.apply("kotlin-parcelize")
        project.plugins.apply("androidx.navigation.safeargs.kotlin")

        // configure the android block
        val androidExtensions = project.extensions.getByName("android")
        if (androidExtensions is BaseExtension) {
            androidExtensions.apply {
                compileSdkVersion(31)
//                buildToolsVersion = "30.0.3"

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
                    targetSdk = 31
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
                            buildConfigField(
                                "String",
                                "BASE_URL",
                                "\"https://raw.githubusercontent.com/ToybethSystems/Dokto-Android/master/\""
                            )
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
                            }
                        }
                    }
                }
                project.dependencies {
                    add("implementation", "com.google.dagger:hilt-android:$HILT_VERSION")
                    add("kapt", "com.google.dagger:hilt-compiler:$HILT_VERSION")
                }
            }
        }
    }
}