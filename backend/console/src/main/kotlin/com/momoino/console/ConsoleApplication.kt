package com.momoino.console

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.momoino"])
@EnableJpaRepositories(basePackages = ["com.momoino"])
@EntityScan(basePackages = ["com.momoino"])
class ConsoleApplication

fun main(args: Array<String>) {
  runApplication<ConsoleApplication>(*args)
}
