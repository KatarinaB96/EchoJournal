package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.core.domain.models.Topic
import com.campus.echojournal.ui.theme.Gray6
import com.campus.echojournal.ui.theme.OnErrorContainer
import com.campus.echojournal.ui.theme.OnSurfaceVariant
import com.campus.echojournal.ui.theme.OutlineVariant
import com.campus.echojournal.ui.theme.Primary
import com.campus.echojournal.ui.theme.Secondary

@Composable
fun TopicPicker(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    topics: List<Topic>,
    onAddTopicToList: (String) -> Unit,
    onRemoveTopicFromList: (String) -> Unit
) {
    val suggestions by remember { mutableStateOf(setOf("Work", "Hobby", "Personal", "Office", "Family", "Friends", "Workout")) }

    Column(
        modifier = Modifier
            .wrapContentHeight()
    ) {
        TopicsList(
            selectedTopics = topics,
            searchQuery = searchQuery,
            onSearchQueryChange = { onSearchQueryChange(it) },
            onDelete = { topic ->
                onRemoveTopicFromList(topic)
            }
        )

        if (searchQuery.isNotEmpty()) {
            TopicDropdown(
                selectedTopics = topics,
                onAddTopicToList = { newTopicName ->
                    onAddTopicToList(newTopicName)
                },
                modifier = Modifier,
                searchQuery = searchQuery,
                onSearchQueryChange = { onSearchQueryChange(it) },
                suggestedTopics = suggestions
            )
        }
    }
}

@Composable
private fun TopicDropdown(
    selectedTopics: List<Topic>,
    onAddTopicToList: (String) -> Unit,
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    suggestedTopics: Set<String>
) {
    // Filter out already selected topics from the suggestions
    val filteredSuggestedTopics = suggestedTopics.filter { name ->
        !selectedTopics.map { it.name }.contains(name)
    }

    // Check if the search query matches any selected topic
    val isDuplicate = selectedTopics.any { it.name.equals(searchQuery, ignoreCase = true) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .offset(y = -(8.dp))
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            // Show warning message if search query is a duplicate
            if (isDuplicate) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_hashtag),
                        contentDescription = stringResource(R.string.icon_hashtag),
                        tint = OnErrorContainer
                    )

                    Text(
                        text = "'$searchQuery' already exist",
                        style = MaterialTheme.typography.labelSmall,
                        color = OnErrorContainer
                    )
                }
            } else {
                val matchingSuggestedTopics = filteredSuggestedTopics.filter { name ->
                    name.startsWith(prefix = searchQuery, ignoreCase = true)
                }

                matchingSuggestedTopics.forEach { topic ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    onAddTopicToList(topic)
                                    onSearchQueryChange("")
                                }
                            )
                            .padding(horizontal = 6.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_hashtag),
                            contentDescription = stringResource(R.string.icon_hashtag),
                            tint = Primary
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            text = topic,
                            style = MaterialTheme.typography.labelSmall,
                            color = Secondary
                        )
                    }
                }

                // Option to create a new topic if no matching suggestion exists
                if (!isDuplicate && matchingSuggestedTopics.isEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val newTopic = searchQuery.trim()
                                onAddTopicToList(newTopic)
                                onSearchQueryChange("")
                            }
                            .padding(horizontal = 2.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            modifier = Modifier.size(18.dp),
                            contentDescription = stringResource(R.string.add_topic),
                            tint = Primary,
                        )

                        Text(
                            text = "Create '$searchQuery'",
                            style = MaterialTheme.typography.labelSmall,
                            color = Primary,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TopicsList(
    selectedTopics: List<Topic>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onDelete: (String) -> Unit,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        // Display selected topics as chips
        selectedTopics.forEach { topic ->
            TopicChip(
                modifier = Modifier.padding(end = 4.dp),
                topic = topic,
                onDelete = { onDelete(it) }
            )
        }

        // Search query input field
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_hashtag),
                contentDescription = stringResource(R.string.icon_hashtag),
                tint = OutlineVariant,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(6.dp))
            Box {
                if (searchQuery.isEmpty()) {
                    Text(
                        text = "Topic",
                        color = OutlineVariant,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { onSearchQueryChange(it) },
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun TopicChip(
    modifier: Modifier,
    topic: Topic,
    onDelete: (String) -> Unit = {},
) {
    InputChip(
        modifier = modifier,
        colors = InputChipDefaults.inputChipColors(
            containerColor = Gray6,
            labelColor = OnSurfaceVariant,
            selectedContainerColor = Gray6
        ),
        shape = RoundedCornerShape(100.dp),
        onClick = {
            onDelete(topic.name)
        },
        label = {
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = topic.name
            )
        },
        selected = true,
        avatar = {
            Icon(
                painter = painterResource(R.drawable.ic_hashtag),
                contentDescription = stringResource(R.string.icon_hashtag),
                tint = OnSurfaceVariant.copy(alpha = 0.5f)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.remove_tag),
                tint = OnSurfaceVariant.copy(alpha = 0.3f),
                modifier = Modifier.clickable { onDelete(topic.name) },
            )
        },
    )
}

@Composable
@Preview
fun FilterChipDropdownPreview() {
    MaterialTheme {
        TopicPicker("Journal", {}, emptyList(), {}, {})
    }
}
