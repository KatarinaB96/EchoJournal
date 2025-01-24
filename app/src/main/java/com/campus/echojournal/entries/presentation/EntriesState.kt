package com.campus.echojournal.entries.presentation

import com.campus.echojournal.core.domain.models.Entry
import com.campus.echojournal.core.domain.models.Topic


data class EntriesState(
    val isAllMoodsOpen: Boolean = false,
    val isAllTopicsOpen: Boolean = false,
    val selectedMoods: List<Int> = emptyList(),
    val selectedTopics: List<Int> = emptyList(),
    val isRecordAudioBottomSheetOpen: Boolean = false,
    val isRecording: Boolean = false,
    val isPlaying: Boolean = false,
    val entries: List<Entry> = emptyList(),
    val topics: List<Topic> = emptyList(),
)