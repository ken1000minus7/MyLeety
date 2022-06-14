package com.ken.myapplication.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationData(
    val icon : ImageVector,
    val title : String,
    val route : String
){
    object Home : BottomNavigationData(Icons.Default.AccountCircle,"Profile","profile")
    object Search : BottomNavigationData(Icons.Default.Person,"Following","following")
}
