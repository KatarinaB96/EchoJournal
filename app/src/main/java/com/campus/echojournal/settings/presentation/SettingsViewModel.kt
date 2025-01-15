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

class SettingsViewModel(private val repository: JournalRepository) : ViewModel() {
    private val _state = MutableStateFlow(ChipListState())

    val state = _state.onStart {
        getChips()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    private fun getChips() {
        viewModelScope.launch {
            repository.getAllTopics().collect { chips ->
                _state.update {
                    it.copy(topics = chips)
                }
            }
        }
    }

    fun onAction(action: TopicListAction) {
        when (action) {
            is TopicListAction.OnAddTopic -> {
                viewModelScope.launch {
                    repository.addTopic(
                        Topic(
                            id = 0,
                            name = action.name
                        )
                    )
                }
            }

            is TopicListAction.OnDeleteTopic -> {
                viewModelScope.launch {
                    repository.deleteTopicById(action.id)
                }
            }
        }
    }
}