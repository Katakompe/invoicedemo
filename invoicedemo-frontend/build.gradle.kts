import com.github.gradle.node.npm.task.NpmTask

plugins {
  java
  id("com.github.node-gradle.node") version "7.0.2"
}
buildscript {
  dependencies {
    classpath("com.github.node-gradle:gradle-node-plugin:7.0.2")
  }
}

/**
 * Npm Tasks for the Frontend Build
 */

tasks.register<NpmTask>("ngOpenapiGenerate") {
  dependsOn("npmInstall")
  args = listOf("run", "openapi")
}

tasks.register<NpmTask>("ngBuild") {
  dependsOn("ngOpenapiGenerate")
  args = listOf("run", "build")
  inputs.files("package.json", "package-lock.json", "angular.json", "tsconfig.json", "tsconfig.app.json")
  inputs.dir("src")
  inputs.dir(fileTree("node_modules").exclude(".cache"))
  outputs.dir("dist")
}

val ngTest = tasks.register<NpmTask>("ngTest") {
  dependsOn("ngOpenapiGenerate")
  args = listOf("run", "test")
}


/**
 * Configuration to create a WebJar from the Angular frontend which can be consumed as a dependency from the invoicedemo-server
 */
val webjars by configurations.creating {
  isCanBeConsumed = true
  isCanBeResolved = false
}

val webjar = tasks.named<Jar>("jar") {
  dependsOn("ngBuild")
  from("$projectDir/dist")
  into("META-INF/resources")
}

artifacts {
  add("webjars", webjar)
}


/**
 * Build depends on frontend build
 */
tasks.named("build") {
  dependsOn(webjar)

  //TODO: ngTest does not stop running after all tests pass, and blocks the build.
  // But only on Linux Mint, not on Windows.
  //dependsOn(ngTest)
}

/**
 * Extend clean task to delete all build folders
 */
tasks.named("clean") {
  doLast {
    delete("$projectDir/src/app/generated")
    delete("$projectDir/dist")
    delete("${project.layout.buildDirectory}")
  }
}

