plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("module-plugin") {
            id = "module-plugin"
            implementationClass = "CommonModulePlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.3")
    implementation(kotlin("gradle-plugin", "1.5.30"))

    implementation(gradleApi())
    implementation(localGroovy())
}