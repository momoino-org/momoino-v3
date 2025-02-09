plugins {
  id("buildlogic.kotlin-application-conventions")

  alias(libs.plugins.kotlin.plugin.spring)
  alias(libs.plugins.org.springframework.boot)
  alias(libs.plugins.io.spring.dependencyManagement)
  alias(libs.plugins.org.hibernate.orm)
  alias(libs.plugins.org.graalvm.buildtools.native)
  alias(libs.plugins.kotlin.plugin.jpa)
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

dependencies {
  implementation(project(":common"))
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
  implementation("org.liquibase:liquibase-core:4.30.0")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
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
