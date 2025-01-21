package com.example.data.db

import org.jetbrains.exposed.sql.Table

const val MAX_VARCHAR_LENGTH = 255

object ZonesTable : Table("zones") {
    val zoneId = long("zone_id").autoIncrement()
    val name = varchar("zone_name", MAX_VARCHAR_LENGTH)
    val phone = varchar("phone", MAX_VARCHAR_LENGTH)
    val minLatitude = double("min_latitude")
    val maxLatitude = double("max_latitude")
    val minLongitude = double("min_longitude")
    val maxLongitude = double("max_longitude")
    val level = customEnumeration(
        name = "LEVEL",
        sql = "ENUM('LOW', 'MEDIUM', 'HIGH')",
        fromDb = { value -> Level.valueOf(value.toString()) },
        toDb = { it.name }
    )

    override val primaryKey = PrimaryKey(zoneId)
}

object IncidentsTable : Table("incidents") {
    val incidentId = long("incident_id").autoIncrement()

    val type = customEnumeration(
        name = "TYPE",
        sql = "ENUM('FIRE', 'FLOOD',  'NATURAL_DISASTER', 'GASLEAK', 'OTHER')",
        fromDb = { value -> Type.valueOf(value.toString()) },
        toDb = { it.name }
    )

    val latitude = double("latitude")
    val longitude = double("longitude")
    val description = varchar("description", MAX_VARCHAR_LENGTH)

    val status = customEnumeration(
        name = "STATUS",
        sql = "ENUM('NEW', 'IN_PROGRESS', 'CLOSED')",
        fromDb = { value -> Status.valueOf(value.toString()) },
        toDb = { it.name }
    )

    val zoneId = long("zone_id").references(ZonesTable.zoneId)

    override val primaryKey = PrimaryKey(incidentId)
}