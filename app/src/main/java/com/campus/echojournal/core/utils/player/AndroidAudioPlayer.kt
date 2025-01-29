package com.campus.echojournal.core.utils.player

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import androidx.core.net.toUri
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class AndroidAudioPlayer(
    private val context: Context
) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: File) {
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }

    override fun pause() {
        player?.pause()
    }

    override fun resume() {
        player?.let {
            if (!it.isPlaying) {
                it.start()
            }
        }
    }

    override fun getDuration(file: File): Duration {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(file.absolutePath)
        val durationMs = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0
        return durationMs.milliseconds
    }

    override fun getCurrentPosition(): Flow<Int> = flow {
        while (player != null) {
            emit((player?.currentPosition ?: 0) / 1000)
            delay(1000)
        }
    }

}