package com.campus.echojournal.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.core.domain.models.Topic
import com.campus.echojournal.settings.presentation.SettingsAction
import com.campus.echojournal.settings.presentation.SettingsState
import com.campus.echojournal.ui.theme.Gray6
import com.campus.echojournal.ui.theme.OnErrorContainer
import com.campus.echojournal.ui.theme.OnSurface
import com.campus.echojournal.ui.theme.OnSurfaceVariant
import com.campus.echojournal.ui.theme.Primary
import com.campus.echojournal.ui.theme.Secondary

@Composable
fun FilterChipDropdown(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val suggestions by remember { mutableStateOf(setOf("Work", "Hobby", "Personal", "Office", "Family", "Friends", "Workout")) }
    var isAddingTopic by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var showWarning by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        TopicsList(
            modifier = modifier,
            selectedTopics = state.topics,
            isAddingTopic = isAddingTopic,
            onAddingTopicChange = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            keyboardController = keyboardController,
            focusRequester = focusRequester,
            showWarning = { showWarning = it },
            onDelete = {
                onAction(SettingsAction.OnDeleteTopic(it))
            }
        )

        if (searchQuery.isEmpty()) return

        TopicDropdown(
            onSaveTopics = {
                onAction(SettingsAction.OnAddTopic(it))
            },
            selectedTopics = state.topics,
            modifier = modifier,
            onAddingTopicChange = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            suggestedTopics = suggestions,
            showWarning = showWarning
        )
    }
}

@Composable
fun TopicDropdown(
    selectedTopics: List<Topic>,
    onSaveTopics: (String) -> Unit,
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
            .padding(vertical = 2.dp, horizontal = 30.dp)
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

                // Show matching saved topics first
                matchingSavedTopics.forEach { topic ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    onSaveTopics(topic)
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
                                onSaveTopics(newTopic)
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

@Composable
private fun TopicsList(
    modifier: Modifier = Modifier,
    selectedTopics: List<Topic>,
    isAddingTopic: Boolean,
    onAddingTopicChange: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    showWarning: (Boolean) -> Unit,
    onDelete: (Int) -> Unit
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp),
                spotColor = Color(0xFF474F60).copy(alpha = 0.08f)
            ),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            TitleAndSubtitleText()

            ChipFlowRow(
                selectedTopics = selectedTopics,
                isAddingTopic = isAddingTopic,
                onAddingTopicChange = onAddingTopicChange,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                keyboardController = keyboardController,
                focusRequester = focusRequester,
                showWarning = showWarning,
                onDelete = onDelete
            )
        }
    }
}

@Composable
private fun TitleAndSubtitleText() {
    Column {
        Text(
            text = stringResource(R.string.my_topics),
            style = MaterialTheme.typography.headlineSmall,
            color = OnSurface,
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = stringResource(R.string.select_default_topics),
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipFlowRow(
    modifier: Modifier = Modifier,
    selectedTopics: List<Topic>,
    isAddingTopic: Boolean,
    onAddingTopicChange: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    showWarning: (Boolean) -> Unit,
    onDelete: (Int) -> Unit
) {

    LaunchedEffect(isAddingTopic) {
        if (isAddingTopic.not()) return@LaunchedEffect
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    // Selected Topics
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {

        selectedTopics.forEach { topic ->
            TopicChip(
                modifier = Modifier.padding(end = 4.dp),
                topic = topic,
                onDelete = {
                    onDelete(it)
                }
            )
        }

        if (isAddingTopic.not()) {
            IconButton(
                onClick = { onAddingTopicChange(true) },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(color = Gray6, shape = CircleShape)
                    .size(32.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                    )
                }
            )
        } else {
            BasicTextField(
                value = searchQuery,
                onValueChange = { onSearchQueryChange(it) },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 16.dp)
                    .focusRequester(focusRequester),
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
                            onAddingTopicChange(false)
                            showWarning(false)
                        }
                        keyboardController?.hide()
                    }
                )
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
                tint = OnSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.clickable { onDelete(topic.id) },
            )
        },
    )
}

@Composable
@Preview
fun FilterChipDropdownPreview() {
    FilterChipDropdown(SettingsState(), {}, Modifier)
}
