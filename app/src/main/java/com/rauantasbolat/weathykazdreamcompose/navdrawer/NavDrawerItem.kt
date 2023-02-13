package com.rauantasbolat.weathykazdreamcompose.navdrawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDrawerItem(
    var route: String,
    var icon: ImageVector,
    var title: String
) {
    object Home : NavDrawerItem("Location", Icons.Rounded.Home, "Location")
    object Astana : NavDrawerItem("Astana", Icons.Rounded.Place, "Astana")
    object Almaty : NavDrawerItem("Almaty", Icons.Rounded.Place, "Almaty")

}