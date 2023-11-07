package com.haki.hqrecipe

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.haki.hqrecipe.components.BottomBar
import com.haki.hqrecipe.navigation.Screen
import com.haki.hqrecipe.screen.detail.DetailScreen
import com.haki.hqrecipe.screen.home.HomeScreen
import com.haki.hqrecipe.screen.profile.ProfileScreen
import com.haki.hqrecipe.screen.saved.SavedScreen

@Composable
fun CookHqApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailRecipe.route) {
                BottomBar(navController = navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { recipeId ->
                        navController.navigate(Screen.DetailRecipe.createRoute(recipeId))
                    }
                )
            }
            composable(Screen.Saved.route) {
                SavedScreen(
                    navigateToDetail = { recipeId ->
                        navController.navigate(Screen.DetailRecipe.createRoute(recipeId))
                    }
                )
            }
            composable(Screen.About.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailRecipe.route,
                arguments = listOf(navArgument("recipeId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("recipeId") ?: -1L
                DetailScreen(
                    recipeId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}