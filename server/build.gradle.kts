plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
}

group = "com.example.panaderiapos"
version = "1.0.0"
application {
    mainClass = "com.example.panaderiapos.ApplicationKt"
}

dependencies {
    api(project(":core"))
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(libs.kotlinx.serialization)
    implementation(libs.ktor.serialization.kotlinx)
    implementation(libs.ktor.server.content.negociation)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}