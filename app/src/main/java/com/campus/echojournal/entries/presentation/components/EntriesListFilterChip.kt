package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.ui.theme.EchoJournalTheme

@Composable
fun EntriesListFilterChip(
    title: String,
    selectedList: List<Pair<Int, String>> = emptyList(),
    onClick: () -> Unit = {},
    isActive: Boolean = false,
    modifier: Modifier = Modifier,
    showIcons: Boolean = true
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
                if (showIcons) {
                    Image(
                        painter = painterResource(id = selectedList[0].first),
                        contentDescription = selectedList[0].second,
                    )
                    Text(
                        modifier = Modifier.padding(
                            start = 8.dp
                        ),
                        text = selectedList.joinToString { it.second },
                    )
                } else {
                    Text(
                        text = selectedList.joinToString { it.second },
                    )
                }
            }

            2 -> {
                if (showIcons) {
                    Row {
                        for (i in selectedList.indices) {
                            Image(
                                modifier = Modifier
                                    .offset(x = if (i != 0) (-15 * i).dp else 0.dp),
                                painter = painterResource(id = selectedList[i].first),
                                contentDescription = selectedList[i].second,
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .offset(x = (-15 * (selectedList.size - 1)).dp)
                            .padding(
                                start = 8.dp
                            ),
                        text = selectedList.joinToString { it.second },
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = selectedList.joinToString { it.second },
                    )
                }


            }

            else -> {
                if (showIcons) {
                    Row {
                        for (i in 0..1) {
                            Image(
                                modifier = Modifier
                                    .offset(x = if (i != 0) (-15 * i).dp else 0.dp),
                                painter = painterResource(id = selectedList[i].first),
                                contentDescription = selectedList[i].second,
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .offset(x = (-15).dp)
                            .padding(
                                start = 8.dp
                            ),
                        text = selectedList.joinToString(limit = 2, truncated = "") { it.second },
                    )
                    Text(
                        modifier = Modifier
                            .offset(x = (-15).dp),
                        text = "+${selectedList.size - 2}",
                    )
                } else {
                    Text(
                        modifier = Modifier

                            .padding(
                                start = 8.dp
                            ),
                        text = selectedList.joinToString(limit = 2, truncated = "") { it.second },
                    )
                    Text(

                        text = "+${selectedList.size - 2}",
                    )
                }


            }
        }
    }

}

@Preview
@Composable
private fun EntriesListFilterChipPreview() {
    EchoJournalTheme {
        Surface {
            EntriesListFilterChip(title = "All Moods")

        }
    }
}