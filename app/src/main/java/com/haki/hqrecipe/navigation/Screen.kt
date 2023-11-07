package com.haki.hqrecipe.navigation

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Saved : Screen("Saved")
    object About : Screen("About")
    object DetailRecipe : Screen("home/{recipeId}") {
        fun createRoute(recipeId: Long) = "home/$recipeId"
    }
}