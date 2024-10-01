package com.dzdexon.memomartian.navigation


import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dzdexon.memomartian.ui.screens.details.DetailScreen
import com.dzdexon.memomartian.ui.screens.details.DetailScreenDestination
import com.dzdexon.memomartian.ui.screens.edit.EditScreen
import com.dzdexon.memomartian.ui.screens.edit.EditScreenDestination
import com.dzdexon.memomartian.ui.screens.home.HomeDestination
import com.dzdexon.memomartian.ui.screens.home.HomeScreen
import com.dzdexon.memomartian.ui.screens.search.SearchScreen
import com.dzdexon.memomartian.ui.screens.search.SearchScreenDestination

@Composable
fun NotesAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        customComposable(
            route = HomeDestination.route,
        ) {
            Log.d("NAV", "Navigate to HomeScreen")
            HomeScreen(
                navigateToEditNote = {
                    navController.navigate("${EditScreenDestination.route}/${it}")
                },
                navigateToDetailScreen = {
                    navController.navigate("${DetailScreenDestination.route}/${it}")
                },
                navigateToSearchScreen = {
                    navController.navigate(SearchScreenDestination.route)
                },
            )
        }
        customComposable(
            route = EditScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(EditScreenDestination.noteIdArgs) {
                type = NavType.LongType
            }),
        ) {
            Log.d("NAV", "Navigate to EditScreen")

            EditScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToHome = {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(HomeDestination.route) {
                            inclusive = true
                        }
                    }

                },
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
//        customComposable(
//            route = CreateScreenDestination.route,
//        ) {
//            Log.d("NAV", "Navigate to CreateScreen")
//
//            CreateScreen(
//                navigateBack = {
//                    navController.popBackStack()
//                },
//                navigateUp = {
//                    navController.navigateUp()
//                },
//            )
//        }
        customComposable(
            route = DetailScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailScreenDestination.noteIdArgs) {
                type = NavType.IntType
            }),
        ) {
            Log.d("NAV", "Navigate to DetailScreen")

            DetailScreen(
                navigateToEditScreen = {
                    navController.navigate("${EditScreenDestination.route}/${it}")
                },
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
        customComposable(
            route = SearchScreenDestination.route,
        ) {
            Log.d("NAV", "Navigate to SearchScreen")

            SearchScreen(
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToDetailScreen = {
                    navController.navigate("${DetailScreenDestination.route}/${it}")
                },
            )
        }
    }
}

fun NavGraphBuilder.customComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable() (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(200)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(200)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(200)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(200)
            )
        },
        content = content
    )
}