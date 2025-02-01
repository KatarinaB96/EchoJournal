package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.campus.echojournal.core.domain.models.Entry
import com.campus.echojournal.ui.theme.EchoJournalTheme

@Composable
fun EntriesListDayView(
    playingEntryId : Int = -1,
    entries : List<Entry>,
    date : String,
    onClickPlay: (Int) -> Unit = {},
    onClickPause: (Int) -> Unit = {},
    onClickResume: (Int) -> Unit = {},
    getAudioAmplitudes: (String) -> List<Int>,
    modifier: Modifier = Modifier
) {
    Column {

        Text(
            date,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp
        )
        for(i in 0..<entries.size) {
            EntryItem(
                playingEntryId = playingEntryId,
                onClickPlay = onClickPlay,
                onClickPause = onClickPause,
                onClickResume = onClickResume,
                index = (if(i == entries.size -1 && i == 0) 3 else if(i == entries.size-1) 2  else if(i == 0) 0  else 1 ),
               //index == 3 for single entry, index == 2 for last entry, index == 0 for first entry, index == 1 for middle entries
                entry = entries[i],
            )
        }


    }
}

@Preview
@Composable
fun EntriesListDayViewPreview() {
    EchoJournalTheme {
        Surface {
            EntriesListDayView(
                date = "Today",
                getAudioAmplitudes = { emptyList() },
                entries = listOf(
                    Entry(
                        id = 0,
                        title = "Title",
                        moodIndex = 0,
                        recordingPath = "",
                        topics = emptyList(),
                        description = "Description",
                        audioDuration = 0
                    )
                )
            )
        }

    }
}