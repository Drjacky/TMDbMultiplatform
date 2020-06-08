buildscript {
    val kotlinVersion: String by extra
    val gradleAndroidVersion: String by extra

    repositories {
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        maven("https://dl.bintray.com/kotlin/kotlin-dev")

        google()
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.android.tools.build:gradle:$gradleAndroidVersion")
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlinx")
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
        maven("https://dl.bintray.com/badoo/maven")

        google()
        jcenter()
    }

    //In case of the same error explained here(https://youtrack.jetbrains.com/issue/KT-27170), uncomment below line:
    //configurations.create("compileClasspath")
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
