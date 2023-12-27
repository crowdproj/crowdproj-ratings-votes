package com.crowdproj.vote.app.spring

import com.crowdproj.vote.app.spring.config.SqlPropertiesEx
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
// swagger URL: http://localhost:8080/swagger-ui.html

@SpringBootApplication
@EnableConfigurationProperties(SqlPropertiesEx::class)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
