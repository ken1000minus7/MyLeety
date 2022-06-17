package com.ken.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ken.myapplication.components.*
import com.ken.myapplication.model.Routes
import com.ken.myapplication.screens.*
import com.ken.myapplication.ui.theme.MyLeetyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyLeetyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyLeetyApp()
                }
            }
        }
    }
}

@Composable
fun MyLeetyApp(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen
    ){
        composable(Routes.SplashScreen){
            SplashScreen(navController)
        }
        composable(Routes.OnBoarding){
            OnBoarding(navController = navController)
        }
        composable(Routes.LoginPage){
            LoginPage(navController = navController)
        }
        composable(Routes.MainPage){
            MainPage(navController)
        }
        composable(
            Routes.DetailsPage+"/{username}",
            listOf(navArgument("username"){ type = NavType.StringType })
        ){
            val username = it.arguments?.getString("username")
            DetailsPage(navController,username!!)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyLeetyAppPreview(){
    MyLeetyApp()
}

