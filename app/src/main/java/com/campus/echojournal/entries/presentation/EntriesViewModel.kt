package com.campus.echojournal.entries.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class EntriesViewModel : ViewModel() {
    var state by mutableStateOf(EntriesState())
        private set

    private val eventChannel = Channel<EntriesEvent>()
    val events = eventChannel.receiveAsFlow()

        fun onAction(action: EntriesAction) {
        when (action) {
            EntriesAction.onCancelRecording -> TODO()
            EntriesAction.onClickAddEntry -> TODO()
            EntriesAction.onClickAllMoods -> {

                state = state.copy(
                    isAllMoodsOpen = !state.isAllMoodsOpen,
                    isAllTopicsOpen = if (!state.isAllMoodsOpen) false else state.isAllTopicsOpen
                )
            }
            EntriesAction.onClickAllTopics -> {
                state = state.copy(
                    isAllTopicsOpen = !state.isAllTopicsOpen,
                    isAllMoodsOpen = if (!state.isAllTopicsOpen) false else state.isAllMoodsOpen
                )
            }
            is EntriesAction.onPauseAudio -> TODO()
            EntriesAction.onPauseRecording -> TODO()
            is EntriesAction.onPlayAudio -> TODO()
            EntriesAction.onResumeRecording -> TODO()
            EntriesAction.onSaveRecording -> TODO()
            EntriesAction.onSelectFilterMoods -> TODO()
            EntriesAction.onSelectFilterTopics -> TODO()
            else -> Unit
        }

    }
}