plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.20"
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)
}
dependencies {
    implementation(ktorLibs.server.callLogging)
    implementation(ktorLibs.server.cio)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(libs.logback.classic)

    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)

    implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.0")

    implementation("org.jetbrains.exposed:exposed-core:1.2.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.2.0")
    implementation("org.jetbrains.exposed:exposed-dao:1.2.0")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:1.2.0")
    implementation("org.postgresql:postgresql:42.7.4")
}

tasks.shadowJar {
    mergeServiceFiles()
    archiveBaseName.set(project.name)
    archiveClassifier.set("all")
    archiveVersion.set("")
    manifest {
        attributes["Main-Class"] = "com.example.MainKt"
    }
}

tasks.matching {
    it.name in listOf(
        "startScripts",
        "startShadowScripts",
        "distZip",
        "distTar",
        "shadowDistZip",
        "shadowDistTar"
    )
}.configureEach {
    enabled = false
}