package com.ken.myapplication.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ken.myapplication.components.FollowingList
import com.ken.myapplication.components.AddFab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowingPage(navController: NavController){
    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AddFab(lazyListState = lazyListState)
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            FollowingList(navController = navController, lazyListState = lazyListState)
        }
    }
}