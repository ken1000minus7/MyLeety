package com.ken.myapplication.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ken.myapplication.api.LeetyApiResult
import com.ken.myapplication.model.Routes
import com.ken.myapplication.utils.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController){
    val context = LocalContext.current
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val userViewModel : UserViewModel = hiltViewModel()
    val apiResult = userViewModel.apiResult.observeAsState()
    val sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE)
    val dialogVisible = rememberSaveable {
        mutableStateOf(false)
    }
    val username = rememberSaveable {
        mutableStateOf("")
    }
    BackHandler(
        enabled = username.value.isNotEmpty()
    ) {
        username.value = ""
    }

    LaunchedEffect(apiResult.value){
        when(apiResult.value){
            is LeetyApiResult.Loading ->{
                dialogVisible.value = true
            }
            is LeetyApiResult.Success -> {
                dialogVisible.value = false
                sharedPreferences.edit()
                    .putString("username",username.value)
                    .apply()
                navController.navigate(Routes.MainPage){
                    popUpTo(navBackStackEntry.value!!.destination.route!!){
                        inclusive = true
                    }
                }
                Toast.makeText(context,"Welcome ${username.value}!",Toast.LENGTH_SHORT).show()
            }
            is LeetyApiResult.NonExistentUser -> {
                dialogVisible.value = false
                Toast.makeText(context,"User not found. Please check your username and your network connection and try again",Toast.LENGTH_SHORT).show()
            }
            is LeetyApiResult.Failed -> {
                dialogVisible.value = false
                Toast.makeText(context,"Unknown error occurred. Please make sure you are connected to the internet and try again",Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "MyLeety", fontSize = 40.sp)
        ElevatedCard(
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(vertical = 20.dp)
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = "Please provide your Leetcode details",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(value = username.value, onValueChange = {
                        username.value = it
                    }, label = {
                        Text(text = "Enter username")
                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                )
                Button(onClick = {
                    if(username.value==""){
                        Toast.makeText(context,"Username can't be empty",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        userViewModel.getUser(username.value)
                    }
                }, modifier = Modifier.padding(10.dp)) {
                    Text(text = "Submit")
                }
            }
        }
    }
    if(dialogVisible.value){
        Dialog({

        }) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPagePreview(){
    LoginPage(rememberNavController())
}