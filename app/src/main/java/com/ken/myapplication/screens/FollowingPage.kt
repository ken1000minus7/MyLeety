package com.ken.myapplication.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ken.myapplication.api.LeetyApiResult
import com.ken.myapplication.components.FollowingList
import com.ken.myapplication.components.AddFab
import com.ken.myapplication.components.Loading
import com.ken.myapplication.utils.FollowingViewModel
import com.ken.myapplication.utils.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowingPage(navController: NavController){
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val dialogState = rememberSaveable {
        mutableStateOf(false)
    }
    val progressVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    val followingViewModel : FollowingViewModel = hiltViewModel()
    val userViewModel : UserViewModel = hiltViewModel()
    val apiResult = userViewModel.apiResult.observeAsState().value
    when(apiResult){
        is LeetyApiResult.Success -> {
            progressVisibility.value = false
            val user = apiResult.data!!
            Toast.makeText(context,"${user.username} added",Toast.LENGTH_SHORT).show()
            followingViewModel.insertUser(user.toSavedUser())
            dialogState.value = false
            userViewModel.apiResult.value = null
        }
        is LeetyApiResult.Failed -> {
            progressVisibility.value = false
            Toast.makeText(context,"Unknown error occured",Toast.LENGTH_SHORT).show()
            userViewModel.apiResult.value = null
        }
        is LeetyApiResult.NonExistentUser -> {
            progressVisibility.value = false
            Toast.makeText(context,"User not found. Please check the entered username.",Toast.LENGTH_SHORT).show()
            userViewModel.apiResult.value = null
        }
        is LeetyApiResult.Loading -> {
            progressVisibility.value = true
        }
    }

    if(progressVisibility.value){
        Dialog(onDismissRequest = {  }) {
            Loading()
        }
    }

    if(dialogState.value){
        AddDialog(dialogState = dialogState,userViewModel)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AddFab(lazyListState = lazyListState,dialogState)
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            FollowingList(navController = navController, lazyListState = lazyListState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDialog(dialogState : MutableState<Boolean>,userViewModel: UserViewModel){
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username",null)
    val followingViewModel : FollowingViewModel = hiltViewModel()
    val userList = followingViewModel.userList.observeAsState().value
    Dialog(onDismissRequest = {
        dialogState.value = false
    }) {
        ElevatedCard(
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val usernameState = rememberSaveable {
                    mutableStateOf("")
                }
                Text(
                    text = "Follow a user",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
                OutlinedTextField(
                    value = usernameState.value, 
                    onValueChange = {
                        usernameState.value = it
                    },
                    label = {
                        Text(text = "Enter username")
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )
                ElevatedButton(onClick = {
                    if(usernameState.value.isEmpty())
                        Toast.makeText(context,"Username can't be empty. Please enter a valid username",Toast.LENGTH_SHORT).show()
                    else if(usernameState.value==username){
                        Toast.makeText(context,"You can't follow yourself",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        var found = false
                        if (userList != null) {
                            for(user in userList){
                                if(user.username==usernameState.value){
                                    found = true
                                    break
                                }
                            }
                        }
                        if(found){
                            Toast.makeText(context,"You are already following this user",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            userViewModel.getUser(usernameState.value)
                        }
                    }
                }) {
                    Text(text = "Add user", fontSize = 20.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPreview(){
    AddDialog(dialogState = remember {
        mutableStateOf(true)
    },hiltViewModel())
}