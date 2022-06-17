package com.ken.myapplication.screens

import android.content.Context
import android.window.SplashScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ken.myapplication.model.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    val navControllerBackStackEntry = navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username",null)
    val route = if(username==null) Routes.OnBoarding else Routes.MainPage
    LaunchedEffect(key1 = true){
        delay(2000)
        navController.navigate(route){
            popUpTo(navControllerBackStackEntry.value?.destination?.route!!){
                inclusive = true
            }
        }
    }
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val title = createRef()
        val description = createRef()
        Text(
            text = "MyLeety",
            modifier = Modifier.constrainAs(title){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(description.top)
            },
            fontSize = 40.sp
        )
        Text(
            text = "Stay up-to-date with your Leetcode stats !",
            modifier = Modifier.fillMaxWidth().constrainAs(description){
                bottom.linkTo(parent.bottom, margin = 60.dp)
            },
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen(rememberNavController())
}