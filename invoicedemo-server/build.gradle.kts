plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "7.7.0"
    id("org.jooq.jooq-codegen-gradle") version "3.19.10"
}

group = "me.komposch"
version = "0.0.1-SNAPSHOT"


buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jooq:jooq-codegen:3.19.10")
        classpath("org.jooq:jooq-meta-extensions:3.19.10")

    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

sourceSets {
    main {
        java {
            srcDir("${project.layout.buildDirectory.get()}/generated/openapi/src/main/java")
            srcDir("${project.layout.buildDirectory.get()}/generated/jooq/src/main/java")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("jakarta.validation:jakarta.validation-api:3.1.0")
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

/**
 * Task to generate Spring REST Controllers and the API model classes from an OpenAPI specification.
 */
openApiGenerate {
    generatorName = "spring"
    library = "spring-boot"
    generateApiDocumentation = false
    generateModelDocumentation = false
    generateApiTests = false
    generateModelTests = false
    inputSpec = "${rootDir}/openapi/InvoiceApi.yaml"
    outputDir = "${project.layout.buildDirectory.get()}/generated/openapi"
    apiPackage = "me.komposch.invoicedemo.server.endpoint"
    modelPackage = "me.komposch.invoicedemo.server.endpoint.model"
    invokerPackage = "me.komposch.invoicedemo.server.endpoint.util"
    globalProperties =
        mapOf(
            "models" to "",
            "apis" to "",
            "supportingFiles" to "RFC3339DateFormat.java,ApiUtil.java"
        )
    configOptions = mapOf(
        "useSpringBoot3" to "true",
        "delegatePattern" to "true",
        "skipDefaultInterface" to "false",
        "useJakartaEe" to "true",
        "useTags" to "true",
        "useSwaggerUI" to "false",
        "annotationLibrary" to "none",
        "documentationProvider" to "none",
        "openApiNullable" to "false"
    )
}

/**
 * Task to generate data classes for database access
 */

jooq {
    configuration {
        generator {
            database {
                name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                properties {
                    property {
                        key = "scripts"
                        value = "$projectDir/src/main/resources/schema.sql"
                    }
                }
            }
            target {
                packageName = "me.komposch.invoicedemo.server.persistence.model"
                directory = "${project.layout.buildDirectory.get()}/generated/jooq/src/main/java"
            }
        }
    }
}

/**
 * Only compile after API classes are generated
 */
tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
    dependsOn("openApiGenerate")
    dependsOn("jooqCodegen")
}

