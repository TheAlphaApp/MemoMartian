package com.dzdexon.memomartian.navigation


import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
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
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

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
        slideInComposable(
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
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) = composable(
    route = route,
    arguments = arguments,
    deepLinks = deepLinks,
    enterTransition = { defaultScreenEnterAnimation() },
    exitTransition = { defaultScreenExitAnimation() },
    popEnterTransition = { defaultScreenEnterAnimation() },
    popExitTransition = { defaultScreenExitAnimation() },
    content = content
)

fun NavGraphBuilder.slideInComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) = composable(
    route = route,
    arguments = arguments,
    deepLinks = deepLinks,
    enterTransition = { slideScreenEnterAnimation() },
    exitTransition = { defaultScreenExitAnimation() },
    popEnterTransition = { defaultScreenEnterAnimation() },
    popExitTransition = { slideScreenExitAnimation() },
    content = content
)



private const val DEFAULT_FADE_DURATION = 400
private const val DEFAULT_SCALE_DURATION = 400
private const val DEFAULT_SLIDE_DURATION = 200
private const val DEFAULT_INITIAL_SCALE = 0.9f

fun getNoteEnterAnimation(): EnterTransition {
    return fadeIn(animationSpec = tween(DEFAULT_FADE_DURATION)) + scaleIn(
        initialScale = 0.9f,
        animationSpec = tween(DEFAULT_SCALE_DURATION)
    )
}

fun getNoteExitAnimation(slideDirection: Int): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { slideDirection * it },
        animationSpec = tween(durationMillis = DEFAULT_SLIDE_DURATION)
    ) + fadeOut(animationSpec = tween(durationMillis = DEFAULT_FADE_DURATION))
}

fun defaultScreenEnterAnimation(): EnterTransition {
    return fadeIn(animationSpec = tween(DEFAULT_FADE_DURATION)) +
            scaleIn(
                initialScale = DEFAULT_INITIAL_SCALE,
                animationSpec = tween(DEFAULT_SCALE_DURATION)
            )
}

fun defaultScreenExitAnimation(): ExitTransition {
    return fadeOut(animationSpec = tween(DEFAULT_FADE_DURATION)) +
            scaleOut(
                targetScale = DEFAULT_INITIAL_SCALE,
                animationSpec = tween(DEFAULT_SCALE_DURATION)
            )
}

fun slideScreenEnterAnimation(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(DEFAULT_SLIDE_DURATION)
    )
}

fun slideScreenExitAnimation(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(DEFAULT_SLIDE_DURATION)
    )
}