package com.campus.echojournal.core

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object EchoJournalGraph : Route

    @Serializable
    data object EntryList : Route

    @Serializable
    data class AddEntry(val path:String) : Route

    @Serializable
    data object Settings : Route

}