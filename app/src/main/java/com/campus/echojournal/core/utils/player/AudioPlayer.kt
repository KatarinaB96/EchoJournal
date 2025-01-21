package com.campus.echojournal.core.utils.player

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()

}