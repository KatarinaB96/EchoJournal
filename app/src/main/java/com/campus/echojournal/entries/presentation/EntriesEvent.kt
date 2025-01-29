package com.campus.echojournal.entries.presentation

sealed interface EntriesEvent {
    data class OnSavedAudio(val audioFilePath : String) : EntriesEvent
}