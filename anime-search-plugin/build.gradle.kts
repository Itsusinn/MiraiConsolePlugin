plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    kotlin("kapt") version "1.4.0"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "org.meowcat"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
}

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
}

dependencies {
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    compileOnly(kotlin("stdlib-jdk8"))

    val core = "1.2.3"
    val console = "1.0-M4"

    compileOnly("net.mamoe:mirai-console:$console")
    compileOnly("net.mamoe:mirai-core:$core")

    val autoService = "1.0-rc7"
    kapt("com.google.auto.service", "auto-service", autoService)
    compileOnly("com.google.auto.service", "auto-service-annotations", autoService)

    testImplementation("net.mamoe:mirai-console:$console")
    testImplementation("net.mamoe:mirai-core:$core")
    testImplementation("net.mamoe:mirai-console-pure:$console")
    testImplementation(kotlin("stdlib-jdk8"))

}

kotlin.target.compilations.all {
    kotlinOptions.freeCompilerArgs += "-Xjvm-default=enable"
    kotlinOptions.jvmTarget = "1.8"
}