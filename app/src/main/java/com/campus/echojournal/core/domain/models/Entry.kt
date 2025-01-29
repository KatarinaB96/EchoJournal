package com.campus.echojournal.core.domain.models

data class Entry(
    val id: Int,
    val title: String,
    val moodIndex: Int,
    val recordingPath: String,
    val topics: List<Topic>,
    val description: String,
    val audioDuration: Int,
    val createdDate: Long = System.currentTimeMillis()
)

