package domain

import database.MilkTypesTable
import database.model.MilkDTO
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import java.math.BigDecimal

object WarehouseRepository {


    fun getAllMilk(): List<MilkDTO> = transaction {
        MilkTypesTable.selectAll().map { row ->
            MilkDTO(
                id = row[MilkTypesTable.id].value,
                name = row[MilkTypesTable.name],
                surcharge = row[MilkTypesTable.surcharge],
                quantity = row[MilkTypesTable.quantity]
            )
        }
    }

    fun getMilkById(id: Long): MilkDTO? = transaction {
        MilkTypesTable
            .selectAll()
            .where { MilkTypesTable.id.eq(id) }
            .singleOrNull()
            ?.let { row ->
                MilkDTO(
                    id = row[MilkTypesTable.id].value,
                    name = row[MilkTypesTable.name],
                    surcharge = row[MilkTypesTable.surcharge],
                    quantity = row[MilkTypesTable.quantity]
                )
            }
    }

    fun createMilk(name: String, surcharge: BigDecimal, quantity: Int): MilkDTO = transaction {
        val id = MilkTypesTable.insert {
            it[MilkTypesTable.name] = name
            it[MilkTypesTable.surcharge] = surcharge
            it[MilkTypesTable.quantity] = quantity
        } get MilkTypesTable.id
        MilkDTO(id.value, name, surcharge, quantity)
    }

    fun updateMilk(id: Long, name: String, surcharge: BigDecimal, quantity: Int): MilkDTO? = transaction {
        val updatedRows = MilkTypesTable.update({ MilkTypesTable.id eq id }) {
            it[MilkTypesTable.name] = name
            it[MilkTypesTable.surcharge] = surcharge
            it[MilkTypesTable.quantity] = quantity
        }

        if (updatedRows > 0) {
            MilkTypesTable
                .selectAll()
                .where { MilkTypesTable.id.eq(id) }
                .map { row ->
                    MilkDTO(
                        id = row[MilkTypesTable.id].value,
                        name = row[MilkTypesTable.name],
                        surcharge = row[MilkTypesTable.surcharge],
                        quantity = row[MilkTypesTable.quantity]
                    )
                }
                .singleOrNull()
        } else {
            null
        }
    }

    fun deleteMilk(id: Long): Boolean = transaction {
        MilkTypesTable.deleteWhere { MilkTypesTable.id eq id } > 0
    }

}