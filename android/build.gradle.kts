import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val buildToolsVersion: String by project
val kotlinVersion: String by project
val androidxBaseVersion: String by project
val androidxUiVersion: String by project
val androidMaterialVersion: String by project
val androidConstraintLayoutVersion: String by project
val androidMultidexVersion: String by project
val ktorVersion: String by project
val glideVersion: String by project
val reactiveVersion: String by project
val kodeinVersion: String by project

plugins {
    id("com.android.application")
    kotlin("android") // = id("kotlin-android")
    kotlin("android.extensions")
    kotlin("kapt")
}

androidExtensions {
    isExperimental = true
}

android {
    compileSdkVersion(29)
    buildToolsVersion = buildToolsVersion
    defaultConfig {
        applicationId = "app.web.drjackycv.tmdbmultiplatform"
        minSdkVersion(21)
        targetSdkVersion(29)
        multiDexEnabled = true
        versionCode = 2
        versionName = "1.1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val debug by getting {}
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    sourceSets {
        val main by getting {
            java.srcDirs("src/main/kotlin")
            manifest.srcFile("src/main/AndroidManifest.xml")
            res.srcDirs("src/main/res")
        }
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:$androidxBaseVersion")
    implementation("androidx.core:core-ktx:$androidxBaseVersion")
    implementation("com.google.android.material:material:$androidMaterialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$androidConstraintLayoutVersion")
    implementation("com.android.support:multidex:$androidMultidexVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")
    implementation("com.badoo.reaktive:reaktive:$reactiveVersion")
    implementation("org.kodein.di:kodein-di-framework-android-x:$kodeinVersion")

    implementation(project(":shared"))
}