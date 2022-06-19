package com.ken.myapplication.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ken.myapplication.model.Routes
import com.ken.myapplication.room.SavedUser
import com.ken.myapplication.utils.FollowingViewModel

@Composable
fun FollowingList(navController: NavController,lazyListState: LazyListState){
    val followingViewModel : FollowingViewModel = hiltViewModel()
    val userList = followingViewModel.userList.observeAsState().value


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        lazyListState
    ){
        items(userList ?: listOf()){ user->
            FollowingListItem(user = user,navController)
        }
        item{
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowingListItem(user : SavedUser,navController: NavController){
    ElevatedCard(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .border(2.dp, Color.Black, RoundedCornerShape(5.dp))
            .clickable {
                navController.navigate(Routes.DetailsPage+"/${user.username}")
            }
    ) {
        Text(
            text = user.username,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}