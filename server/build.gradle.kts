plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
}

group = "com.moddy.panaderiapos"
version = "1.0.0"
application {
    mainClass = "com.moddy.panaderiapos.ApplicationKt"
}

dependencies {
    api(project(":core"))
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2)
    implementation(libs.ktor.serialization.kotlinx)
    implementation(libs.ktor.server.content.negociation)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}