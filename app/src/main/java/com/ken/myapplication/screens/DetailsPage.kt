package com.ken.myapplication.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DetailsPage(navController: NavController,username : String){
    Column(modifier = Modifier.fillMaxSize()) {
        SmallTopAppBar(
            title = {

            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "")
                }
            }
        )
        Box(modifier = Modifier.weight(1f)) {
            ProfilePage(username)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsPagePreview(){
    DetailsPage(navController = rememberNavController(), username = "")
}