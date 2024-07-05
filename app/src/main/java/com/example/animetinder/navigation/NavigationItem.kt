package com.example.animetinder.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.animetinder.R

sealed class NavigationItem(
    val screen: Screen,
    val unselectedIcon: Int,
    val selectedIcon: Int

    ) {

    object SingleGame: NavigationItem(
        screen = Screen.SingleGame,
        unselectedIcon = R.drawable.single_game_icon_unselected,
        selectedIcon = R.drawable.single_game_icon_selected
    )

    object CooperativeGame: NavigationItem(
        screen = Screen.CooperativeGame,
        unselectedIcon = R.drawable.cooperative_game_icon_unselected,
        selectedIcon = R.drawable.cooperative_game_icon_selected
    )

    object Lists: NavigationItem(
        screen = Screen.Lists,
        unselectedIcon = R.drawable.lists_icon_unselected,
        selectedIcon = R.drawable.lists_icon_selected
    )

    /*object Search: NavigationItem(
        screen = Screen.Search,
        unselectedIcon = Icons.Filled.Share
    )



    object Profile: NavigationItem(
        screen = Screen.Profile,
        unselectedIcon = Icons.Filled.Settings
    )*/
}

