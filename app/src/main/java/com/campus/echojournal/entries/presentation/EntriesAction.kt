package com.campus.echojournal.entries.presentation

sealed interface EntriesAction {
    data object onClickAllMoods: EntriesAction
    data class onSelectFilterMoods(val mood: String): EntriesAction

    data object onClickAllTopics: EntriesAction
    data class onSelectFilterTopics(val topic: String): EntriesAction

    data class onPlayAudio(val id: Int): EntriesAction
    data class onPauseAudio(val id: Int): EntriesAction

    data object onClickAddEntry: EntriesAction

    data object onCancelRecording: EntriesAction
    data object onPauseRecording: EntriesAction
    data object onResumeRecording: EntriesAction
    data object onSaveRecording: EntriesAction


    data object onSettingsClick: EntriesAction





}