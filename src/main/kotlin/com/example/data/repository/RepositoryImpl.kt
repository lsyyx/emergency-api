package com.example.data.repository

import com.example.data.db.IncidentsTable
import com.example.data.db.ZonesTable
import com.example.data.mapper.toIncidentsList
import com.example.data.mapper.toZone
import com.example.data.mapper.toZonesList
import com.example.data.network.dto.IncidentDto
import com.example.domain.entity.Incident
import com.example.domain.entity.UNDEFINED_ZONE_ID
import com.example.domain.entity.Zone
import com.example.domain.repository.Repository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class RepositoryImpl : Repository {

    override fun getZonesList(): List<Zone> = transaction {
        ZonesTable
            .selectAll()
            .toList()
            .toZonesList()
    }

    override fun getZoneById(zoneId: Long): Zone = transaction {
        ZonesTable
            .selectAll()
            .where { ZonesTable.zoneId eq zoneId }
            .singleOrNull()
            ?.toZone()
            ?: throw NoSuchElementException("Zone with ID $zoneId not found")
    }

    override fun getIncidentsList(): List<Incident> = transaction {
        IncidentsTable
            .selectAll()
            .toList()
            .toIncidentsList()
    }

    override fun getIncidentsByZone(zoneId: Long): List<Incident> = transaction {
        IncidentsTable
            .selectAll()
            .where { IncidentsTable.zoneId eq zoneId }
            .toList()
            .toIncidentsList()
    }

    override fun createIncident(incident: IncidentDto): Unit = transaction {
        IncidentsTable.insert {
            it[type] = incident.type
            it[latitude] = incident.latitude
            it[longitude] = incident.longitude
            it[description] = incident.description
            it[status] = incident.status
            it[zoneId] = incident.zoneId!!
        }
    }

    override fun updateIncident(incident: IncidentDto): Unit = transaction {
        if (incident.incidentId == null) return@transaction
        IncidentsTable
            .selectAll()
            .where { IncidentsTable.incidentId eq incident.incidentId }
            .singleOrNull()
            ?.let {
                IncidentsTable.update({ IncidentsTable.incidentId eq incident.incidentId }) {
                    it[type] = incident.type
                    it[latitude] = incident.latitude
                    it[longitude] = incident.longitude
                    it[description] = incident.description
                    it[status] = incident.status
                    it[zoneId] = incident.zoneId!!
                }
            }
    }

    override fun calculateZoneByCoordinates(latitude: Double, longitude: Double): Long {
       return getZonesList().firstOrNull() {
            latitude in it.minLatitude..it.maxLatitude && longitude in it.minLongitude..it.maxLongitude
        }?.zoneId ?: UNDEFINED_ZONE_ID
    }
}