plugins {
    val kotlinVersion = "1.6.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.12.0"
}

group = "pers.shennoter"
version = "1.6.0"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
    google()
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.0")
}