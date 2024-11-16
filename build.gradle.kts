plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")

    testImplementation("io.rest-assured:rest-assured:4.4.0")

    testImplementation("io.rest-assured:json-path:4.4.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    testImplementation("org.junit.platform:junit-platform-suite:1.8.2")

    testImplementation("io.rest-assured:json-schema-validator:4.4.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    implementation("com.google.code.gson:gson:2.8.9")
}

tasks.test {
    useJUnitPlatform()
}