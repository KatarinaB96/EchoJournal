package com.campus.echojournal.entries.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.campus.echojournal.R
import com.campus.echojournal.core.domain.models.Topic
import com.campus.echojournal.entries.presentation.components.AudioWave
import com.campus.echojournal.entries.presentation.components.TopicPicker
import com.campus.echojournal.entries.util.allMoodsList
import com.campus.echojournal.settings.presentation.components.SelectableMood
import com.campus.echojournal.ui.theme.Background
import com.campus.echojournal.ui.theme.BlueGradient1
import com.campus.echojournal.ui.theme.ErrorContainer
import com.campus.echojournal.ui.theme.InverseOnSurface
import com.campus.echojournal.ui.theme.OnErrorContainer
import com.campus.echojournal.ui.theme.OnPrimary
import com.campus.echojournal.ui.theme.OnSurface
import com.campus.echojournal.ui.theme.Outline
import com.campus.echojournal.ui.theme.OutlineVariant
import com.campus.echojournal.ui.theme.Primary
import com.campus.echojournal.ui.theme.PrimaryContainer
import com.campus.echojournal.ui.theme.Secondary70
import com.campus.echojournal.ui.theme.Secondary95
import com.campus.echojournal.ui.theme.SurfaceVariant
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewEntryScreenRoot(
    path: String,
    viewModel: NewEntryViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NewEntryScreen(
        onBackClick,
        path = path,
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntryScreen(
    onBackClick: () -> Unit,
    path: String,
    state: EntryState,
    onAction: (NewEntryAction) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var moodIndex by remember { mutableStateOf(-1) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var isSheetOpen by remember { mutableStateOf(false) }
    var selectedTopics = remember { mutableStateListOf<Topic>() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background),
                title = { Text(text = stringResource(R.string.new_entry), color = OnSurface) },
                navigationIcon = {
                    IconButton(onClick = {
                        onAction(NewEntryAction.OnCancel)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            AddTitleField(
                text = title,
                moodIndex = moodIndex,
                onValueChange = { title = it },
                onLeadingIconClick = { isSheetOpen = true },
                focusRequester = focusRequester,
                keyboardController = keyboardController
            )

            AudioWave(
                audioDuration = 0,
                moodIndex = moodIndex,
                amplitudes = listOf(),


            )
            Spacer(Modifier.height(16.dp))
            TopicPicker(
                defaultTopics = state.defaultTopics,
                onSaveTopics = {
                    selectedTopics.addAll(it)
                }
            )
            Spacer(Modifier.height(16.dp))
            DescriptionTextField(description, onSearchQueryChange = {
                description = it
            })
            Spacer(modifier = Modifier.weight(1f))

            ButtonRow(
                text = stringResource(R.string.save),
                onCancelClick = {
                    onAction(NewEntryAction.OnCancel)
                },
                onButtonClick = {
                    onAction(
                        NewEntryAction.OnAddNewEntry(
                            title = title,
                            moodIndex = moodIndex,
                            recordingPath = path,
                            topics = selectedTopics,
                            description = description,

                        )
                    )
                },
                isButtonEnabled = title.isNotEmpty() && moodIndex != -1,
                showIcon = false
            )

            if (state.showBackConfirmationDialog) {
                ConfirmExitDialog(onAction, onBackClick)
            }
        }
    }

    if (isSheetOpen) {
        BottomSheet(
            moodIndexFromSettings = state.savedMoodIndex,
            onDismiss = { isSheetOpen = false },
            onCancelClick = { isSheetOpen = false },
            onConfirmClick = {
                moodIndex = it
                isSheetOpen = false
            }
        )
    }

}

@Composable
fun ConfirmExitDialog(onAction: (NewEntryAction) -> Unit, onBackClick: () -> Unit) {
    AlertDialog(
        containerColor = ErrorContainer,
        onDismissRequest = {
            onAction(NewEntryAction.OnDismissDialog)
        },
        title = { Text(stringResource(R.string.confirm_exit), style = MaterialTheme.typography.titleMedium, color = OnErrorContainer) },
        text = { Text(stringResource(R.string.dialog_subtitle), style = MaterialTheme.typography.labelSmall, color = OnSurface) },
        confirmButton = {
            Box(
                modifier = Modifier
                    .width(101.dp)
            ) {
                GradientButton(
                    text = stringResource(R.string.exit),
                    isEnabled = true,
                    onClick = {
                        onAction(NewEntryAction.OnDismissDialog)
                        onBackClick()
                    },
                    showIcon = false
                )
            }
        },
        dismissButton = {
            CancelButton(onClick =
            {
                onAction(NewEntryAction.OnDismissDialog)
            })
        }
    )
}

@Composable
fun AddTitleField(
    text: String,
    moodIndex: Int,
    onValueChange: (String) -> Unit,
    onLeadingIconClick: () -> Unit,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?
) {
    val moods = allMoodsList.map { it.first }

    TextField(
        leadingIcon = {
            if (moodIndex != -1) {
                Image(
                    painter = painterResource(
                        moods[moodIndex]
                    ),
                    contentDescription = "Mood $moodIndex",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onLeadingIconClick()
                        }
                )
            } else {
                IconButton(
                    onClick = onLeadingIconClick,
                    modifier = Modifier
                        .background(color = Secondary95, shape = CircleShape)
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Secondary70
                    )
                }
            }
        },
        value = text,
        textStyle = MaterialTheme.typography.headlineLarge,
        onValueChange = onValueChange,
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
            unfocusedTextColor = OnSurface,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .wrapContentWidth()
            .padding(top = 16.dp)
            .focusRequester(focusRequester),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
    )
}

@Composable
fun ButtonRow(text: String, onCancelClick: () -> Unit, onButtonClick: () -> Unit, isButtonEnabled: Boolean, showIcon: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CancelButton(onClick = onCancelClick)
        GradientButton(
            text = text,
            isEnabled = isButtonEnabled,
            onClick = onButtonClick,
            showIcon = showIcon
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    moodIndexFromSettings: Int,
    onDismiss: () -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: (Int) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(true)
    var moodIndexPicked by remember { mutableStateOf(moodIndexFromSettings) }
    var isMoodSelected by remember { mutableStateOf(moodIndexPicked != -1) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.how_are_you_doing),
                textAlign = TextAlign.Center,
                color = OnSurface,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(32.dp))
            SelectableMood(
                savedMoodIndex = moodIndexPicked,
                onMoodSelected = { index ->
                    moodIndexPicked = index
                    isMoodSelected = true
                })
            Spacer(Modifier.height(24.dp))
            ButtonRow(
                text = "Confirm",
                onCancelClick = onCancelClick,
                onButtonClick = { onConfirmClick(moodIndexPicked) },
                isButtonEnabled = isMoodSelected,
                showIcon = true
            )
        }
    }
}

@Composable
fun CancelButton(onClick: () -> Unit) {
    FilledTonalButton(
        modifier = Modifier
            .width(101.dp)
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(containerColor = InverseOnSurface),
        onClick = onClick
    ) {
        Text(
            stringResource(R.string.cancel),
            color = Primary,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun GradientButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    text: String,
    showIcon: Boolean = false
) {
    val gradient = Brush.linearGradient(colors = listOf(BlueGradient1, PrimaryContainer))
    val disabledButtonColor = Brush.linearGradient(colors = listOf(SurfaceVariant, SurfaceVariant))
    Button(
        enabled = isEnabled,
        modifier = Modifier
            .width(263.dp)
            .height(44.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = SurfaceVariant,
            containerColor = Color.Transparent
        ),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isEnabled) gradient else disabledButtonColor),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (showIcon) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.checkmark),
                        tint = if (isEnabled) OnPrimary else Outline,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    color = if (isEnabled) OnPrimary else Outline,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun GradientButtonPreview() {
    GradientButton(
        text = "Save",
        isEnabled = false,
        showIcon = false,
        onClick = {}
    )
}

@Preview
@Composable
fun GradientButtonConfirmPreview() {
    GradientButton(
        text = "Confirm",
        isEnabled = true,
        showIcon = true,
        onClick = {}
    )
}

@Composable
fun DescriptionTextField(
    text: String, onSearchQueryChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_pen),
            contentDescription = stringResource(R.string.icon_pen),
            tint = OutlineVariant,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(6.dp))
        Box {
            if (text.isEmpty()) {
                Text(
                    text = "Description",
                    color = OutlineVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            BasicTextField(
                value = text,
                onValueChange = {
                    onSearchQueryChange(it)
                },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
@Preview
fun AlertDialogPreview() {
    ConfirmExitDialog({}, {})
}

