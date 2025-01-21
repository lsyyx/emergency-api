package com.example.data.network.dto

import com.example.data.db.Level
import com.example.data.db.ZonesTable.autoIncrement
import com.example.data.db.ZonesTable.customEnumeration
import com.example.data.db.ZonesTable.double
import com.example.data.db.ZonesTable.long
import com.example.data.db.ZonesTable.varchar
import kotlinx.serialization.Serializable

@Serializable
data class ZoneDto(
    val zoneId: Long,
    val name: String,
    val phone: String,
    val minLatitude: Double,
    val maxLatitude: Double,
    val minLongitude: Double,
    val maxLongitude: Double,
    val level: Level
)