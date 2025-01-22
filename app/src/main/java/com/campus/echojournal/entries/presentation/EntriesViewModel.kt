package com.campus.echojournal.entries.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.campus.echojournal.R
import com.campus.echojournal.entries.util.allMoodsList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class EntriesViewModel : ViewModel() {
    var state by mutableStateOf(EntriesState())
        private set

    private val eventChannel = Channel<EntriesEvent>()
    val events = eventChannel.receiveAsFlow()


    fun onAction(action: EntriesAction) {
        when (action) {
            EntriesAction.onStartRecording -> {
                state = state.copy(
                    isRecording = true
                )
            }
            EntriesAction.onCancelRecording -> {
                state = state.copy(
                    isRecording = false,
                )
            }
            EntriesAction.onClickAddEntry -> {
                state = state.copy(
                    isRecordAudioBottomSheetOpen = true
                )
            }
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

            EntriesAction.onPauseRecording -> {
                state = state.copy(
                    isRecording = false
                )
            }

            is EntriesAction.onPlayAudio -> TODO()

            EntriesAction.onResumeRecording -> {
                state = state.copy(
                    isRecording = true
                )
            }

            EntriesAction.onSaveRecording -> {
                state = state.copy(
                    isRecording = false,
                )
            }

            is EntriesAction.onSelectFilterMoods -> {
                val item = action.mood
                val selectedMoods = state.selectedMoods.toMutableList()
                if (!selectedMoods.removeAll { it.second == item }) { // remove if already selected
                    val selectedMood = allMoodsList.find {
                        it.second == item
                    } ?: Pair(0, "")
                    selectedMoods.add(selectedMood) // add to selected
                }
                state = state.copy(
                    selectedMoods = selectedMoods
                )
            }

            is EntriesAction.onSelectFilterTopics -> {
                val item = action.topic
                val selectedTopics = state.selectedTopics.toMutableList()
                if (!selectedTopics.removeAll { it.second == item }) { // remove if already selected
                    selectedTopics.add(Pair(R.drawable.ic_hashtag, item)) // add to selected
                }
                state = state.copy(
                    selectedTopics = selectedTopics
                )
            }

            EntriesAction.OnDismissRecordAudioBottomSheet -> {
                state = state.copy(
                    isRecordAudioBottomSheetOpen = false
                )
            }
            else -> Unit
        }

    }
}