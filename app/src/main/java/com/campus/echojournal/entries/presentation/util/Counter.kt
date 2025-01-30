package com.campus.echojournal.entries.presentation.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch



class Counter {
    private val _counterFlow = MutableStateFlow(0)
    val counterFlow: StateFlow<Int> = _counterFlow.asStateFlow()

    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    fun start() {
        if (job == null || job?.isCancelled == true) {
            job = scope.launch {
                while (isActive) {
                    _counterFlow.value++
                    println( _counterFlow.value)
                    delay(1000)
                }
            }
        }
    }

    fun pause() {
        job?.cancel()
        job = null
    }

    fun reset() {
        pause()
        _counterFlow.value = 0
    }
}