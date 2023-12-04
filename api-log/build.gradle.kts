plugins {
    kotlin("multiplatform")
    id("com.crowdproj.generator")
    kotlin("plugin.serialization")
}

val specDir = "${layout.buildDirectory.get()}/specs"

kotlin {
    jvm { }
    linuxX64 { }

    sourceSets {
        val coroutinesVersion: String by project
        val serializationVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            kotlin.srcDirs("$buildDir/generate-resources/main/src/commonMain/kotlin")
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

crowdprojGenerate {
    packageName.set("${project.group}.api.v1")
    inputSpec.set("$specDir/spec-crowdproj-votes-log.yaml")
}

tasks {
    val getSpecs: Task by creating(Copy::class) {
        group = "openapi tools"
        from("${rootProject.projectDir}/specs")
        into(specDir)
    }
    this.openApiGenerate {
        dependsOn(getSpecs)
        mustRunAfter("compileCommonMainKotlinMetadata")
    }
    filter { it.name.startsWith("compile") }.forEach {
        it.dependsOn(openApiGenerate)
    }
}

afterEvaluate {
    val openApiGenerate = tasks.getByName("openApiGenerate")
    tasks.filter { it.name.startsWith("compile") }.forEach {
        it.dependsOn(openApiGenerate)
    }
}
