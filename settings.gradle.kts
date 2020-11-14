pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                //"kotlinx-serialization" -> useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
                "kotlinx-serialization" -> useModule("org.jetbrains.kotlin:kotlin-serialization:1.4.20-RC")
                "kotlin-multiplatform" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20-RC")
                //"kotlin-platform-js" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
            }
        }
    }
}

//enableFeaturePreview("GRADLE_METADATA")


include(":android", ":shared")
rootProject.name = "TMDbMultiplatform"
