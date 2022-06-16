package com.ken.myapplication.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ken.myapplication.model.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    val navControllerBackStackEntry = navController.currentBackStackEntryAsState()
    LaunchedEffect(key1 = true){
        delay(3000)
        navController.navigate(Routes.OnBoarding){
            popUpTo(navControllerBackStackEntry.value?.destination?.route!!){
                inclusive = true
            }
        }
    }
    Text(text = "elo")
}