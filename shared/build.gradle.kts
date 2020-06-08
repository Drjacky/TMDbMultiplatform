import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

val buildToolsVersion: String by project
val kotlinVersion: String by extra
val reactiveVersion: String by extra

plugins {
    id("com.android.library")
    kotlin("multiplatform")
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
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("com.badoo.reaktive:reaktive:$reactiveVersion")
            }
        }

        val mobileMain by creating {
            dependsOn(commonMain)
        }

        val jvmMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
                api("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
            }
        }

        val androidMain by getting {
            dependsOn(mobileMain)
            dependsOn(jvmMain)
        }
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