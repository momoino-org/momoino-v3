plugins {
  id("buildlogic.kotlin-library-conventions")

  alias(libs.plugins.kotlin.plugin.spring)
  alias(libs.plugins.org.springframework.boot)
  alias(libs.plugins.io.spring.dependencyManagement)
  alias(libs.plugins.org.hibernate.orm)
  alias(libs.plugins.kotlin.plugin.jpa)
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

dependencies {
  api("org.springframework.boot:spring-boot-starter-data-jpa")
  api("org.springframework.boot:spring-boot-starter-security")
  api("org.springframework.boot:spring-boot-starter-web")
  api("org.springframework.boot:spring-boot-starter-validation")
  api("com.fasterxml.jackson.module:jackson-module-kotlin")
  api("org.jetbrains.kotlin:kotlin-reflect")
  api("io.hypersistence:hypersistence-utils-hibernate-63:3.9.2")
  implementation("com.networknt:json-schema-validator:1.5.5")
  runtimeOnly("org.postgresql:postgresql")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
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
