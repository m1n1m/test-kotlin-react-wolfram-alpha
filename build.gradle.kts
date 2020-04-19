import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
    id("com.moowork.node") version "1.3.1"
}

node {
    download = false
    version = "10.9.0"
    npmVersion = "6.9.0"
    nodeModulesDir = file("${project.projectDir}/webapp")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation( "com.squareup.retrofit2:retrofit:2.7.2")
    implementation( "com.squareup.retrofit2:converter-gson:2.7.2")

    testImplementation( "junit:junit:4.13")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

task<com.moowork.gradle.node.yarn.YarnTask>("yarnInstallAll") {
    args = listOf("install")
}

task<com.moowork.gradle.node.yarn.YarnTask>("buildFront") {

    dependsOn("yarnInstallAll")

    args = listOf("build")

    val dest = "build/resources/main/static";

    doLast {
        delete {
            delete(fileTree(dest).include("**/*"))
        }
        copy {
            from("webapp/build")
            into(dest)
        }
    }
}

tasks {
    "assemble" {
        dependsOn("buildFront")
    }

    "build" {
        dependsOn("buildFront")
    }

    "bootJar" {
        dependsOn("buildFront")
    }
}