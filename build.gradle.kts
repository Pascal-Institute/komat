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
            groupId = "com.pascal.institute" //Navigate beyond computing oceans.
            artifactId = "komat"

            version = "1.8.7"

            from(components["java"])
        }
    }
}