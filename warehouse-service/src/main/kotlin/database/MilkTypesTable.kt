package database

import org.jetbrains.exposed.v1.core.dao.id.LongIdTable

object MilkTypesTable : LongIdTable("milk_types") {
    val name = varchar("name", 50)
    val surcharge = decimal("surcharge", 10, 2)
    val quantity = integer("quantity")
}