package com.ken.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ken.myapplication.components.BottomNavigation
import com.ken.myapplication.components.MainPage
import com.ken.myapplication.components.TopBar
import com.ken.myapplication.components.UserList
import com.ken.myapplication.ui.theme.MyLeetyTheme

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLeetyApp(){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(scope = scope, scaffoldState = scaffoldState)
        },
        bottomBar = {
            BottomNavigation()
        },
        drawerContent = {
            com.ken.myapplication.components.NavigationDrawer()
        },
        drawerBackgroundColor = MaterialTheme.colorScheme.surface,
        drawerContentColor = MaterialTheme.colorScheme.surface,
        drawerScrimColor = DrawerDefaults.scrimColor,
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background
    ){
        UserList(it)
    }

}

@Preview(showBackground = true)
@Composable
private fun MyLeetyAppPreview(){
    MyLeetyApp()
}

