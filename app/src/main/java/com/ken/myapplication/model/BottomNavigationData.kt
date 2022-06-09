package com.ken.myapplication.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationData(
    val icon : ImageVector,
    val title : String
){
    object Home : BottomNavigationData(Icons.Default.Home,"Home")
    object Search : BottomNavigationData(Icons.Default.Search,"Search")
}
