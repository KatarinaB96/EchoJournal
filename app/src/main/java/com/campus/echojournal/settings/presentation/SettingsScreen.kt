package com.campus.echojournal.settings.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.ui.theme.Background
import com.campus.echojournal.ui.theme.Gray6
import com.campus.echojournal.ui.theme.OnSurface
import com.campus.echojournal.ui.theme.OnSurfaceVariant
import com.campus.echojournal.ui.theme.Primary
import com.campus.echojournal.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(text = "Settings", color = OnSurface)
            },
            navigationIcon = {
                IconButton(onClick = {

                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = stringResource(R.string.back)
                    )

                }
            }
        )
    }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            MoodCard()
            Spacer(Modifier.height(16.dp))
            TopicsCard()
        }
    }
}

@Composable
fun MoodCard() {
    Card(
        modifier = Modifier
            .height(148.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        Column(
            modifier = Modifier.padding(
                bottom = 12.dp,
                top = 14.dp,
                start = 14.dp,
                end = 14.dp
            )
        ) {
            Text(
                stringResource(R.string.my_mood), color = OnSurface,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(2.dp))
            Text(
                stringResource(R.string.select_default_mood),
                color = OnSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(14.dp))
            SelectableMood()
        }
    }
}

@Composable
fun TopicsCard() {
    var isTagInputVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        Column(
            modifier = Modifier.padding(
                bottom = 12.dp,
                top = 14.dp,
                start = 14.dp,
                end = 14.dp
            )
        ) {
            Text(
                stringResource(R.string.my_topics), color = OnSurface,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(2.dp))

            Text(
                text = stringResource(R.string.select_default_topics),
                color = OnSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(14.dp))

            if (!isTagInputVisible) {
                OutlinedButton(
                    onClick = {
                        isTagInputVisible = true
                    },
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, Gray6),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Gray6, containerColor = Gray6)
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_topic), tint = OnSurfaceVariant)
                }
            } else {
                TagInputWithSuggestions(
                    onClose = { isTagInputVisible = false }
                )
            }
        }
    }
}

@Composable
fun SelectableMood() {
    var selectedImageIndex by remember { mutableStateOf(-1) }
    val moods = listOf(
        Pair(R.drawable.mood_stresses_active_off to R.drawable.mood_stresses_active, "Stressed"),
        Pair(R.drawable.mood_sad_active_off to R.drawable.mood_sad_active_on, "Sad"),
        Pair(R.drawable.mood_neutral_active_off to R.drawable.mood_neutral_active_on, "Neutral"),
        Pair(R.drawable.mood_peaceful_active_off to R.drawable.mood_peaceful_active_on, "Peaceful"),
        Pair(R.drawable.mood_excited_active_off to R.drawable.mood_excited_active_on, "Excited")
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        moods.forEachIndexed { index, (images, moodName) ->
            val (emptyImage, filledImage) = images

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { selectedImageIndex = index }
            ) {
                Image(
                    painter = painterResource(
                        id = if (index == selectedImageIndex) filledImage else emptyImage
                    ),
                    contentDescription = "Mood $moodName",
                    modifier = Modifier
                        .size(40.dp)
                )
                Text(
                    text = moodName,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (index == selectedImageIndex) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagInputWithSuggestions(onClose: () -> Unit) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(key1 = true) { focusRequester.requestFocus() }

    var inputText by remember { mutableStateOf("") }
    var selectedTags by remember { mutableStateOf(listOf<String>()) }

    val suggestions = listOf("Work", "Love", "Friendship", "Nature", "Relaxation", "Jack", "Jared", "Jane", "James", "Jasmine")
    val filteredSuggestions = suggestions.filter { it.startsWith(inputText, ignoreCase = true) && it !in selectedTags }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        selectedTags.forEach { tag ->
            Chip(text = tag) {
                selectedTags = selectedTags - tag
            }
        }

        BasicTextField(
            value = inputText,
            onValueChange = {
                inputText = it
            },
            singleLine = true,
            modifier = Modifier
                .height(32.dp)
                .focusRequester(focusRequester)
        ) {
            Box(
                modifier = Modifier
                    .widthIn(min = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = inputText,
                    style = MaterialTheme.typography.labelSmall,
                    color = OnSurface,
                    modifier = Modifier
                        .widthIn(min = 20.dp)
                )
            }
        }
    }

    if (inputText.isNotEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Background),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                if (filteredSuggestions.isNotEmpty()) {
                    items(filteredSuggestions) { suggestion ->
                        TagRow(
                            text = suggestion,
                            icon = painterResource(id = R.drawable.ic_hashtag),
                            iconDescription = stringResource(R.string.icon_hashtag),
                            iconTint = Primary.copy(alpha = 0.5f)
                        ) {
                            selectedTags = selectedTags + suggestion
                            inputText = ""
                        }
                    }
                }
                item {
                    TagRow(
                        text = "Create '$inputText'",
                        icon = Icons.Default.Add,
                        iconDescription = stringResource(R.string.add_topic),
                        iconTint = Primary
                    ) {
                        selectedTags = selectedTags + inputText
                        inputText = ""
                    }
                }
            }
        }
    }
}


@Composable
fun TagRow(
    text: String,
    icon: Any,
    iconDescription: String,
    iconTint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon is Painter) {
            Icon(
                painter = icon,
                contentDescription = iconDescription,
                tint = iconTint
            )
        } else if (icon is ImageVector) {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription,
                tint = iconTint
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Secondary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun Chip(
    text: String,
    onDismiss: () -> Unit,
) {
    InputChip(
        colors = InputChipDefaults.inputChipColors(
            containerColor = Gray6,
            labelColor = OnSurfaceVariant,
            selectedContainerColor = Gray6
        ),
        shape = RoundedCornerShape(100.dp),
        onClick = {
            onDismiss()
        },
        label = {
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = text
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
                Icons.Default.Close,
                contentDescription = stringResource(R.string.remove_tag),
                tint = OnSurfaceVariant.copy(alpha = 0.5f)
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}

@Preview(showBackground = true)
@Composable
fun TagInputWithSuggestionsPreview() {
    TagInputWithSuggestions({})
}

@Preview(showBackground = true)
@Composable
fun InputChipExamplePreview() {
    Chip("Hello", {})
}