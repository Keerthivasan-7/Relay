package com.keerthi.relay.models

data class CallLogEntry(
    val name: String?,
    val number: String,
    val type: String, // e.g., Incoming, Outgoing, Missed
    val date: Long, // timestamp
    val duration: String // call duration in seconds
)
