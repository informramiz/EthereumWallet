plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://artifacts.consensys.net/public/maven/maven/")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation ("org.web3j:core:4.11.2")
    implementation("org.slf4j:slf4j-nop:2.0.13")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}