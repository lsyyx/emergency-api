package com.example.data.db

import kotlinx.serialization.Serializable

@Serializable
enum class Status {
    NEW, IN_PROGRESS, CLOSED
}