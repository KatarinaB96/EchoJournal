package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.campus.echojournal.ui.theme.EchoJournalTheme

@Composable
fun EntriesListDayView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(
            top = 16.dp
        )
    ) {
        Text(
            "TODAY",
            fontWeight = FontWeight.W500,
            fontSize = 12.sp
        )

        LazyColumn(
        ) {
            items(3) { it ->
                EntryItem(
                    index = it
                )
            }
        }


    }
}

@Preview
@Composable
fun EntriesListDayViewPreview() {
    EchoJournalTheme {
        Surface {
            EntriesListDayView()
        }

    }
}