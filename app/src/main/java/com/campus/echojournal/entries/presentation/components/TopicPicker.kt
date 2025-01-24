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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    defaultTopics: List<Topic>,
    onSaveTopics: (List<Topic>) -> Unit,
) {
    val suggestions by remember { mutableStateOf(setOf("Work", "Hobby", "Personal", "Office", "Family", "Friends", "Workout")) }
    var isAddingTopic by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showWarning by remember { mutableStateOf(false) }

    val selectedTopics = remember { mutableStateListOf<Topic>().apply { addAll(defaultTopics) } }


    Column(
        modifier = Modifier
            .wrapContentHeight()
    ) {

        TopicsList(
            selectedTopics = selectedTopics,
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            showWarning = { showWarning = it },
            onDelete = {topicId ->
                selectedTopics.removeIf { it.id == topicId }
                onSaveTopics(selectedTopics)
            }
        )

        if (searchQuery.isEmpty()) return

        TopicDropdown(
            selectedTopics = selectedTopics,
            onAddTopicToList = { newTopicName ->
                val newTopic = Topic(id = selectedTopics.size + 1, name = newTopicName, false)
                selectedTopics.add(newTopic)
                onSaveTopics(selectedTopics)
            },
            modifier = Modifier,
            onAddingTopicChange = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            suggestedTopics = suggestions,
            showWarning = showWarning
        )
    }
}

@Composable
private fun TopicDropdown(
    selectedTopics: List<Topic>,
    onAddTopicToList: (String) -> Unit,
    modifier: Modifier = Modifier,
    onAddingTopicChange: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    suggestedTopics: Set<String>,
    showWarning: Boolean
) {

    val filterSuggestedTopics = suggestedTopics.filter { name ->
        !selectedTopics.map { it.name }.contains(name)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .offset(y = -(8.dp))
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp)),
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            if (showWarning) {
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

                val matchingSavedTopics = filterSuggestedTopics.filter { name ->
                    name.startsWith(prefix = searchQuery, ignoreCase = true)
                }

                matchingSavedTopics.forEach { topic ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    onAddTopicToList(topic)
                                    onSearchQueryChange("")
                                    onAddingTopicChange(false)
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

                        Spacer(Modifier.width(4.dp))

                        Text(
                            text = topic,
                            style = MaterialTheme.typography.labelSmall,
                            color = Secondary
                        )
                    }
                }

                if (!suggestedTopics.any { it.equals(searchQuery, ignoreCase = true) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val newTopic = searchQuery.trim()
                                onAddTopicToList(newTopic)
                                onSearchQueryChange("")
                                onAddingTopicChange(false)
                            }
                            .padding(horizontal = 2.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            modifier = Modifier.size(18.dp),
                            contentDescription = stringResource(R.string.add_topic),
                            tint = MaterialTheme.colorScheme.primary,
                        )

                        Text(
                            text = "Create '$searchQuery'",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
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
    showWarning: (Boolean) -> Unit,
    onDelete: (Int) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (selectedTopics.map { it.name }.contains(searchQuery)) {
                                showWarning(true)
                            } else {
                                showWarning(false)
                            }
                        }
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium
                )
            }
        }

        selectedTopics.forEach { topic ->
            TopicChip(
                modifier = Modifier.padding(end = 4.dp),
                topic = topic,
                onDelete = {
                    onDelete(it)
                }
            )
        }

    }
}

@Composable
fun TopicChip(
    modifier: Modifier,
    topic: Topic,
    onDelete: (Int) -> Unit = {},
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
            onDelete(topic.id)
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
                modifier = Modifier.clickable { onDelete(topic.id) },
            )
        },
    )
}

@Composable
@Preview
fun FilterChipDropdownPreview() {
    MaterialTheme {
        //        TopicPicker()
    }
}
