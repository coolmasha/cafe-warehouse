package com.example

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import org.jetbrains.exposed.v1.jdbc.Database

fun main(args: Array<String>) {
    val dbUrl = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/warehouse"
    val dbUser = System.getenv("DB_USER") ?: "postgres"
    val dbPassword = System.getenv("DB_PASSWORD") ?: "123456"

    Database.connect(
        url = dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )


    embeddedServer(
        factory = io.ktor.server.cio.CIO,
        port = System.getenv("PORT")?.toIntOrNull() ?: 8080,
        host = "0.0.0.0",
        module = Application::rootModule
    ).start(wait = true)
}
