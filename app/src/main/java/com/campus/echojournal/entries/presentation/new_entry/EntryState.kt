package com.campus.echojournal.entries.presentation.new_entry

import com.campus.echojournal.core.domain.models.Topic

data class EntryState(
    val defaultTopics: List<Topic> = emptyList(),
    val pickedTopics: List<Topic> = emptyList(),
    val savedMoodIndex: Int = -1,
    val showBackConfirmationDialog: Boolean = false,
    val searchQuery: String = "",
    val filteredTopics: List<Topic> = emptyList(),
    val audioDuration : Int = 0,
    val recordingPath : String = "",
)
