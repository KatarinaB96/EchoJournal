package com.campus.echojournal.entries.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.campus.echojournal.core.utils.player.AndroidAudioPlayer
import com.campus.echojournal.core.utils.recorder.AndroidAudioRecorder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.io.File

class EntriesViewModel(
    private val application: Application
) : ViewModel() {
    var state by mutableStateOf(EntriesState())
        private set

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
        when (action) {
            EntriesAction.onStartRecording -> {
                state = state.copy(
                    isRecording = true
                )
                File(application.cacheDir, "audio_${System.currentTimeMillis()}.mp3").also {
                    recorder.start(it)
                    audioFile = it
                }

            }

            EntriesAction.onCancelRecording -> {
                state = state.copy(
                    isRecording = false,
                )
                recorder.cancel()
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

            is EntriesAction.onPauseAudio ->{
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
            is EntriesAction.onResumeAudio ->{
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
                state = state.copy(
                    isRecording = false,
                )
                recorder.stop()
            }

            is EntriesAction.onSelectFilterMoods -> {
                val item = action.moodId
                val selectedMoods = state.selectedMoods.toMutableList()
                if (!selectedMoods.removeIf {
                    it == item }) { // remove if already selected
                    val selectedMood = item
                    selectedMoods.add(selectedMood) // add to selected
                }
                state = state.copy(
                    selectedMoods = selectedMoods
                )

                println("Selected Moods: ${state.selectedMoods}")
            }

            is EntriesAction.onSelectFilterTopics -> {
        /*        val item = action.topic
                val selectedTopics = state.selectedTopics.toMutableList()
                if (!selectedTopics.removeAll { it.second == item }) { // remove if already selected
                    selectedTopics.add(Pair(R.drawable.ic_hashtag, item)) // add to selected
                }
                state = state.copy(
                    selectedTopics = selectedTopics
                )*/
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