package domain

import com.example.domain.model.MilkListResponse
import com.example.domain.model.MilkRequest
import com.example.domain.model.MilkResponse

class WarehouseController {

    fun getAllMilk(): List<MilkResponse> {
        val milkList = WarehouseRepository.getAllMilk()
        return milkList.map {
            MilkResponse(id = it.id, name = it.name, surcharge = it.surcharge.toDouble())
        }
    }

    fun getMilkById(id: Long): MilkResponse? {
        val milk = WarehouseRepository.getMilkById(id) ?: return null
        return MilkResponse(milk.id, milk.name, milk.surcharge.toDouble())
    }

    fun createMilk(request: MilkRequest): MilkResponse {
        val milk = WarehouseRepository.createMilk(
            request.name,
            request.surcharge.toBigDecimal(),
            request.quantity
        )
        return MilkResponse(milk.id, milk.name, milk.surcharge.toDouble())
    }

    fun updateMilk(id: Long, request: MilkRequest): MilkResponse? {
        val milk = WarehouseRepository.updateMilk(
            id,
            request.name,
            request.surcharge.toBigDecimal(),
            request.quantity
        ) ?: return null

        return MilkResponse(milk.id, milk.name, milk.surcharge.toDouble())
    }

    fun deleteMilk(id: Long): Boolean {
        return WarehouseRepository.deleteMilk(id)
    }

}