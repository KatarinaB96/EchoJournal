package com.campus.echojournal.entries.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.entries.presentation.components.AudioWave
import com.campus.echojournal.ui.theme.OnSurface
import com.campus.echojournal.ui.theme.OutlineVariant
import com.campus.echojournal.ui.theme.Secondary70
import com.campus.echojournal.ui.theme.Secondary95

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryScreen() {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var textTopic by remember { mutableStateOf("") }
    var textDescription by remember { mutableStateOf("") }
    val suggestions by remember { mutableStateOf(setOf("Work", "Hobby", "Personal", "Office", "Family", "Friends", "Workout")) }
    var showWarning by remember { mutableStateOf(false) }
    var isAddingTopic by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.new_entry), color = OnSurface)
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
        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                leadingIcon = {
                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .background(
                                color = Secondary95,
                                shape = CircleShape
                            )
                            .size(32.dp),
                        content = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Secondary70,
                            )
                        }
                    )
                },
                value = text,
                onValueChange = {
                    text = it
                },
                placeholder = {
                    Text(
                        stringResource(R.string.add_title),
                        style = MaterialTheme.typography.headlineLarge,
                        color = OutlineVariant
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedLabelColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedTextColor = OnSurface,
                    unfocusedTextColor = OutlineVariant,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 16.dp)
                    .focusRequester(focusRequester),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            )

            AudioWave()
            Spacer(Modifier.height(16.dp))
            TextFieldWithIcon(textTopic, "Topic", R.drawable.ic_hashtag) { TODO() }
            Spacer(Modifier.height(16.dp))
            TextFieldWithIcon(textDescription, "Add Description...", R.drawable.ic_pen) { TODO() }
        }

    }

}

@Composable
@Preview
fun NewEntryScreenPreview() {
    NewEntryScreen()
}

@Composable
fun TextFieldWithIcon(text: String, hintText: String, iconImage: Int, onTextChange: (String) -> Unit) {
    TextField(
        value = text,
        leadingIcon = {
            Icon(
                painter = painterResource(iconImage),
                contentDescription = stringResource(R.string.icon_hashtag),
                tint = OutlineVariant,
                modifier = Modifier.size(24.dp)
            )
        },
        onValueChange = {
            //            onTextChange(it)
        },
        placeholder = {
            Text(
                text = hintText,
                style = MaterialTheme.typography.bodyMedium,
                color = OutlineVariant
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedLabelColor = Color.Transparent,
            focusedLabelColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedTextColor = OnSurface,
            focusedLeadingIconColor = Color.Yellow,
            unfocusedLeadingIconColor = Color.Yellow,
            unfocusedTextColor = OutlineVariant,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .wrapContentWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldWithIconPreview() {
    TextFieldWithIcon("", "Topic", R.drawable.ic_hashtag, {})
}
