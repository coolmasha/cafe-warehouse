package com.example

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.Serializable

@Serializable
data class Test (
    val text: String
)

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}