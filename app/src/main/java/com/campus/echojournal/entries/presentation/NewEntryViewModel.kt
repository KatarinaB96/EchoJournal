package com.campus.echojournal.entries.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.echojournal.core.domain.JournalRepository
import com.campus.echojournal.core.domain.models.Entry
import com.campus.echojournal.core.utils.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewEntryViewModel(
    private val repository: JournalRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow(EntryState())

    val state = combine(
        _state,
        dataStoreManager.getSavedMoodIndex
    ) { currentState, savedMoodIndex ->
        currentState.copy(savedMoodIndex = savedMoodIndex)
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
                _state.update {
                    it.copy(defaultTopics = chips)
                }
            }
        }
    }

    fun onAction(action: EntryAction) {
        when (action) {
            is EntryAction.OnAddEntry -> {
                viewModelScope.launch {
                    repository.insertEntryWithTopics(
                        Entry(
                            id = 0,
                            title = action.title,
                            moodIndex = action.moodIndex,
                            recordingPath = action.recordingPath,
                            description = action.description,
                            topics = action.topics
                        )
                    )
                }
            }
        }
    }
}
