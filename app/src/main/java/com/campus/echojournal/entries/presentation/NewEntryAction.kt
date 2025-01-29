package com.campus.echojournal.entries.presentation

import com.campus.echojournal.core.domain.models.Topic

sealed interface NewEntryAction {
    data class OnAddNewEntry(
        val title: String,
        val moodIndex: Int,
        val recordingPath: String,
        val description: String,
        val topics: List<Topic>,
    ) : NewEntryAction

    data object OnCancel : NewEntryAction
    data object OnDismissDialog : NewEntryAction
    data class OnAddTopic(val name: String) : NewEntryAction
    data class OnDeleteTopic(val name: String) : NewEntryAction
    data class OnSearchQueryChanged(val query: String) : NewEntryAction
}
