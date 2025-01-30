package com.campus.echojournal.core.utils.player

import kotlinx.coroutines.flow.Flow
import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
    fun pause()
    fun resume()
    fun getDuration(file: File): Int
    fun getCurrentPosition(): Flow<Int>
}