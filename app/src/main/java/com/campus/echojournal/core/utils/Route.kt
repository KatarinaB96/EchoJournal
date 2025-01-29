package com.campus.echojournal.core.utils

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object EchoGraph : Route

    @Serializable
    data object HomeScreen : Route

    @Serializable
    data class AddEntryScreen(val fileUri: String, val duration: String) : Route

    @Serializable
    data object SettingsScreen : Route

}