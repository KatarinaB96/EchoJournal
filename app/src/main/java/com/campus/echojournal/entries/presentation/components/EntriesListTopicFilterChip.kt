package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.core.domain.models.Topic
import com.campus.echojournal.ui.theme.EchoJournalTheme

@Composable
fun EntriesListTopicFilterChip(
    title: String,
    selectedList: List<Topic> = emptyList(),
    onClick: () -> Unit = {},
    isActive: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .clip(
            shape = RoundedCornerShape(50.dp)
        )
        .background(
            if (isActive || selectedList.isNotEmpty()) Color.White else Color.Transparent
        )
        .clickable {
            onClick()
        }
        .border(
            width = 1.dp,
            color = Color.Gray,
            shape = RoundedCornerShape(50.dp)
        )
        .padding(horizontal = 12.dp, vertical = 6.dp),

        verticalAlignment = Alignment.CenterVertically)
    {

        when (selectedList.size) {
            0 -> {
                Text(
                    text = title
                )
            }

            1 -> {
                Text(
                    text = selectedList[0].name,
                )
            }

            2 -> {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = selectedList.joinToString { it.name },
                )
            }

            else -> {

                    Text(
                        modifier = Modifier

                            .padding(
                                start = 8.dp
                            ),
                        text = selectedList.joinToString(
                            limit = 2,
                            truncated = ""
                        ) { it.name },
                    )
                    Text(

                        text = "+${selectedList.size - 2}",
                    )
                }



        }
    }

}

@Preview
@Composable
private fun EntriesListFilterChipPreview() {
    EchoJournalTheme {
        Surface {
            EntriesListMoodFilterChip(title = "All Moods")

        }
    }
}