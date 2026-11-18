package domain

import com.example.domain.model.MilkRequest
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
            call.respond(HttpStatusCode.OK, controller.getAllMilk())
        }

        get("/milk/{id}") {
            val id = call.parameters["id"]?.toLong()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Некорректный id молока")

            val controller = WarehouseController()
            val milk = controller.getMilkById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, "Молоко не найдено")

            call.respond(HttpStatusCode.OK, milk)
        }

        post("/milk") {
            val request = runCatching { call.receive<MilkRequest>() }.getOrNull()
                ?: return@post call.respond(HttpStatusCode.BadRequest, "Некорректное тело запроса")

            val controller = WarehouseController()
            call.respond(HttpStatusCode.Created, controller.createMilk(request))
        }

        put("/milk/{id}") {
            val id = call.parameters["id"]?.toLong()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Некорректный id молока")

            val request = runCatching { call.receive<MilkRequest>() }.getOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Некорректное тело запроса")

            val controller = WarehouseController()
            val updated = controller.updateMilk(id, request)
                ?: return@put call.respond(HttpStatusCode.NotFound, "Молоко не найдено")

            call.respond(HttpStatusCode.OK, updated)
        }

        delete("/milk/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Некорректный id молока")

            val controller = WarehouseController()
            if (controller.deleteMilk(id)) {
                call.respond(HttpStatusCode.OK, "Молоко удалено")
            } else {
                call.respond(HttpStatusCode.NotFound, "Молоко не найдено")
            }
        }
    }
}