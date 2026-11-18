package database.model

import java.math.BigDecimal

data class MilkDTO(
    val id: Long,
    val name: String,
    val surcharge: BigDecimal,
    val quantity: Int
)