package com.campus.echojournal.entries.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.echojournal.core.domain.JournalRepository
import com.campus.echojournal.core.domain.models.Entry
import com.campus.echojournal.core.utils.player.AndroidAudioPlayer
import com.campus.echojournal.core.utils.recorder.AndroidAudioRecorder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File

class EntriesViewModel(
    private val application: Application,
    private val repository: JournalRepository,
) : ViewModel() {
    var state by mutableStateOf(EntriesState())
        private set

    //    private val _startRecording = MutableStateFlow(false) // Tracks if recording should start
    //    val startRecording: StateFlow<Boolean> = _startRecording

    init {
        repository.getAllEntriesWithTopics().onEach { entries ->
            state = state.copy(entries = entries, filteredEntries = entries)
        }.launchIn(viewModelScope)
    }

    private val eventChannel = Channel<EntriesEvent>()
    val events = eventChannel.receiveAsFlow()

    private val recorder by lazy {
        AndroidAudioRecorder(application)
    }

    private val player by lazy {
        AndroidAudioPlayer(application)
    }

    private var audioFile: File? = null

    fun onAction(action: EntriesAction) {
        Log.d("Widget", "EntriesViewModel onAction called with: $action")
        when (action) {
            EntriesAction.onStartRecording -> {
                Log.d("Widget", "EntriesViewModel Starting recording")
                state = state.copy(
                    isRecording = true
                )
                File(application.cacheDir, "audio_${System.currentTimeMillis()}.mp3").also {
                    recorder.start(it)
                    audioFile = it
                    Log.d("Widget", " EntriesViewModel Recording started at: ${it.absolutePath}")
                }
            }

            EntriesAction.onCancelRecording -> {
                Log.d("Widget", "EntriesViewModel Canceling recording")
                state = state.copy(
                    isRecording = false,
                )
                recorder.cancel()
            }

            EntriesAction.onClickAddEntry -> {
                Log.d("Widget",  " EntriesViewModel Opening bottom sheet")
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

            is EntriesAction.onPauseAudio -> {
                player.pause()
                state = state.copy(
                    isPlaying = false
                )
            }

            EntriesAction.onPauseRecording -> {
                state = state.copy(
                    isRecording = false
                )
                recorder.pause()
            }

            is EntriesAction.onPlayAudio -> {
                player.playFile(audioFile!!)
                state = state.copy(
                    isPlaying = true
                )
            }

            is EntriesAction.onResumeAudio -> {
                player.resume()
                state = state.copy(
                    isPlaying = true
                )
            }

            EntriesAction.onResumeRecording -> {
                state = state.copy(
                    isRecording = true
                )
                recorder.resume()
            }

            EntriesAction.onSaveRecording -> {
                viewModelScope.launch {
                    state = state.copy(
                        isRecording = false,
                        audioFileUri = audioFile?.absolutePath ?: ""
                    )
                    recorder.stop()

                    eventChannel.send(EntriesEvent.OnSavedAudio(audioFile?.absolutePath ?: ""))
                }

            }

            is EntriesAction.onSelectFilterMoods -> {
                val item = action.moodId
                val selectedMoods = state.selectedMoods.toMutableList()
                if (!selectedMoods.removeIf {
                        it == item
                    }) { // remove if already selected
                    val selectedMood = item
                    selectedMoods.add(selectedMood) // add to selected
                }

                val filteredEntries =  filterEntries()


                state = state.copy(
                    selectedMoods = selectedMoods,
                    filteredEntries = filteredEntries
                )

                println("Selected Moods: ${state.selectedMoods}")
            }

            is EntriesAction.onSelectFilterTopics -> {
                val item = action.topic
                val selectedTopics = state.selectedTopics.toMutableList()
                if (!selectedTopics.removeIf {
                        it == item
                    }) { // remove if already selected
                    selectedTopics.add(item) // add to selected
                }
                val filteredEntries =  filterEntries()
                state = state.copy(
                    selectedTopics = selectedTopics,
                    filteredEntries = filteredEntries
                )
                println(
                    "Selected Topics: ${state.selectedTopics}"
                )

            }

            EntriesAction.OnDismissRecordAudioBottomSheet -> {
                state = state.copy(
                    isRecordAudioBottomSheetOpen = false
                )
            }

            EntriesAction.loadEntries -> {
                // Load entries


            }

            else -> Unit
        }

    }

    private fun filterEntries(
    ): List<Entry> {
        return state.entries.filter { entry ->
            val moodMatch = state.selectedMoods.isEmpty() || entry.moodIndex in state.selectedMoods

            val topicMatch = state.selectedTopics.isEmpty() ||
                    entry.topics.any { entryTopic ->
                        state.selectedTopics.any { filterTopic ->
                            filterTopic.id == entryTopic.id
                        }
                    }
            moodMatch && topicMatch
        }
    }

    fun setStartRecording(startRecording: Boolean) {
        if (startRecording){
            state = state.copy(
                isRecordAudioBottomSheetOpen = true
            )
            Log.d("Widget", "EntriesViewModel setStartRecording called with: $startRecording")
        }

    }
}