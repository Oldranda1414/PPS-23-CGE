/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Scala application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.10.1/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the scala Plugin to add support for Scala.
    scala

    // Apply the application plugin to add support for building a CLI application in Java.
    application

    // Add Scoverage plugin for code coverage.
    id("org.scoverage") version "8.1"

    // Add Scalafmt plugin for code formatting.
    id("cz.augi.gradle.scalafmt") version "1.21.3"

    // Add Wartremover plugin for static analysis.
    // id("cz.augi.gradle.wartremover") version "0.17.1"

    // Add sonarqube plugin for CI pipeline
    id("org.sonarqube") version "3.5.0.2730"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use Scala 2.13 in our library project
    implementation(libs.scala.library)

    // This dependency is used by the application.
    implementation(libs.guava)

    // Use Scalatest for testing our library
    testImplementation(libs.junit)
    testImplementation(libs.scalatest.v2.v13)
    testImplementation(libs.junit.v4.v13.v2.v13)

    // Need scala-xml at test runtime
    testRuntimeOnly(libs.scala.xml.v2.v13)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass = "org.example.App"
}

// Configure Scoverage settings
scoverage {
    minimumRate.set(BigDecimal("0.80"))
     excludedFiles.set(
        listOf(
            ".*/resources/.*",
            ".*/build/*"
        )
    )
}

scalafmt {
    configFilePath = ".scalafmt.conf"
}

val organization = "CGE-PPS-LR"
val githubUrl = "https://github.com/Oldranda1414/${rootProject.name}"

sonarqube.properties {
    property("sonar.organization", organization)
    property("sonar.host.url", "https://sonarcloud.io")
    property("sonar.projectName", rootProject.name)
    property("sonar.projectKey", "${organization}_${rootProject.name}")
    property("sonar.projectDescription", "Project for PPS.")
    property("sonar.projectVersion", project.version.toString())
    System.getenv()["SONARCLOUD_TOKEN"]?.let { property("sonar.login", it) }
    property("sonar.scm.provider", "git")
    property("sonar.verbose", "true")
    property("sonar.links.homepage", githubUrl)
    property("sonar.links.ci", "$githubUrl/actions")
    property("sonar.links.scm", githubUrl)
    property("sonar.links.issue", "$githubUrl/issues")
    property("sonar.scala.coverage.reportPaths", "${project.buildDir}/reports/scoverage/scoverage.xml")
}