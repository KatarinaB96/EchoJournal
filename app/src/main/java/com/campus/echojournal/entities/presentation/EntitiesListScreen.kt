@file:OptIn(ExperimentalMaterial3Api::class)

package com.campus.echojournal.entities.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.campus.echojournal.R
import com.campus.echojournal.entities.presentation.components.EchoFloatingActionButton
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.GradientColor1
import com.campus.echojournal.ui.theme.GradientColor2

@Composable
fun EntitiesListScreenRoot(

    //viewModel: EntitiesListViewModel = org.koin.androidx.compose.koinViewModel()

) {

    EntitiesListScreen(

        //   state = viewModel.state,

        // onAction = viewModel::onAction

    )

}

@Composable

private fun EntitiesListScreen(

    //  state: EntitiesListState,

    //  onAction: (EntitiesListAction) -> Unit

) {
    Box(
        modifier = Modifier
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

            modifier = Modifier
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
                ),
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                GradientColor1.copy(alpha = 0.4F),
                                GradientColor2.copy(alpha = 0.4F)
                            )
                        )
                    ),
            ) {
                TopAppBar(
                    // I can't give the transparent color to the top app bar. So, I removed from Scaffold parameter.
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                    actions = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_settings),
                                contentDescription = stringResource(R.string.settings),
                            )
                        }
                    },

                    title = {
                        Text(stringResource(R.string.your_echojournal))
                    },

                    )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.no_entries),
                        contentDescription = stringResource(R.string.no_entries),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.no_entries),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W500

                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(
                            R.string.start_recording_first_echo
                        ),
                    )
                }

            }
        }
    }


}

@Preview

@Composable

private fun EntitiesListScreenPreview() {

    EchoJournalTheme {

        EntitiesListScreen(

            //       state = EntitiesListState(),

            //     onAction = {}

        )

    }

}