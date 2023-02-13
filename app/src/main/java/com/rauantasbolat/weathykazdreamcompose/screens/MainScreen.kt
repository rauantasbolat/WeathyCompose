package com.rauantasbolat.weathykazdreamcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import com.rauantasbolat.weathykazdreamcompose.R
import com.rauantasbolat.weathykazdreamcompose.navdrawer.Drawer
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rauantasbolat.weathykazdreamcompose.SearchScreen
import com.rauantasbolat.weathykazdreamcompose.navdrawer.NavDrawerItem

@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold (
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerBackgroundColor = Color.White,
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        }
            ) {
        Navigation(navController = navController)
    }



}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    TopAppBar(
        title = {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 64.dp, 0.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            text = stringResource(R.string.app_name),
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Image(
                    painter = painterResource(R.drawable.ic_menu),
                    stringResource(R.string.app_name)
                )
            }
        },
        backgroundColor = Color(0, 0, 0, 0),
        contentColor = Color.Black,
        elevation = 0.dp,
    )
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavDrawerItem.Home.route) {
        composable(NavDrawerItem.Home.route) {
            LocationScreen()
        }
        composable(NavDrawerItem.Astana.route) {
            AstanaScreen()
        }
        composable(NavDrawerItem.Almaty.route) {
            AlmatyScreen()
        }
    }
}