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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.Neutral_35
import com.campus.echojournal.ui.theme.Neutral_95
import com.linc.audiowaveform.AudioWaveform

@Composable
fun AudioWave(
    amplitudes: List<Int> = listOf(),
    audioDuration : String,
    currentDuration : String,

    onClickPlay: (Int) -> Unit = {},
    onClickPause: (Int) -> Unit = {},
    onClickResume: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {

    var waveformProgress by remember { mutableStateOf(1F) }


    Row(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(100.dp))
            .background(Neutral_95),
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
                    onClickPlay(1)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Neutral_35
            )
        }
        Box(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth(0.6f)
                .padding(2.dp)
        )
        {
            AudioWaveform(
                modifier = Modifier
                    .height(32.dp),
                spikeWidth = 3.dp,
                spikePadding = 1.dp,
                amplitudes = amplitudes,
                progress = waveformProgress,
                onProgressChange = { waveformProgress = it },
            )
        }

        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = "0:00/" + audioDuration,
            color = Color.Black
        )

    }

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
                onClickResume =  {},
                audioDuration = "12:30",
                currentDuration = "0:00",
                modifier =  Modifier
            )
        }

    }
}