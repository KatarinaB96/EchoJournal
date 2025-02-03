package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.core.domain.models.Entry
import com.campus.echojournal.entries.presentation.util.AudioWaveManager
import com.campus.echojournal.entries.util.allMoodsList
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.LineColor
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun EntryItem(
    entry: Entry,
    playingEntryId: Int = -1,
    onClickPlay: (Int) -> Unit = {},
    onClickPause: (Int) -> Unit = {},
    onClickResume: (Int) -> Unit = {},
    index: Int = 0,
    modifier: Modifier = Modifier,
) {
    val audioWaveManager: AudioWaveManager = koinInject()

    var amplitudes by remember {
        mutableStateOf(emptyList<Int>())
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        coroutineScope.launch {
            amplitudes = audioWaveManager.getAmplitudes(entry.recordingPath)
        }
    }


    val scrollState = rememberScrollState()
    Row(modifier = modifier.height(IntrinsicSize.Max)) {
        Box(
            contentAlignment = Alignment.TopCenter,
        ) {

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(if (index == 2) 0.1f else if (index == 3) 0f else 1f)
                    .padding(top = if (index == 0) 20.dp else 0.dp)
                    .background(LineColor)
            )

            Image(
                modifier = Modifier.padding(top = 16.dp),
                painter = painterResource(id = allMoodsList.get(entry.moodIndex).first),
                contentDescription = allMoodsList.get(entry.moodIndex).second
            )
        }



        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(
                        end = 14.dp, start = 14.dp, top = 12.dp, bottom =
                        14.dp
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        entry.title, fontWeight = FontWeight.W500
                    )
                    Text(formatTimeFromLong(entry.createdDate))

                }
                AudioWave(
                    onClickPlay = onClickPlay,
                    onClickPause = onClickPause,
                    onClickResume = onClickResume,
                    audioDuration = entry.audioDuration,
                    id = entry.id,
                    amplitudes = amplitudes,
                    moodIndex = entry.moodIndex,
                    isActiveAudio = entry.id == playingEntryId
                )
               ExpandableText(
                    text = entry.description,
                    modifier = Modifier.padding(top = 8.dp),

               )
                Row(
                    modifier = Modifier.horizontalScroll(
                        scrollState
                    )
                ) {
                    repeat(
                        entry.topics.size
                    ) {
                        EntriesTopicChip(
                            modifier = Modifier.padding(2.dp),
                            title = entry.topics[it].name
                        )

                    }
                }

            }
        }

    }


}


// TODO: Move to a common place

fun formatTimeFromLong(timestamp: Long, zoneId: ZoneId = ZoneId.systemDefault()): String {
    return Instant.ofEpochMilli(timestamp)
        .atZone(zoneId)
        .format(DateTimeFormatter.ofPattern("HH:mm"))
}


@Preview
@Composable
private fun EntryItemPreview() {
    EchoJournalTheme {
        EntryItem(
            entry = Entry(
                id = 0,
                title = "Title",
                moodIndex = 0,
                recordingPath = "",
                topics = emptyList(),
                description = "Description",
                audioDuration = 0,

                )
        )
    }
}