plugins {
  kotlin("jvm") version "1.9.25" apply true
  kotlin("plugin.spring") version "1.9.25" apply false
  id("org.springframework.boot") version "3.4.2" apply false
  id("io.spring.dependency-management") version "1.1.7" apply false
  id("org.hibernate.orm") version "6.6.7.Final" apply false
  id("org.graalvm.buildtools.native") version "0.10.5" apply false
  id("com.diffplug.spotless") version "7.0.2" apply false
  kotlin("plugin.jpa") version "1.9.25" apply false
}

repositories {
  mavenCentral()
}

subprojects {
  apply(plugin = "kotlin")
  apply(plugin = "java")

  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(21))
    }
  }

  kotlin {
    jvmToolchain(21)

    compilerOptions {
      freeCompilerArgs.addAll("-Xjsr305=strict")
    }
  }
}
