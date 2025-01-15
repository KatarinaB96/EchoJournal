package com.campus.echojournal.settings.presentation

import com.campus.echojournal.core.domain.models.Topic

data class ChipListState(
    val topics: List<Topic> = emptyList(),
    val searchQuery: String = ""
)