import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    application
    kotlin("plugin.serialization") version "1.6.20"
    id("org.jetbrains.dokka") version "1.6.10"
}

group = "me.jmsa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.1")
    implementation("org.hexworks.zircon:zircon.core-jvm:2021.1.0-RELEASE")
    implementation("org.hexworks.zircon:zircon.jvm.swing:2021.1.0-RELEASE")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("com.charleskorn.kaml:kaml:0.43.0") // Get the latest version number from https://github.com/charleskorn/kaml/releases/latest
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.2.0") //for JVM platform
    implementation("com.floern.castingcsv:casting-csv-kt:1.2")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("org.xerial:sqlite-jdbc:3.35.0.1")
    implementation("org.jetbrains.exposed", "exposed-core", "0.38.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.38.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.38.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "15"
}

tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
}

application {
    mainClass.set("MainKt")
}