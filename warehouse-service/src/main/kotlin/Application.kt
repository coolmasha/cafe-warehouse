package com.example

import domain.warehouseRouting
import io.ktor.server.application.Application

fun Application.rootModule() {
    configureSerialization()
    configureRouting()
    warehouseRouting()
}
