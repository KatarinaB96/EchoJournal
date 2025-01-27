package com.campus.echojournal.settings.presentation

import com.campus.echojournal.core.domain.models.Topic

data class TopicListState(
    val topics: List<Topic> = emptyList(),
    val savedMoodIndex: Int = -1
)