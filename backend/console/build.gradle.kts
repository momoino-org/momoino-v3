plugins {
  kotlin("jvm")
  kotlin("plugin.spring")
  id("org.springframework.boot")
  id("io.spring.dependency-management")
  id("org.hibernate.orm")
  id("org.graalvm.buildtools.native")
  id("com.diffplug.spotless")
  kotlin("plugin.jpa")

  application
}

group = "com.momoino.console"
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
  implementation(project(":common"))
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
  implementation("org.liquibase:liquibase-core:4.30.0")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
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
