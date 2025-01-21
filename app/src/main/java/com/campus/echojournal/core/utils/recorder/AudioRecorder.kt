package com.campus.echojournal.core.utils.recorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}