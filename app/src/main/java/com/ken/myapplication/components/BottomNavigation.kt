package com.ken.myapplication.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.ken.myapplication.model.BottomNavigationData

@Composable
fun BottomNavigation(){
    val bottomNavigationItems = listOf(
        BottomNavigationData.Home,
        BottomNavigationData.Search
    )
    NavigationBar {
        bottomNavigationItems.forEach { item->
            var selectedState by remember {
                mutableStateOf(item is BottomNavigationData.Home)
            }
            NavigationBarItem(
                selected = selectedState,
                onClick = {
                    selectedState = true
                },
                icon = {
                    Icon(item.icon,item.title)
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}