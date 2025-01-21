package com.example.data.network

import com.example.data.network.api.ApiServiceImpl
import com.example.data.network.dto.IncidentDto
import com.example.domain.entity.UNDEFINED_ZONE_ID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    routing {
        baseRoute()
        zonesRoute()
        incidentsRoute()
    }
}

fun Route.baseRoute() {
    get("/") {
        call.respondText("Hello, world!")
    }
}


fun Route.zonesRoute() {
    route("/zones") {
        get {
            ApiServiceImpl.getZonesList().let {
                call.respond(HttpStatusCode.OK, it)
            }
        }

        get("/{zoneId}") {
            val zoneId = call.parameters["zoneId"]?.toLongOrNull()
            if (zoneId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid zoneId")
                return@get
            }
            ApiServiceImpl.getZoneById(zoneId).let { call.respond(it) }
        }

    }
}

fun Route.incidentsRoute() {
    route("/incidents") {
        get {
            ApiServiceImpl.getIncidentsList().let { call.respond(HttpStatusCode.OK, it) }
        }
        get("/{zoneId}") {
            val zoneId = call.parameters["zoneId"]?.toLongOrNull()
            if (zoneId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid zoneId")
                return@get
            }
            ApiServiceImpl.getIncidentsByZone(zoneId).let { call.respond(it) }
        }

        post("/update") {
            val newIncident = call.receive<IncidentDto>()
            val zoneId = ApiServiceImpl.calculateZone(newIncident)
            if (zoneId == UNDEFINED_ZONE_ID) {
                call.respond(HttpStatusCode.BadRequest, "Undefined Zone")
            } else {
                ApiServiceImpl.updateIncident(newIncident.copy(zoneId = zoneId))
                call.respond(HttpStatusCode.OK, "Incident updated successfully")
            }

        }
        post("/create") {
            val newIncident = call.receive<IncidentDto>()
            val zoneId = ApiServiceImpl.calculateZone(newIncident)
            if (zoneId == UNDEFINED_ZONE_ID) {
                call.respond(HttpStatusCode.BadRequest, "Undefined Zone")
            } else {
                ApiServiceImpl.createIncident(newIncident.copy(zoneId = zoneId))
                call.respond(HttpStatusCode.Created, "Incident created successfully")
            }
        }
    }

}