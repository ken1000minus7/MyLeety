package com.ken.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ken.myapplication.components.*
import com.ken.myapplication.model.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(mainNavController: NavHostController) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    androidx.compose.material.Scaffold(
        drawerBackgroundColor = MaterialTheme.colorScheme.surface,
        drawerContentColor = MaterialTheme.colorScheme.surface,
        drawerScrimColor = DrawerDefaults.scrimColor,
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background,
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(scope = scope, scaffoldState = scaffoldState)
        },
        bottomBar = {
            BottomNavigation(navController)
        },
        drawerContent = {
            NavigationDrawer()
        }
    ){
        NavHost(
            navController = navController,
            startDestination = Routes.Profile,
            modifier = Modifier.padding(paddingValues = it)
        ){
            composable(Routes.Profile){
                ProfilePage()
            }
            composable(Routes.Following){
                FollowingPage(navController = mainNavController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPagePreview(){
    MainPage(rememberNavController())
}