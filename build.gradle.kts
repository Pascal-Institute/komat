plugins {
    kotlin("jvm") version "2.0.0-RC1"
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
            groupId = "com.volta2030"
            artifactId = "komat"

            version = "1.2.0"

            from(components["java"])
        }
    }
}