package com.campus.echojournal.settings.presentation

sealed interface TopicListAction {
    data class OnAddTopic(val name: String) : TopicListAction
    data class OnDeleteTopic(val id: Int) : TopicListAction
    data class OnMoodIndexChanged(val index: Int) : TopicListAction
}
