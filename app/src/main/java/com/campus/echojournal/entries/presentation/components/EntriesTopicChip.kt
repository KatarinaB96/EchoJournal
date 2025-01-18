package com.campus.echojournal.entries.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.ui.theme.EchoJournalTheme

@Composable
fun EntriesTopicChip(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "#$title",
        modifier = modifier
            .clip(
                RoundedCornerShape(50)
            )
            .background(Color.Gray).padding(4.dp)

    )
}

@Preview
@Composable
private fun EntriesTopicChipPreview() {
    EchoJournalTheme {
        Surface {
            EntriesTopicChip(title = "Topic")
        }
    }

}