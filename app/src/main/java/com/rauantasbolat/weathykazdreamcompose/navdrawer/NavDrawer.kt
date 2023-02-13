package com.rauantasbolat.weathykazdreamcompose.navdrawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rauantasbolat.weathykazdreamcompose.R
import com.rauantasbolat.weathykazdreamcompose.ui.theme.Vazir
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {
    val items = listOf(
        NavDrawerItem.Home,
        NavDrawerItem.Astana,
        NavDrawerItem.Almaty
    )
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        val navBackState by navController.currentBackStackEntryAsState()
        val currentRoute = navBackState?.destination?.route
        items.forEach{
            item -> DrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                navController.navigate(item.route) {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            route -> popUpTo(route) {
                                saveState = true
                        }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }

                }
        } )
        }


    }
}

@Composable
fun DrawerItem(item: NavDrawerItem, selected: Boolean, onItemClick: (NavDrawerItem) -> Unit) {
    val background = if (selected) R.color.purple_700 else R.color.white
    val textColor = if (selected) Color.White else Color.Black
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
    ) {
        Icon(
            item.icon,
            contentDescription = item.title,
            modifier = Modifier
                .height(22.dp)
                .width(22.dp),
            Color.Gray
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = item.title,
            color = textColor,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            fontFamily = Vazir
        )
    }
}