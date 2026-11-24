package api

import com.example.domain.model.ErrorDetails
import com.example.domain.model.MilkRequest
import com.example.domain.model.SuccessDetails
import domain.WarehouseController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing

fun Application.warehouseRouting() {
    routing {
        get("/health") {
            call.respond("Warehouse Service")
        }
        get("/milk") {
            val controller = WarehouseController()
            val milkList = controller.getAllMilk()
            call.respond(HttpStatusCode.Companion.OK, milkList)
        }

        get("/milk/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@get call.respond(
                    HttpStatusCode.Companion.BadRequest,
                    ErrorDetails(
                        status = HttpStatusCode.Companion.BadRequest.value,
                        message = "Некорректный id молока"
                    )
                )

            val controller = WarehouseController()
            val milk = controller.getMilkById(id)
                ?: return@get call.respond(
                    HttpStatusCode.Companion.NotFound,
                    ErrorDetails(
                        status = HttpStatusCode.Companion.NotFound.value,
                        message = "Молоко не найдено"
                    )
                )

            call.respond(HttpStatusCode.Companion.OK, milk)
        }

        post("/milk") {
            val request = runCatching { call.receive<MilkRequest>() }.getOrNull()
                ?: return@post call.respond(
                    HttpStatusCode.Companion.BadRequest, ErrorDetails(
                        status = HttpStatusCode.Companion.BadRequest.value,
                        message = "Некорректное тело запроса"
                    )
                )

            val controller = WarehouseController()
            call.respond(HttpStatusCode.Companion.Created, controller.createMilk(request))
        }

        put("/milk/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@put call.respond(
                    HttpStatusCode.Companion.BadRequest, ErrorDetails(
                        status = HttpStatusCode.Companion.BadRequest.value,
                        message = "Некорректный id молока"
                    )
                )

            val request = runCatching { call.receive<MilkRequest>() }.getOrNull()
                ?: return@put call.respond(
                    HttpStatusCode.Companion.BadRequest, ErrorDetails(
                        status = HttpStatusCode.Companion.BadRequest.value,
                        message = "Некорректное тело запроса"
                    )
                )

            val controller = WarehouseController()
            val updated = controller.updateMilk(id, request)
                ?: return@put call.respond(
                    HttpStatusCode.Companion.NotFound, ErrorDetails(
                        status = HttpStatusCode.Companion.NotFound.value,
                        message = "Молоко не найдено"
                    )
                )

            call.respond(HttpStatusCode.Companion.OK, updated)
        }

        delete("/milk/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@delete call.respond(
                    HttpStatusCode.Companion.BadRequest, ErrorDetails(
                        status = HttpStatusCode.Companion.BadRequest.value,
                        message = "Некорректный id молока"
                    )
                )

            val controller = WarehouseController()
            if (controller.deleteMilk(id)) {
                call.respond(
                    HttpStatusCode.Companion.OK, SuccessDetails(
                        status = HttpStatusCode.Companion.OK.value,
                        message = "Молоко удалено"
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.Companion.NotFound, ErrorDetails(
                        status = HttpStatusCode.Companion.NotFound.value,
                        message = "Молоко не найдено"
                    )
                )
            }
        }
    }
}