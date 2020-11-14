import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val buildToolsVersion: String by project
val kotlinVersion: String by extra
val reactiveVersion: String by extra
val kotlinSerializationVersion: String by extra
val ktorVersion: String by extra
val coroutinesVersion: String by extra
val kodeinVersion: String by extra

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.4.20-RC" //org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION
}

android {
    compileSdkVersion(29)
    buildToolsVersion = buildToolsVersion
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
    }
    sourceSets {
        val main by getting {
            java.srcDirs("src/androidMain/kotlin")
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}

kotlin {
    jvm()
    //jvm("android")
    android()

    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //implementation("org.jetbrains.kotlin:kotlin-stdlib")
                //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("com.badoo.reaktive:reaktive:$reactiveVersion")
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$kotlinSerializationVersion")
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinSerializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("org.kodein.di:kodein-di:$kodeinVersion")
            }
        }

        val mobileMain by creating {
            dependsOn(commonMain)
        }

        val jvmMain by getting {
            dependencies {
                //api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
                //api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
                //api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$kotlinSerializationVersion")
                //api("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
                //api("io.ktor:ktor-client-core-jvm:$ktorVersion")
                //api("io.ktor:ktor-client-logging-jvm:$ktorVersion")
            }
        }

        val androidMain by getting {
            dependsOn(mobileMain)
            dependsOn(jvmMain)
            dependencies {
                //implementation("io.ktor:ktor-client-android:$ktorVersion")
            }
        }

        val iosMain by getting {
            dependsOn(mobileMain)
            dependencies {
                //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutinesVersion")
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$kotlinSerializationVersion")
                //implementation("io.ktor:ktor-client-ios:$ktorVersion")
                //implementation("io.ktor:ktor-client-serialization-native:$ktorVersion")
                //implementation("io.ktor:ktor-client-logging-native:$ktorVersion")
            }
        }

        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
        }
    }

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")
    //selecting the right configuration for the iOS framework depending on the Xcode environment variables
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)

    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\nexport 'JAVA_HOME=${System.getProperty("java.home")}'\ncd '${rootProject.rootDir}'\n./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)