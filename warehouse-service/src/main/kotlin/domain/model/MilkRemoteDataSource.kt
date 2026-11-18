package com.example.domain.model


import kotlinx.serialization.Serializable


@Serializable
data class MilkRequest(
    val name: String,
    val surcharge: Double,
    val quantity: Int
)
@Serializable
data class MilkResponse(
    val id: Long,
    val name: String,
    val surcharge: Double
)

@Serializable
data class MilkListResponse(
    val milkList: List<MilkResponse>
)
