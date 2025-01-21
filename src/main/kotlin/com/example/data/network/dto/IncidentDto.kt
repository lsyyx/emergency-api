package com.example.data.network.dto

import com.example.data.db.Status
import com.example.data.db.Type
import com.example.domain.entity.UNDEFINED_ZONE_ID
import kotlinx.serialization.Serializable

@Serializable
data class IncidentDto(
    val incidentId: Long? = null,
    val type: Type,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val status: Status,
    var zoneId: Long? = UNDEFINED_ZONE_ID
)
