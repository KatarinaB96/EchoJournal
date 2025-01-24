package com.campus.echojournal.entries.presentation

import com.campus.echojournal.core.domain.models.Topic

sealed interface EntriesAction {
    data object onClickAllMoods: EntriesAction
    data class onSelectFilterMoods(val moodId: Int): EntriesAction

    data object onClickAllTopics: EntriesAction
    data class onSelectFilterTopics(val topic: Topic): EntriesAction

    data class onPlayAudio(val id: Int): EntriesAction
    data class onPauseAudio(val id: Int): EntriesAction
    data class onResumeAudio(val id: Int): EntriesAction

    data object onClickAddEntry: EntriesAction
    data object OnDismissRecordAudioBottomSheet: EntriesAction

    data object onStartRecording: EntriesAction
    data object onCancelRecording: EntriesAction
    data object onPauseRecording: EntriesAction
    data object onResumeRecording: EntriesAction
    data object onSaveRecording: EntriesAction

    data object onSettingsClick: EntriesAction
    data object loadEntries : EntriesAction



}