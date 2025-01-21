package com.example.domain.api

import com.example.data.network.dto.IncidentDto
import com.example.data.network.dto.ZoneDto
import com.example.domain.entity.Zone

interface ApiService {

    fun getZonesList(): List<ZoneDto>

    fun getZoneById(zoneId: Long): ZoneDto

    fun getIncidentsList(): List<IncidentDto>

    fun getIncidentsByZone(zoneId: Long): List<IncidentDto>

    fun createIncident(incident: IncidentDto)

    fun updateIncident(incident: IncidentDto)

    fun calculateZone(incident: IncidentDto): Long
}