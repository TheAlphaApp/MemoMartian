package com.dzdexon.memomartian.navigation


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dzdexon.memomartian.ui.screens.create.CreateScreen
import com.dzdexon.memomartian.ui.screens.create.CreateScreenDestination
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
        composable(
            route = HomeDestination.route,
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
            }
        ) {

            HomeScreen(
                navigateToCreateNote = {
                    navController.navigate(CreateScreenDestination.route)
                },
                navigateToDetailScreen = {
                    navController.navigate("${DetailScreenDestination.route}/${it}")
                },
                navigateToSearchScreen = {
                    navController.navigate(SearchScreenDestination.route)
                },
            )
        }
        composable(
            route = EditScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(EditScreenDestination.noteIdArgs) {
                type = NavType.IntType
            }),
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
            }
        ) {
            EditScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = CreateScreenDestination.route,
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
            }

        ) {
            CreateScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateUp = {
                    navController.navigateUp()
                },
            )
        }
        composable(
            route = DetailScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailScreenDestination.noteIdArgs) {
                type = NavType.IntType
            }),
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
            }
        ) {
            DetailScreen(
                navigateToEditScreen = {
                    navController.navigate("${EditScreenDestination.route}/${it}")
                },
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = SearchScreenDestination.route,
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
            }
        ) {
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