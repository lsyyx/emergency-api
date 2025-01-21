package com.example.data.network.api

import com.example.data.mapper.toIncidentDto
import com.example.data.mapper.toZoneDto
import com.example.data.network.dto.IncidentDto
import com.example.data.network.dto.ZoneDto
import com.example.data.repository.RepositoryImpl
import com.example.domain.api.ApiService

object ApiServiceImpl : ApiService {

    private val repository = RepositoryImpl()

    override fun getZonesList(): List<ZoneDto> {
        return repository.getZonesList().map { it.toZoneDto() }
    }

    override fun getZoneById(zoneId: Long): ZoneDto {
        return repository.getZoneById(zoneId).toZoneDto()
    }

    override fun getIncidentsList(): List<IncidentDto> {
        return repository.getIncidentsList().map { it.toIncidentDto() }
    }

    override fun getIncidentsByZone(zoneId: Long): List<IncidentDto> {
        return repository.getIncidentsByZone(zoneId).map { it.toIncidentDto() }
    }

    override fun createIncident(incident: IncidentDto) {
        repository.createIncident(incident)
    }

    override fun updateIncident(incident: IncidentDto) {
        repository.updateIncident(incident)
    }

    override fun calculateZone(incident: IncidentDto): Long {
        return repository.calculateZoneByCoordinates(incident.latitude, incident.longitude)
    }
}