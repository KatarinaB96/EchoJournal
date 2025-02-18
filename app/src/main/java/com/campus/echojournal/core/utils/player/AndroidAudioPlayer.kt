package com.campus.echojournal.core.utils.player

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import androidx.core.net.toUri
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import java.io.File

class AndroidAudioPlayer(
    private val context: Context
) : AudioPlayer {

    private var player: MediaPlayer? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying


    override fun playFile(file: File) {
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
        }
        _isPlaying.value = true
        player?.setOnCompletionListener {
            _isPlaying.value = false
        }

    }

    override fun stop() {
        _isPlaying.value = false
        player?.stop()
        player?.release()
        player = null
    }

    override fun pause() {
        _isPlaying.value = false
        player?.pause()
    }

    override fun resume() {
        _isPlaying.value = true
        player?.let {
            if (!it.isPlaying) {
                it.start()
            }
        }

    }

    override fun getDuration(file: File): Int {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(context, file.toUri())
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            duration?.toInt()?.div(1000) ?: 0
        } catch (e: Exception) {
            0
        } finally {
            retriever.release()
        }
    }

    override fun getCurrentPosition(): Flow<Int> = flow {
        while (player != null) {
            emit((player?.currentPosition ?: 0) / 1000)
            delay(500)
        }
    }

}