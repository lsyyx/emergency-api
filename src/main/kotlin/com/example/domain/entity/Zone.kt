package com.example.domain.entity

import com.example.data.db.Level

data class Zone(
    val zoneId: Long,
    val name: String,
    val phone: String,
    val minLatitude: Double,
    val maxLatitude: Double,
    val minLongitude: Double,
    val maxLongitude: Double,
    val level: Level,
)