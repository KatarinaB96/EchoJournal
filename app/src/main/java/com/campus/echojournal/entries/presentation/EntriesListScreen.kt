@file:OptIn(ExperimentalMaterial3Api::class)

package com.campus.echojournal.entries.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.GradientColor1
import com.campus.echojournal.ui.theme.GradientColor2

@Composable
fun EntriesListScreenRoot(

    //viewModel: EntitiesListViewModel = org.koin.androidx.compose.koinViewModel()

) {

    EntriesListScreen(

        //   state = viewModel.state,

        // onAction = viewModel::onAction

    )

}

@Composable

private fun EntriesListScreen(

    //  state: EntitiesListState,

    //  onAction: (EntitiesListAction) -> Unit

) {
    val entriesList = listOf<String>(
        "Echo 1",
        )


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
            EntriesListTopAppBar()
        },

        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues).fillMaxSize()
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
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    Row {
                        EntriesListFilterChip(
                            title = "All Moods"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        EntriesListFilterChip(
                            title = "All Topics"
                        )
                    }


                    entriesList.forEach {
                        EntriesListDayView()
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

            //       state = EntitiesListState(),

            //     onAction = {}

        )

    }

}