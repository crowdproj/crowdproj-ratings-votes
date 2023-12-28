rootProject.name = "crowdproj-vote"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val codeGeneratorVersion: String by settings
        val bmuschkoVersion: String by settings
        val autoversionVersion: String by settings
        val springframeworkBootVersion: String by settings
        val springDependencyManagementVersion: String by settings
        val pluginSpringVersion: String by settings
        val pluginJpa: String by settings
        val kotestVersion: String by settings
        val pluginShadow: String by settings
        val ktorPluginVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false

        id("com.crowdproj.generator") version codeGeneratorVersion apply false

        id("com.crowdproj.plugin.autoversion") version autoversionVersion apply false
        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false

        id("io.kotest.multiplatform") version kotestVersion apply false
        id("com.github.johnrengelman.shadow") version pluginShadow apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false

        id("io.ktor.plugin") version ktorPluginVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false
    }
}

include("crowdproj-vote-api-v1")
include("crowdproj-vote-common")
include("crowdproj-vote-api-v1-mappers")
include("crowdproj-vote-app-spring")
include("crowdproj-vote-kafka")
include("crowdproj-vote-biz")
include("stubs")
include("crowdproj-votes-repo-in-memory")
include("crowdproj-vote-repo-tests")
include("crowdproj-vote-repo-postgresql")
include("lib-logging-module")
include("api-log")
include("mappers-log")
include("lib-logging-logback")
include("crowdproj-vote-repo-stubs")
