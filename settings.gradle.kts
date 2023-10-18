rootProject.name = "crowdproj-vote"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val ktorPluginVersion: String by settings
        val codeGeneratorVersion: String by settings
        val bmuschkoVersion: String by settings
        val autoversionVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorPluginVersion apply false

        id("com.crowdproj.generator") version codeGeneratorVersion apply false

//        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
        id("com.crowdproj.plugin.autoversion") version autoversionVersion apply false
    }
}

include("crowdproj-vote-api-v1")
include("crowdproj-vote-common")
include("crowdproj-vote-api-v1-mappers")
