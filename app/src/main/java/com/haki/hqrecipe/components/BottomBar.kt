package com.haki.hqrecipe.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.haki.hqrecipe.R
import com.haki.hqrecipe.navigation.NavigationItem
import com.haki.hqrecipe.navigation.Screen
import com.haki.hqrecipe.ui.theme.botBg
import com.haki.hqrecipe.ui.theme.genBg
import com.haki.hqrecipe.ui.theme.indicatorColor
import com.haki.hqrecipe.ui.theme.selectedItem
import com.haki.hqrecipe.util.Urbanist

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavigationBar(
        containerColor = botBg,
        contentColor = indicatorColor,
        modifier = modifier.shadow(5.dp),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home),
                icon = R.drawable.home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.favorite),
                icon = R.drawable.bookmark,
                screen = Screen.Saved
            ),
            NavigationItem(
                title = stringResource(R.string.profile),
                icon = R.drawable.profile,
                screen = Screen.About
            ),
        )

        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title, fontFamily = Urbanist) },
                selected = currentRoute == item.screen.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = genBg,
                    selectedTextColor = selectedItem,
                    indicatorColor = selectedItem,
                    unselectedIconColor = selectedItem,
                    unselectedTextColor = selectedItem
                ),
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}