package com.campus.echojournal.core.utils.recorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Build.VERSION
import java.io.File
import java.io.FileOutputStream

class AndroidAudioRecorder(private val context: Context) : AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var outputFile: File? = null


    private fun createRecorder(): MediaRecorder {


        return if (VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
    }

    override fun start(outputFile: File) {
        this.outputFile = outputFile
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            recorder = this
        }
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder = null

    }

    override fun pause() {
        recorder?.pause()
    }

    override fun resume() {
        recorder?.resume()

    }

    override fun cancel() {
        try {
            recorder?.apply {
                stop()
                reset()
                release()
            }
            recorder = null

            outputFile?.let {
                if (it.exists()) {
                    it.delete()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            recorder = null
            outputFile = null
        }
    }


}