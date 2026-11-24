package com.example

import api.warehouseRouting
import io.ktor.server.application.Application

fun Application.rootModule() {
    configureSerialization()
    configureRouting()
    warehouseRouting()
}
