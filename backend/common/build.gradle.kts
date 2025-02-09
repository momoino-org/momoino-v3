plugins {
  `java-library`

  kotlin("jvm")
  kotlin("plugin.spring")
  id("org.springframework.boot")
  id("io.spring.dependency-management")
  id("org.hibernate.orm")
  id("com.diffplug.spotless")
  kotlin("plugin.jpa")
}

group = "com.momoino.common"
version = "0.0.1.pre-alpha"

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  api("org.springframework.boot:spring-boot-starter-data-jpa")
  api("org.springframework.boot:spring-boot-starter-security")
  api("org.springframework.boot:spring-boot-starter-web")
  api("org.springframework.boot:spring-boot-starter-validation")
  api("com.fasterxml.jackson.module:jackson-module-kotlin")
  api("org.jetbrains.kotlin:kotlin-reflect")
  api("io.hypersistence:hypersistence-utils-hibernate-63:3.9.0")
  implementation("com.networknt:json-schema-validator:1.5.5")
  runtimeOnly("org.postgresql:postgresql")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.springframework.security:spring-security-test")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

hibernate {
  enhancement {
    enableAssociationManagement = true
  }
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

spotless {
  kotlin {
    ktlint("1.5.0")
  }
  kotlinGradle {
    ktlint("1.5.0")
  }
}
