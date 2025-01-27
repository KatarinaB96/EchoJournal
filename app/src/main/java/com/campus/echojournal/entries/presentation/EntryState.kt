package com.campus.echojournal.entries.presentation

import com.campus.echojournal.core.domain.models.Topic

data class EntryState(
    val defaultTopics: List<Topic> = emptyList(),
    val savedMoodIndex: Int = -1,
)
