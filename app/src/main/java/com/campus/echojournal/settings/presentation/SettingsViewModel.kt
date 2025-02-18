package com.campus.echojournal.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.echojournal.core.domain.JournalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.campus.echojournal.core.domain.models.Topic
import com.campus.echojournal.core.utils.DataStoreManager
import kotlinx.coroutines.flow.combine

class SettingsViewModel(
    private val repository: JournalRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())

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
                    it.copy(topics = chips)
                }
            }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnAddTopic -> {
                viewModelScope.launch {
                    repository.addTopic(
                        Topic(
                            id = 0,
                            name = action.name,
                            isDefaultTopic = true
                        )
                    )
                }
            }

            is SettingsAction.OnDeleteTopic -> {
                viewModelScope.launch {
                    repository.deleteTopicById(action.id)
                }
            }

            is SettingsAction.OnMoodIndexChanged -> {
                viewModelScope.launch {
                    dataStoreManager.saveSelectedMoodIndex(action.index)
                }
                _state.update {
                    it.copy(savedMoodIndex = action.index)
                }
            }

            SettingsAction.OnBack -> {
            }
        }
    }
}