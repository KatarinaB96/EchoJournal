package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.campus.echojournal.R
import com.campus.echojournal.core.utils.player.AndroidAudioPlayer
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.Excited_35
import com.campus.echojournal.ui.theme.Excited_80
import com.campus.echojournal.ui.theme.Excited_95
import com.campus.echojournal.ui.theme.Neutral_35
import com.campus.echojournal.ui.theme.Neutral_80
import com.campus.echojournal.ui.theme.Neutral_95
import com.campus.echojournal.ui.theme.Peaceful_35
import com.campus.echojournal.ui.theme.Peaceful_80
import com.campus.echojournal.ui.theme.Peaceful_95
import com.campus.echojournal.ui.theme.Sad_35
import com.campus.echojournal.ui.theme.Sad_80
import com.campus.echojournal.ui.theme.Sad_95
import com.campus.echojournal.ui.theme.Stressed_35
import com.campus.echojournal.ui.theme.Stressed_80
import com.campus.echojournal.ui.theme.Stressed_95
import com.linc.audiowaveform.AudioWaveform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AudioWave(
    amplitudes: List<Int>,
    audioDuration: Int,
    moodIndex: Int,
    id: Int = 0,
    isActiveAudio: Boolean = false,
    onClickPlay: (Int) -> Unit = {},
    onClickPause: (Int) -> Unit = {},
    onClickResume: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {

    val player: AndroidAudioPlayer = koinInject()

    val isPlaying by player.isPlaying.collectAsStateWithLifecycle()


    var currentPosition by remember {
        mutableStateOf(0)
    }
    val audioProgressFlow = remember { MutableStateFlow(0f) }
    val waveformProgress by audioProgressFlow.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(currentPosition, audioDuration) {
        if (audioDuration > 0) {
            audioProgressFlow.value = currentPosition.toFloat() / audioDuration.toFloat()
        }
    }

    Row(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(100.dp))
            .background(getColorForMood(moodIndex)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = 4.dp)
                .size(32.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(100.dp),
                    spotColor = Color.Black

                )
                .clip(RoundedCornerShape(100.dp))
                .background(Color.White)
                .clickable {
                    if (isActiveAudio) {
                        when {
                            isPlaying -> onClickPause(id)
                            currentPosition == 0 -> onClickPlay(id)
                            else -> onClickResume(id)
                        }
                    } else {
                        onClickPlay(id)
                    }

                    coroutineScope.launch {
                        player
                            .getCurrentPosition()
                            .collect { currentPosition = it }
                    }
                },
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = painterResource(if (isPlaying && isActiveAudio) R.drawable.ic_pause else R.drawable.ic_play),
                tint = getAudioWavePlayColor(moodIndex),
                contentDescription = "Play",
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
        )
        {
            AudioWaveform(
                waveformBrush = SolidColor(getAuidoWaveNotPlayColor(moodIndex)),
                progressBrush = SolidColor(getAudioWavePlayColor(moodIndex)),
                modifier = Modifier.padding(vertical = 10.dp),
                spikeWidth = 4.dp,
                spikePadding = 2.dp,
                amplitudes = amplitudes,
                progress = if (isActiveAudio) waveformProgress else 0f,
                onProgressChange = { },
            )
        }

        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = formatSecondsToTime(if (isActiveAudio) currentPosition else 0) + "/" + formatSecondsToTime(
                audioDuration
            ),
            color = Color.Black,
            fontSize = 12.sp
        )

    }

}

fun getColorForMood(moodIndex: Int): Color {
    return when (moodIndex) {
        0 -> Excited_95
        1 -> Peaceful_95
        2 -> Neutral_95
        3 -> Sad_95
        4 -> Stressed_95

        else -> Neutral_95
    }
}

fun getAuidoWaveNotPlayColor(moodIndex: Int): Color {
    return when (moodIndex) {
        0 -> Excited_80
        1 -> Peaceful_80
        2 -> Neutral_80
        3 -> Sad_80
        4 -> Stressed_80
        else -> Neutral_80
    }
}

fun getAudioWavePlayColor(moodIndex: Int): Color {
    return when (moodIndex) {
        0 -> Excited_35
        1 -> Peaceful_35
        2 -> Neutral_35
        3 -> Sad_35
        4 -> Stressed_35
        else -> Neutral_35
    }
}


fun formatSecondsToTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}

@Preview
@Composable
private fun AudioWavePreview() {
    EchoJournalTheme {
        Surface {
            AudioWave(

                amplitudes = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                onClickPlay = {},
                onClickPause = {},
                onClickResume = {},
                audioDuration = 12,
                moodIndex = 5,
                modifier = Modifier,
            )
        }

    }
}