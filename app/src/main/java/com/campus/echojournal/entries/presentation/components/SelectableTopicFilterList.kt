package com.campus.echojournal.entries.presentation.components

import com.campus.echojournal.core.domain.models.Topic
import com.campus.echojournal.entries.util.allTopicsList
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.SurfaceColor

@Composable
fun SelectableTopicFilterList(
    onClick: (Topic) -> Unit,
    selectedItemList: List<Topic>,
    itemList: List<Topic>,
    isVisible: Boolean = false,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(
            initialOffsetY = { -it }
        ),
        exit = fadeOut() + slideOutVertically {
            -it
        }
    ) {
        Card(
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.Gray

            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .shadow(
                    elevation = 8.dp,
                    spotColor = Color.Black,
                    ambientColor = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )


        ) {
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {

                itemList.forEachIndexed { index, topic ->

                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (selectedItemList.contains(topic))
                                    SurfaceColor.copy(alpha = 0.05f) else Color.Transparent
                            )
                            .clickable {
                                onClick(
                                    topic
                                )
                            }
                            .padding(vertical = 6.dp, horizontal = 3.dp)

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_hashtag),
                                contentDescription = topic.name,
                            )
                            Text(
                                text = topic.name,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        if (selectedItemList.contains(topic)) {
                            Image(
                                modifier = Modifier.padding(end = 8.dp),
                                painter = painterResource(id = R.drawable.ic_check),
                                contentDescription = "Check",
                            )
                        }
                    }
                }
            }
        }
    }

}


@Preview
@Composable
private fun SelectableTopicFilterListPreview() {
    EchoJournalTheme {
        SelectableTopicFilterList(
            isVisible = true,
            onClick = {},
            selectedItemList = listOf(Topic(1,"Topic 1", false)),
            itemList = allTopicsList
        )
    }
}