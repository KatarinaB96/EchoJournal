package com.campus.echojournal.settings.presentation

sealed interface SettingsAction {
    data class OnAddTopic(val name: String) : SettingsAction
    data class OnDeleteTopic(val id: Int) : SettingsAction
    data class OnMoodIndexChanged(val index: Int) : SettingsAction
    data object OnBack: SettingsAction
}
