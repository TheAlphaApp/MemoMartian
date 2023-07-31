package com.dzdexon.memomartian.navigation


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
import com.dzdexon.memomartian.ui.screens.managetags.TagManageDestination
import com.dzdexon.memomartian.ui.screens.managetags.TagManageScreen
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


            ) {
            HomeScreen(
                navigateToCreateNote = {
                    navController.navigate(CreateScreenDestination.route)
                },
                navigateToTagManageScreen = {
                    navController.navigate(TagManageDestination.route)
                },
                navigateToNoteDetail = {
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
            })
        ) {
            EditScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToTagManageScreen = {
                    navController.navigate(TagManageDestination.route)
                },
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = CreateScreenDestination.route) {
            CreateScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToTagManageScreen = {
                    navController.navigate(TagManageDestination.route)
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
            })
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
        composable(route = TagManageDestination.route) {
            TagManageScreen(
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = SearchScreenDestination.route) {
            SearchScreen(
                navigateUp = {
                    navController.navigateUp()
                },
            )
        }
    }

}