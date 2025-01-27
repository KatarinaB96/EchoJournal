package com.campus.echojournal.core.domain.models

data class Topic(
    val id: Int,
    val name: String,
    val isDefaultTopic: Boolean
)