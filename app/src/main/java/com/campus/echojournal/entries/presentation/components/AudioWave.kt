package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.linc.audiowaveform.AudioWaveform

@Composable
fun AudioWave(modifier: Modifier = Modifier) {

    var waveformProgress by remember { mutableStateOf(1F) }
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { },
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
            )
        }
        Box(modifier = Modifier.fillMaxWidth(0.6f))
        {
            AudioWaveform(
                spikeWidth = 2.dp,
                spikePadding = 1.dp,
                amplitudes = listOf(
                    2,
                    3,
                    5,
                    54,
                    534,
                    5,
                    43,
                    5,
                    67,
                    24,
                    324,
                    23,
                    4,
                    32,
                    54,
                    54,
                    6565,
                    76,
                ),
                progress = waveformProgress,
                onProgressChange = { waveformProgress = it },
            )
        }

        Text(
            text = "0:00/12:30",
            color = Color.Black
        )

    }

}

@Preview
@Composable
private fun AudioWavePreview() {
    EchoJournalTheme {
        Surface {
            AudioWave()
        }

    }
}