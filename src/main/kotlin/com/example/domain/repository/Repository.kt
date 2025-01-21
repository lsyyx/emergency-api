package com.example.domain.repository

import com.example.data.network.dto.IncidentDto
import com.example.domain.entity.Incident
import com.example.domain.entity.Zone


interface Repository {

    fun getZonesList(): List<Zone>

    fun getZoneById(zoneId: Long):Zone

    fun getIncidentsList(): List<Incident>

    fun getIncidentsByZone(zoneId: Long): List<Incident>

    fun createIncident(incident: IncidentDto)

    fun updateIncident(incident: IncidentDto)

    fun calculateZoneByCoordinates(latitude: Double, longitude: Double): Long
}