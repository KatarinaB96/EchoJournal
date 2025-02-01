package com.campus.echojournal.entries.presentation.entries_list


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.entries.presentation.components.BottomSheetContent
import com.campus.echojournal.entries.presentation.components.EchoFloatingActionButton
import com.campus.echojournal.entries.presentation.components.EntriesListDayView
import com.campus.echojournal.entries.presentation.components.EntriesListMoodFilterChip
import com.campus.echojournal.entries.presentation.components.EntriesListTopAppBar
import com.campus.echojournal.entries.presentation.components.EntriesListTopicFilterChip
import com.campus.echojournal.entries.presentation.components.NoEntries
import com.campus.echojournal.entries.presentation.components.SelectableMoodFilterList
import com.campus.echojournal.entries.presentation.components.SelectableTopicFilterList
import com.campus.echojournal.entries.util.allMoodsList
import com.campus.echojournal.ui.ObserveAsEvents
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.GradientColor1
import com.campus.echojournal.ui.theme.GradientColor2
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun EntriesListScreenRoot(
    onSettingsClick: () -> Unit,
    onNavigateAddEntryScreen : (String) -> Unit,
    viewModel: EntriesViewModel = org.koin.androidx.compose.koinViewModel()

) {

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is EntriesEvent.OnSavedAudio -> {
                viewModel.onAction(EntriesAction.OnDismissRecordAudioBottomSheet)

                onNavigateAddEntryScreen(event.audioFilePath)
            }
        }
    }

    EntriesListScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is EntriesAction.onSettingsClick -> {
                    onSettingsClick()
                }

                else -> Unit
            }
            viewModel.onAction(action)

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

private fun EntriesListScreen(
    state: EntriesState,
    onAction: (EntriesAction) -> Unit
) {
    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
        confirmValueChange = {
            if (it == SheetValue.PartiallyExpanded || it == SheetValue.Hidden)
                true
            else false

        }

    )

    val scrollState = rememberScrollState()


    Scaffold(
        floatingActionButton = {
            EchoFloatingActionButton(
                onClick = {
                    onAction(EntriesAction.onClickAddEntry)
                },
                onCancelRecording = {
                    onAction(EntriesAction.onCancelRecording)
                },
                onStartRecording = {
                    onAction(EntriesAction.onStartRecording)
                },

                onSaveRecording = {
                    onAction(EntriesAction.onSaveRecording)

                },

                isRecording = state.isRecording,

                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = stringResource(R.string.add_an_echo),
                    )

                }
            )
        },

        topBar = {
            EntriesListTopAppBar(
                onSettingsClick = { onAction(EntriesAction.onSettingsClick) }
            )
        },

        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->

        if (state.isRecordAudioBottomSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    onAction(EntriesAction.onCancelRecording)
                    onAction(EntriesAction.OnDismissRecordAudioBottomSheet)

                },
            ) {
                BottomSheetContent(
                    onCancelRecording = {
                        onAction(EntriesAction.onCancelRecording)
                    },
                    onStartRecording = {
                        onAction(EntriesAction.onStartRecording)
                    },
                    onPauseRecording = {
                        onAction(EntriesAction.onPauseRecording)
                    },
                    onResumeRecording = {
                        onAction(EntriesAction.onResumeRecording)
                    },
                    onSaveRecording = {
                        onAction(EntriesAction.onSaveRecording)
                    },
                    onCloseBottomSheet = {
                            onAction(EntriesAction.OnDismissRecordAudioBottomSheet)
                    },
                    counter = state.counterForRecordingAudioBottomSheet,
                    isRecording = state.isRecording
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            GradientColor1.copy(alpha = 0.4F),
                            GradientColor2.copy(alpha = 0.4F)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
        ) {


            if (state.filteredEntries.isEmpty()) {
                NoEntries(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            16.dp
                        )
                        .padding(
                            top = 56.dp
                        )
                ) {
                    val groupedByDate = state.filteredEntries.groupBy { item ->
                        Instant.ofEpochMilli(item.createdDate)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }

                    items(groupedByDate.size) { it ->
                        EntriesListDayView(
                            date =
                            formatDate(groupedByDate.keys.elementAt(it).toString()),
                            playingEntryId = state.playingEntryId,

                            onClickPlay = { id ->
                                onAction(EntriesAction.onPlayAudio(id))
                            },
                            onClickPause = {
                                onAction(EntriesAction.onPauseAudio(it))
                            },
                            onClickResume = {
                                onAction(EntriesAction.onResumeAudio(it))
                            },
                            entries = groupedByDate.values.elementAt(it).toList(),
                            getAudioAmplitudes = { path ->
                               println(path)
                                emptyList()
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp, top = 16.dp, start = 16.dp)
                            .horizontalScroll(
                                scrollState
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        EntriesListMoodFilterChip(
                            title = "All Moods",
                            selectedList = state.selectedMoods,
                            isActive = state.isAllMoodsOpen,
                            onClick = {
                                onAction(EntriesAction.onClickAllMoods)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        EntriesListTopicFilterChip(
                            selectedList = state.selectedTopics,
                            isActive = state.isAllTopicsOpen,
                            onClick = {
                                onAction(EntriesAction.onClickAllTopics)
                            },
                            title = "All Topics"
                        )
                    }
                    Box {
                        SelectableMoodFilterList(
                            itemList = allMoodsList,
                            isVisible = state.isAllMoodsOpen,
                            selectedItemList = state.selectedMoods,
                            onClick = { item ->
                                onAction(EntriesAction.onSelectFilterMoods(item))
                            }
                        )

                        SelectableTopicFilterList(
                            itemList = state.topics,
                            isVisible = state.isAllTopicsOpen,
                            selectedItemList = state.selectedTopics,
                            onClick = { item ->
                                onAction(EntriesAction.onSelectFilterTopics(item))

                            }
                        )
                    }


                }

            }
        }


    }
}

fun formatDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(dateString, formatter)
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)

    return when (date) {
        today -> "Today"
        yesterday -> "Yesterday"
        else -> date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
    }
}

@Preview

@Composable

private fun EntitiesListScreenPreview() {

    EchoJournalTheme {

        EntriesListScreen(


            state = EntriesState(),

            onAction = {}

        )

    }

}