package com.campus.echojournal.entries.presentation.new_entry

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.echojournal.core.domain.JournalRepository
import com.campus.echojournal.core.domain.models.Entry
import com.campus.echojournal.core.domain.models.Topic
import com.campus.echojournal.core.utils.DataStoreManager
import com.campus.echojournal.core.utils.player.AndroidAudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewEntryViewModel(
    private val repository: JournalRepository,
    private val dataStoreManager: DataStoreManager,
     val player: AndroidAudioPlayer

) : ViewModel() {
    private val _state = MutableStateFlow(EntryState())
    private val newTopics = mutableStateListOf<Topic>()


    val state = combine(
        _state,
        dataStoreManager.getSavedMoodIndex
    ) { currentState, savedMoodIndex ->
        currentState.copy(
            savedMoodIndex = savedMoodIndex,
            pickedTopics = newTopics,
            filteredTopics = filterTopics(currentState.searchQuery)
        )
    }.onStart {
        getChips()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )



    private fun getChips() {
        viewModelScope.launch {
            repository.getAllDefaultTopics().collect { chips ->
                chips.forEach { defaultTopic ->
                    if (!newTopics.any { it.name == defaultTopic.name }) {
                        newTopics.add(defaultTopic)
                    }
                }
                _state.update {
                    it.copy(defaultTopics = chips)
                }
            }
        }
    }

    private fun filterTopics(query: String): List<Topic> {
        if (query.isBlank()) return newTopics
        return newTopics.filter { it.name.contains(query, ignoreCase = true) }
    }

    fun onAction(action: NewEntryAction) {
        when (action) {
            is NewEntryAction.OnAddNewEntry -> {
                viewModelScope.launch {
                    repository.insertEntryWithTopics(
                        Entry(
                            id = 0,
                            title = action.title,
                            moodIndex = action.moodIndex,
                            recordingPath = action.recordingPath,
                            description = action.description,
//                            topics = action.topics,
                            audioDuration = _state.value.audioDuration,
                            topics = newTopics
                        )
                    )
                }
            }

            NewEntryAction.OnCancel -> {
                _state.update {
                    it.copy(
                        showBackConfirmationDialog = true
                    )
                }
            }

            NewEntryAction.OnDismissDialog -> {
                _state.update {
                    it.copy(showBackConfirmationDialog = false)
                }
            }

            is NewEntryAction.OnAddTopic -> {
                val newTopic = Topic(
                    id = 0,
                    name = action.name,
                    isDefaultTopic = false
                )
                if (!newTopics.any { it.name == newTopic.name }) {
                    newTopics.add(newTopic)
                    _state.update {
                        it.copy(pickedTopics = newTopics)
                    }
                }
            }

            is NewEntryAction.OnDeleteTopic -> {
                newTopics.removeIf { it.name == action.name }
                _state.update {
                    it.copy(pickedTopics = newTopics)
                }
            }

            is NewEntryAction.OnSearchQueryChanged -> {
                _state.update {
                    it.copy(
                        searchQuery = action.query,
                        filteredTopics = filterTopics(action.query)
                    )
                }
            }
        }
    }
fun setRecordingPath(path: String) {
        _state.update {
            it.copy(recordingPath = path)
        }
    }

    fun setAudioDuration(duration: Int) {
        _state.update {
            it.copy(audioDuration = duration)
        }
    }
}
