plugins {
    kotlin("jvm") version "1.9.20"
    `maven-publish`
}

repositories {
    mavenCentral()
    maven{
        url = uri("https://jitpack.io")
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.snacklab"
            artifactId = "komat"

            version = "1.0.0"

            from(components["java"])
        }
    }
}