package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDetails(
    val status: Int,
    val message: String
)

@Serializable
data class SuccessDetails(
    val status: Int,
    val message: String
)
