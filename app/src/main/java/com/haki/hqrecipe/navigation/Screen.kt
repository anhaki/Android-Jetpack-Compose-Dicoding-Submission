package com.haki.hqrecipe.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Saved : Screen("saved")
    object About : Screen("about")
    object DetailRecipe : Screen("home/{recipeId}") {
        fun createRoute(recipeId: Long) = "home/$recipeId"
    }
}