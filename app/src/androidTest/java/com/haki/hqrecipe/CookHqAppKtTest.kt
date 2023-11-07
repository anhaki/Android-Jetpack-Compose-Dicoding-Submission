package com.haki.hqrecipe

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.haki.hqrecipe.model.RecipesData
import com.haki.hqrecipe.navigation.Screen
import com.haki.hqrecipe.ui.theme.CookHqTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CookHqAppKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            CookHqTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                CookHqApp(navController = navController)
            }
        }
    }

    @Test
    fun verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun bottomNavigation_isWorking() {
        composeTestRule.onNodeWithStringId(R.string.favorite).performClick()
        navController.assertCurrentRouteName(Screen.Saved.route)
        composeTestRule.onNodeWithStringId(R.string.profile).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navigatesToDetailScreen_andBackToHome() {
        composeTestRule.onNodeWithTag("RecipeList").performScrollToIndex(6)
        composeTestRule.onNodeWithText(RecipesData.recipes[6].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailRecipe.route)
        composeTestRule.onNodeWithText(RecipesData.recipes[6].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(RecipesData.recipes[6].desc).assertIsDisplayed()
        composeTestRule.onNodeWithText(RecipesData.recipes[6].ingre).assertIsDisplayed()
        composeTestRule.onNodeWithText(RecipesData.recipes[6].steps).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back))
            .performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun saveRecipe_check_andRemove() {
        composeTestRule.onNodeWithTag("RecipeList").performScrollToIndex(9)
        composeTestRule.onNodeWithContentDescription("Save 9").performClick()
        composeTestRule.onNodeWithTag("RecipeList").performScrollToIndex(4)
        composeTestRule.onNodeWithContentDescription("Save 4").performClick()
        composeTestRule.onNodeWithTag("RecipeList").performScrollToIndex(6)
        composeTestRule.onNodeWithContentDescription("Save 6").performClick()

        composeTestRule.onNodeWithStringId(R.string.favorite).performClick()
        navController.assertCurrentRouteName(Screen.Saved.route)

        composeTestRule.onNodeWithContentDescription("Save 9").performClick()
        composeTestRule.onNodeWithContentDescription("Save 4").performClick()
        composeTestRule.onNodeWithContentDescription("Save 6").performClick()

        composeTestRule.onNodeWithTag("saved_error").assertIsDisplayed()

        composeTestRule.onNodeWithStringId(R.string.home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun saved_isEmpty() {
        composeTestRule.onNodeWithStringId(R.string.favorite).performClick()
        navController.assertCurrentRouteName(Screen.Saved.route)
        composeTestRule.onNodeWithTag("saved_error").assertIsDisplayed()
    }
}