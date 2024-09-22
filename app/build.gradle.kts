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

    // Add Scalastyle plugin for better code style.
    id("com.github.alisiikh.scalastyle") version "3.5.0"

    // Add Scalafix plugin for code refactoring.
    id("io.github.cosmicsilence.scalafix") version "0.2.2"

    // Add sonarqube plugin for CI pipeline
    id("org.sonarqube") version "5.1.0.4882"
    // id("jacoco")
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
    mainClass = "org.example.App"
}

scoverage {
    minimumRate.set(BigDecimal("0.80"))
}

scalafmt {
    configFilePath = "configs/.scalafmt.conf"
}

wartremover {
    configFile("configs/.wartremover.conf")
}

scalastyle {
    config = file("configs/scalastyle_config.xml")
}

scalafix {
    configFile = file("configs/.scalafix.conf")
}

//Needed for scalfix to work
tasks.withType<ScalaCompile>().configureEach {
    scalaCompileOptions.apply {
        additionalParameters = listOf("-Wunused:all")
    }
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
    property("sonar.scala.coverage.reportPaths", "${layout.buildDirectory}/reports/scoverage/scoverage.xml")
}

// tasks.test {
//     finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
// }
// tasks.jacocoTestReport {
//     dependsOn(tasks.test) // tests are required to run before generating the report
// }