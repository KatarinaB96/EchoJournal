package com.campus.echojournal.entries.presentation

import kotlin.time.Duration

sealed interface EntriesEvent {
    data class OnSavedAudio(val audioFilePath: String, val audioDuration: Duration) : EntriesEvent
}