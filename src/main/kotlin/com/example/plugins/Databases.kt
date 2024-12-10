package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases(config: ApplicationConfig) {
    val url = config.property("postgres.url").getString()
    val driver = config.property("postgres.driver").getString()
    val user = config.property("postgres.user").getString()
    val password = config.property("postgres.password").getString()

    Database.connect(
        url = url,
        driver = driver,
        user = user,
        password = password
    )
}
