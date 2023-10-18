plugins {
    kotlin("multiplatform")
}

version = rootProject.version

kotlin {
    jvm { }
    linuxX64 { }

    sourceSets {
        val commonMain by getting {

            kotlin.srcDirs("${layout.buildDirectory.get()}/generate-resources/main/src/commonMain/kotlin")
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":crowdproj-vote-api-v1"))
                implementation(project(":crowdproj-vote-common"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
