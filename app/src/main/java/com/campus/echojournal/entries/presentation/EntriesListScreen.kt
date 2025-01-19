@file:OptIn(ExperimentalMaterial3Api::class)

package com.campus.echojournal.entries.presentation


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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.GradientColor1
import com.campus.echojournal.ui.theme.GradientColor2

@Composable
fun EntriesListScreenRoot(
    onSettingsClick: () -> Unit

    //viewModel: EntitiesListViewModel = org.koin.androidx.compose.koinViewModel()

) {

    EntriesListScreen(
        onSettingsClick = onSettingsClick
        //   state = viewModel.state,

        // onAction = viewModel::onAction

    )

}

@Composable

private fun EntriesListScreen(

    onSettingsClick: () -> Unit

    //  state: EntitiesListState,

    //  onAction: (EntitiesListAction) -> Unit

) {
    val entriesList = listOf<String>(
        "Echo 1",
        "Echo 2",
    )
    var isOpenAllMoods by remember {
        mutableStateOf(false)
    }

    val allMoodsList = listOf(
        Pair(R.drawable.mood_excited_active_on, "Excited"),
        Pair(R.drawable.mood_peaceful_active_on, "Peaceful"),
        Pair(R.drawable.mood_neutral_active_on, "Neutral"),
        Pair(R.drawable.mood_sad_active_on, "Sad"),
        Pair(R.drawable.mood_stresses_active, "Stressed"),

        )

    var isOpenAllTopics by remember {
        mutableStateOf(false)
    }

    val allTopicsList = listOf(
        Pair(R.drawable.ic_back, "Work"),
        Pair(R.drawable.ic_back, "Family"),
        Pair(R.drawable.ic_back, "Friends"),
        Pair(R.drawable.ic_back, "Love"),
    )

    val selectedMoods = remember {
        mutableStateListOf<Pair<Int, String>>()
    }
    val selectedTopics = remember {
        mutableStateListOf<Pair<Int, String>>()
    }

    val scrollState = rememberScrollState()


    Scaffold(
        floatingActionButton = {
            EchoFloatingActionButton(
                onClick = { },
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
                onSettingsClick = onSettingsClick
            )
        },

        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->

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
                            )
                    ) {
                        EntriesListFilterChip(
                            showIcons = true,
                            title = "All Moods",
                            selectedList = selectedMoods,
                            isActive = isOpenAllMoods,
                            onClick = {
                                isOpenAllMoods = !isOpenAllMoods

                                if (isOpenAllMoods) {
                                    isOpenAllTopics = false
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        EntriesListFilterChip(
                            showIcons = false,
                            selectedList = selectedTopics,
                            isActive = isOpenAllTopics,
                            onClick = {
                                isOpenAllTopics = !isOpenAllTopics
                                if (isOpenAllTopics) {
                                    isOpenAllMoods = false
                                }
                            },
                            title = "All Topics"
                        )
                    }
                    Box {
                        SelectableFilterList(
                            itemList = allMoodsList,
                            isVisible = isOpenAllMoods,
                            selectedItemList = selectedMoods.map {
                                it.second
                            },
                            onClick = { item ->
                                if (!selectedMoods.removeAll { it.second == item }) {
                                    val selectedMood = allMoodsList.find {
                                        it.second == item
                                    } ?: Pair(0, "")
                                    selectedMoods.add(selectedMood)
                                }

                            }
                        )
                        SelectableFilterList(
                            itemList = allTopicsList,
                            isVisible = isOpenAllTopics,
                            selectedItemList = selectedTopics.map {
                                it.second
                            },
                            onClick = { item ->
                                if (!selectedTopics.removeAll { it.second == item }) {
                                    val selectedTopic = allTopicsList.find {
                                        it.second == item
                                    } ?: Pair(0, "")
                                    selectedTopics.add(selectedTopic)
                                }

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

            onSettingsClick = {}

            //       state = EntitiesListState(),

            //     onAction = {}

        )

    }

}