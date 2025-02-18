package com.campus.echojournal.entries.presentation.entries_list

sealed interface EntriesEvent {
    data class OnSavedAudio(val audioFilePath : String) : EntriesEvent
}