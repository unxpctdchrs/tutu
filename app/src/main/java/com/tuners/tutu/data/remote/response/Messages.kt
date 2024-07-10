package com.tuners.tutu.data.remote.response

import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Messages(
    val messageId: String,
    val chatroomId: String,
    val timestamp: Timestamp,
    val message: String,
)
