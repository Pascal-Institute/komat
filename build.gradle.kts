plugins {
    kotlin("jvm") version "2.0.0"
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
            groupId = "Pascal-Institute" //Navigate beyond computing oceans.
            artifactId = "komat"

            version = "1.8.0"

            from(components["java"])
        }
    }
}