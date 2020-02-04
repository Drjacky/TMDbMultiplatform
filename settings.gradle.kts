val kotlinVersion: String by extra

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "kotlinx-serialization" -> useModule("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
                "kotlin-multiplatform" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
                //"kotlin-platform-js" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
            }
        }
    }
}

enableFeaturePreview("GRADLE_METADATA")


include(":android", ":shared")
rootProject.name = "FlickrMultiplatform"
