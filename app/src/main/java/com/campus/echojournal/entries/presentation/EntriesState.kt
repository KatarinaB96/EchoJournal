package com.campus.echojournal.entries.presentation


data class EntriesState(
    val isAllMoodsOpen: Boolean = false,
    val isAllTopicsOpen : Boolean = false,
    val selectedMoods: List<Pair<Int, String>> = emptyList(),
    val selectedTopics: List<Pair<Int, String>> = emptyList(),
    val isRecordAudioBottomSheetOpen : Boolean = false,
    )