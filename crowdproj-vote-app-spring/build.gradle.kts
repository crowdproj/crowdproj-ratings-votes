plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    val kotestVersion: String by project
    val springdocOpenapiUiVersion: String by project
    val coroutinesVersion: String by project
    val serializationVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-actuator") // info; refresh; springMvc output
    implementation("org.springframework.boot:spring-boot-starter-webflux") // Controller, Service, etc..
    implementation("org.springframework.boot:spring-boot-starter-websocket") // Controller, Service, etc..
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:$springdocOpenapiUiVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // from models to json and Vice versa
    implementation("org.jetbrains.kotlin:kotlin-reflect") // for spring-boot app
    implementation("org.jetbrains.kotlin:kotlin-stdlib") // for spring-boot app
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    implementation(project(":crowdproj-vote-common"))
    implementation(project(":crowdproj-vote-api-v1-mappers"))
    implementation(project(":crowdproj-vote-api-v1"))

    // stubs
    implementation(project(":stubs"))

    // biz
    implementation(project(":crowdproj-vote-biz"))

    // logging
    implementation(project(":lib-logging-module"))
    implementation(project(":lib-logging-logback"))
    implementation(project(":mappers-log"))
    implementation(project(":api-log"))

    // repository
    implementation(project(":crowdproj-votes-repo-in-memory"))
    implementation(project(":crowdproj-vote-repo-postgresql"))
    implementation(project(":crowdproj-vote-repo-tests"))

    implementation(project(":crowdproj-vote-repo-stubs"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
    implementation(kotlin("stdlib"))

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux") // Controller, Service, etc..
    testImplementation("com.ninja-squad:springmockk:3.0.1") // mockking beans
}

tasks {
    @Suppress("UnstableApiUsage")
    withType<ProcessResources> {
        from("$rootDir/specs") {
            into("/static")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
