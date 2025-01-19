@file:OptIn(ExperimentalMaterial3Api::class)

package com.campus.echojournal.entries.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.campus.echojournal.entries.presentation.components.EchoFloatingActionButton
import com.campus.echojournal.entries.presentation.components.EntriesListDayView
import com.campus.echojournal.entries.presentation.components.EntriesListFilterChip
import com.campus.echojournal.entries.presentation.components.EntriesListTopAppBar
import com.campus.echojournal.entries.presentation.components.NoEntries
import com.campus.echojournal.entries.presentation.components.SelectableFilterList
import com.campus.echojournal.entries.util.allMoodsList
import com.campus.echojournal.entries.util.allTopicsList
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.GradientColor1
import com.campus.echojournal.ui.theme.GradientColor2

@Composable
fun EntriesListScreenRoot(
    onSettingsClick: () -> Unit,

    viewModel: EntriesViewModel = org.koin.androidx.compose.koinViewModel()

) {

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

@Composable

private fun EntriesListScreen(
    state: EntriesState,
    onAction: (EntriesAction) -> Unit
) {
    val entriesList = listOf<String>(
        "Echo 1",
        "Echo 2",
    )
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
                    onAction(EntriesAction.OnDismissRecordAudioBottomSheet)

                },
            ) {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
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

                }
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


            if (entriesList.isEmpty()) {
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


                    items(3) { it ->
                        EntriesListDayView()

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
                        EntriesListFilterChip(
                            showIcons = true,
                            title = "All Moods",
                            selectedList = state.selectedMoods,
                            isActive = state.isAllMoodsOpen,
                            onClick = {
                                onAction(EntriesAction.onClickAllMoods)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        EntriesListFilterChip(
                            showIcons = false,
                            selectedList = state.selectedTopics,
                            isActive = state.isAllTopicsOpen,
                            onClick = {
                                onAction(EntriesAction.onClickAllTopics)
                            },
                            title = "All Topics"
                        )
                    }
                    Box {
                        SelectableFilterList(
                            itemList = allMoodsList,
                            isVisible = state.isAllMoodsOpen,
                            selectedItemList = state.selectedMoods.map {
                                it.second
                            },
                            onClick = { item ->
                                onAction(EntriesAction.onSelectFilterMoods(item))
                            }
                        )
                        SelectableFilterList(
                            itemList = allTopicsList,
                            isVisible = state.isAllTopicsOpen,
                            selectedItemList = state.selectedTopics.map {
                                it.second
                            },
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