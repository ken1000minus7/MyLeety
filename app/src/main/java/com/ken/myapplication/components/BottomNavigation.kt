package com.ken.myapplication.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ken.myapplication.model.BottomNavigationData

@Composable
fun BottomNavigation(navController: NavController){
    val bottomNavigationItems = listOf(
        BottomNavigationData.Home,
        BottomNavigationData.Search
    )
    NavigationBar {
        val navControllerBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navControllerBackStackEntry?.destination?.route
        bottomNavigationItems.forEach { item->

            NavigationBarItem(
                selected = currentRoute==item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(item.icon,item.title)
                },
                label = {
                    Text(text = item.title, fontWeight = FontWeight.Bold)
                }
            )
        }
    }
}