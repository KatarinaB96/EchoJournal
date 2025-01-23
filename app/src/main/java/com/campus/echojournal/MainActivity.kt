package com.campus.echojournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.campus.echojournal.core.Route
import com.campus.echojournal.entries.presentation.EntriesListScreenRoot
import com.campus.echojournal.entries.presentation.NewEntryScreenRoot
import com.campus.echojournal.entries.presentation.NewEntryViewModel
import com.campus.echojournal.settings.presentation.SettingsScreenRoot
import com.campus.echojournal.settings.presentation.SettingsViewModel
import com.campus.echojournal.ui.theme.EchoJournalTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            EchoJournalTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.EchoJournalGraph
                ) {
                    navigation<Route.EchoJournalGraph>(startDestination = Route.EntryList) {
                        composable<Route.EntryList>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = {
                                slideInHorizontally()
                            }
                        ) {
                            //                            val viewModel = koinViewModel<EntriesViewModel>()
                            EntriesListScreenRoot(
                                //                                viewModel = viewModel,
                            )
                        }

                        composable<Route.AddEntry>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = {
                                slideInHorizontally()
                            }
                        ) {
                            //TODO: send recording
                            val args = it.toRoute<Route.AddEntry>()
                            val viewModel = koinViewModel<NewEntryViewModel>()
                            NewEntryScreenRoot(
                                viewModel = viewModel,
                                onBackClick = {
                                    if (navController.previousBackStackEntry != null) {
                                        navController.navigateUp()
                                    } else {
                                        navController.navigate(Route.EntryList)
                                    }
                                },
                                //                                path = args.path
                            )
                        }
                        composable<Route.Settings>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = {
                                slideInHorizontally()
                            }
                        ) {
                            val viewModel = koinViewModel<SettingsViewModel>()
                            SettingsScreenRoot(viewModel = viewModel,
                                onBackClick = {
                                    if (navController.previousBackStackEntry != null) {
                                        navController.navigateUp()
                                    } else {
                                        navController.navigate(Route.EntryList)
                                    }
                                }
                            )
                        }
                    }
                }

                val viewModel = koinViewModel<NewEntryViewModel>()
                NewEntryScreenRoot(
                    viewModel = viewModel,
                    onBackClick = {
                        //                                                    if (navController.previousBackStackEntry != null) {
                        //                                                        navController.navigateUp()
                        //                                                    } else {
                        //                                                        navController.navigate(Route.EntryList)
                        //                                                    }
                    },
                    //                                path = args.path
                )
            }
        }
    }
}

