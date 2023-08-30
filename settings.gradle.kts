rootProject.name = "crowdproj"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val generatorVersion: String by settings
        val nexusStagingVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("com.crowdproj.generator") version generatorVersion apply false
        id("io.codearte.nexus-staging") version nexusStagingVersion
    }
}
val runTests: String by settings
val shouldTest = runTests.toBoolean()

