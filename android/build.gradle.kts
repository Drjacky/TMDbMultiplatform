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
        applicationId = "app.web.drjackycv.omdbmultiplatform"
        minSdkVersion(21)
        targetSdkVersion(29)
        multiDexEnabled = true
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val debug by getting {
            buildConfigField("String", "API_KEY", "\"d69d5cbf\"")
        }
        val release by getting {
            buildConfigField("String", "API_KEY", "\"d69d5cbf\"")
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

    implementation(project(":shared"))
}