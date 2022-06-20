package com.ken.myapplication.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ken.myapplication.model.Routes
import com.ken.myapplication.room.SavedUser
import com.ken.myapplication.utils.FollowingViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowingList(navController: NavController,lazyListState: LazyListState){
    val followingViewModel : FollowingViewModel = hiltViewModel()
    val userList = followingViewModel.userList.observeAsState().value
    val dialogVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    val deleteUser : MutableState<SavedUser?> = rememberSaveable {
        mutableStateOf(null)
    }
    val context = LocalContext.current

    if(dialogVisibility.value){
        Dialog(onDismissRequest = {
            dialogVisibility.value = false
            deleteUser.value = null
        }) {
            ElevatedCard(
                modifier = Modifier.padding(10.dp).padding(10.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text = "Are sure you want to unfollow ${deleteUser.value?.username}?", modifier = Modifier.padding(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                        ElevatedButton(onClick = {
                            Toast.makeText(context,"Unfollowed ${deleteUser.value?.username}",Toast.LENGTH_SHORT).show()
                            followingViewModel.deleteUser(deleteUser.value!!)
                            deleteUser.value = null
                            dialogVisibility.value = false
                        }) {
                            Text(text = "YES")
                        }
                        ElevatedButton(onClick = {
                            dialogVisibility.value = false
                            deleteUser.value = null
                        }) {
                            Text(text = "NO")
                        }
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        lazyListState
    ){
        items(userList ?: listOf()){ user->
            FollowingListItem(user = user,navController,dialogVisibility,deleteUser)
        }
        item{
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowingListItem(
    user : SavedUser,
    navController: NavController,
    dialogVisibility : MutableState<Boolean>,
    deleteUsername : MutableState<SavedUser?>
){
    ElevatedCard(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 10.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(Routes.DetailsPage + "/${user.username}")
            },
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedCard(
                modifier = Modifier
                    .size(100.dp),
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 0.dp, bottomStart = 15.dp, bottomEnd = 0.dp)
            ) {
                GlideImage(
                    imageModel = user.userAvatar,
                    contentDescription = "Image of ma boi",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.weight(1f).height(100.dp).padding(5.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = user.name ?: "",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.username,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp
                )
                Text(
                    text = user.aboutMe ?: "",
                    fontSize = 12.sp
                )
            }
            IconButton(onClick = {
                deleteUsername.value = user
                dialogVisibility.value = true
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FollowingListItemPreview(){
    val dialogVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    val deleteUser : MutableState<SavedUser?> = rememberSaveable {
        mutableStateOf(null)
    }
    FollowingListItem(user = SavedUser(
        "leetyboi",
        "https://avatars.githubusercontent.com/u/78747188?s=400&u=42878d3757051cc81671932e785e4bd5788680c2&v=4",
        "I love leety very much",
        "Biggy Boi"
    ), navController = rememberNavController(),dialogVisibility,deleteUser)
}