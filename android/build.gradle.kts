val buildToolsVersion: String by extra
val kotlinVersion: String by extra
val androidxBaseVersion: String by extra
val androidxUiVersion: String by extra
val androidMaterialVersion: String by extra
val androidConstraintLayoutVersion: String by extra
val androidMultidexVersion: String by extra

plugins {
    id("com.android.application")
    id("kotlin-multiplatform")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = buildToolsVersion
    defaultConfig {
        applicationId = "app.web.drjackycv.flickrmultiplatform"
        minSdkVersion(21)
        targetSdkVersion(29)
        multiDexEnabled = true
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    sourceSets {
        val main by getting
        main.java.srcDirs("src/main/kotlin")
        main.manifest.srcFile("src/main/AndroidManifest.xml")
        main.res.srcDirs("src/main/res")
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

kotlin {
    android()

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation("androidx.appcompat:appcompat:$androidxBaseVersion")
                implementation("androidx.core:core-ktx:$androidxBaseVersion")
                implementation("androidx.vectordrawable:vectordrawable:$androidxBaseVersion")
                implementation("com.google.android.material:material:$androidMaterialVersion")
                implementation("androidx.constraintlayout:constraintlayout:$androidConstraintLayoutVersion")
                implementation("com.android.support:multidex:$androidMultidexVersion")
                implementation(project(":shared"))
            }
        }
    }
}