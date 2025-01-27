package com.campus.echojournal.entries.presentation

import com.campus.echojournal.core.domain.models.Topic

sealed interface EntryAction {
    data class OnAddEntry(
        val title: String,
        val moodIndex: Int,
        val recordingPath: String,
        val description: String,
        val topics: List<Topic>,
    ) : EntryAction

}
