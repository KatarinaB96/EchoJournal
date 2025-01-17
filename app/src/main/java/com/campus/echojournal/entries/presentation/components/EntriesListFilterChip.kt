package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.ui.theme.EchoJournalTheme

@Composable
fun EntriesListFilterChip(
    title: String,
    onClick: () -> Unit = {},
    isActive : Boolean = false,
    modifier: Modifier = Modifier
) {
    Text(
        title,
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(50.dp)
            ).background(
                if(isActive) Color.White else Color.Transparent
            )
            .clickable {
                onClick()
            }
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)

    )
}

@Preview
@Composable
private fun EntriesListFilterChipPreview() {
    EchoJournalTheme {
        EntriesListFilterChip(title = "All Moods")
    }
}