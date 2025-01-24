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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.LineColor

@Composable
fun EntryItem(
    onClickPlay : (Int) -> Unit = {},
    onClickPause : (Int) -> Unit = {},
    onClickResume : (Int) -> Unit = {},
    index: Int = 0,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()
    Row(modifier = modifier.height(IntrinsicSize.Max)) {
        Box(
            contentAlignment = Alignment.TopCenter,
        ) {

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(if (index == 2) 0.1f else 1f)
                    .padding(top = if (index == 0) 20.dp else 0.dp)
                    .background(LineColor)
            )

            Image(
                modifier = Modifier.padding(top = 16.dp),
                painter = painterResource(id = R.drawable.mood_neutral_active_on),
                contentDescription = "Mood Neutral Active On"
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
                        "My Entry", fontWeight = FontWeight.W500
                    )
                    Text("19:42")

                }
                AudioWave(
                    onClickPlay = onClickPlay,
                    onClickPause = onClickPause,
                    onClickResume = onClickResume,
                    index = index
                )
                Text(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tit amet, consectetur adipiscing elit, sed tdeades,Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tit amet, consectetur adipiscing elit, sed ",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Row(
                    modifier = Modifier.horizontalScroll(
                        scrollState
                    )
                ) {
                    repeat(
                        4
                    ) {
                        EntriesTopicChip(
                            modifier = Modifier.padding(2.dp),
                            title = "Work"
                        )

                    }
                }

            }
        }

    }


}

@Preview
@Composable
private fun EntryItemPreview() {
    EchoJournalTheme {
        EntryItem()
    }
}