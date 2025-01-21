package com.example.domain.entity

import com.example.data.db.Status
import com.example.data.db.Type

const val UNDEFINED_ZONE_ID = 0L

data class Incident(
    val incidentId: Long? = null,
    val type: Type,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val status: Status,
    val zoneId: Long? = UNDEFINED_ZONE_ID
)