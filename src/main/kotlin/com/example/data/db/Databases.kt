package com.example.data.db

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import java.io.File

const val DB_HOST = "127.0.0.1"
const val DB_PORT = 8080
private const val DB_USER = "root"
private const val DB_DRIVER = "org.h2.Driver"
private const val DB_URL_PREFIX = "jdbc:h2:file:"

fun Application.configureDatabases() {
    val databasePath = File("data/db_ktor_server").absolutePath
    Database.connect(
        url = DB_URL_PREFIX + databasePath,
        driver = DB_DRIVER,
        user = DB_USER
    )
}