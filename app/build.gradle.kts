plugins {
    // Apply the scala Plugin to add support for Scala.
    scala

    // Apply the application plugin to add support for building a CLI application in Java.
    application

    // Add Scoverage plugin for code coverage.
    id("org.scoverage") version "8.1"

    // Add Scalafmt plugin for code formatting.
    id("cz.augi.gradle.scalafmt") version "1.21.3"

    // Add Wartremover plugin to avoid code smells.
    id("io.github.jahrim.wartremover") version "0.1.3"

    // Add Scalafix plugin for code refactoring.
    id("io.github.cosmicsilence.scalafix") version "0.2.2"

    // Add sonarqube plugin for CI pipeline
    id("org.sonarqube") version "5.1.0.4882"

    // Add plugin for shadow jar
    id("com.github.johnrengelman.shadow") version "8.1.1"

}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation(libs.scala)

    // Dependencies used by the application.
    implementation(libs.guava)

    // Use Scalatest for testing our library
    testImplementation(libs.scalatest)
    testImplementation(libs.scalatestplusjunit)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "org.cge.Main"
}

scoverage {
    minimumRate.set(BigDecimal("0.80"))
    excludedPackages = listOf(
        "org.cge.Main",
        "org.cge.engine.view.*"
    )
}

scalafmt {
    configFilePath = "configs/.scalafmt.conf"
}

wartremover {
    configFile("configs/.wartremover.conf")
}

scalafix {
    configFile = file("configs/.scalafix.conf")
}

//Needed for scalfix to work
tasks.withType<ScalaCompile>().configureEach {
    scalaCompileOptions.apply {
        additionalParameters = listOf("-Wunused:all", "-feature")
    }
}