package com.ken.myapplication.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ken.myapplication.R
import com.ken.myapplication.api.LeetyApiResult
import com.ken.myapplication.components.Loading
import com.ken.myapplication.data.Profile
import com.ken.myapplication.data.User
import com.ken.myapplication.utils.UserViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfilePage(username : String? = null){
    
    val userViewModel : UserViewModel = hiltViewModel()
    val apiResult = userViewModel.apiResult.observeAsState().value
    val user = userViewModel.apiResult.observeAsState().value?.data
    val refreshing = userViewModel.isRefreshing.observeAsState()
    val refreshState = rememberSwipeRefreshState(isRefreshing = refreshing.value == true)
    val key by rememberSaveable {
        mutableStateOf(true)
    }
    if(user==null){
        LaunchedEffect(key){
            if(username==null) userViewModel.getUser()
            else userViewModel.getUser(username)
        }
    }


    when(apiResult){
        is LeetyApiResult.Success -> {
            SwipeRefresh(state = refreshState, onRefresh = {
                userViewModel.refresh(username)
            }) {
                ProfileContent(user = user!!)
            }
        }
        is LeetyApiResult.NonExistentUser -> {
            Text(text = "User does not exist")
        }
        is LeetyApiResult.Loading -> {
            Loading()
        }
        else -> {
            Text(text = "Failed")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(user : User){
    val context = LocalContext.current
    BoxWithConstraints {
        val constraintSet = if(minWidth < 600.dp) portraitConstraints() else landscapeConstraints()

        ConstraintLayout(
            constraintSet,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            animateChanges = true
        ) {
            ElevatedCard(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .layoutId("image")
            ) {
                GlideImage(
                    imageModel = user.profile.userAvatar,
                    contentDescription = "Image of ma boi",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = user.profile.realName ?: "",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.layoutId("name")
            )
            Text(
                text = user.username,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.layoutId("username")
            )
            Text(
                text = user.profile.aboutMe ?: "",
                modifier = Modifier.layoutId("about"),
                fontSize = 14.sp
            )
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .layoutId("quesStats"),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ElevatedCard(
                        modifier = Modifier.padding(20.dp).wrapContentSize()
                    ) {
                        Text(
                            text = if(user.submitStats.totalSubmissionNum.size==4) user.submitStats.totalSubmissionNum[0].count.toString() else "0",
                            fontSize = 50.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clip(CircleShape).padding(40.dp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        QuestionStats(
                            title = "Easy",
                            value = if(user.submitStats.totalSubmissionNum.size==4) user.submitStats.totalSubmissionNum[1].count else 0,
                            color = Color.Green
                        )
                        QuestionStats(
                            title = "Medium",
                            value = if(user.submitStats.totalSubmissionNum.size==4) user.submitStats.totalSubmissionNum[2].count else 0,
                            color = Color(0xFFC94E0C)
                        )
                        QuestionStats(
                            title = "Hard",
                            value =  if(user.submitStats.totalSubmissionNum.size==4) user.submitStats.totalSubmissionNum[3].count else 0,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}

private fun portraitConstraints() : ConstraintSet{
    return ConstraintSet {
        val image = createRefFor("image")
        val name = createRefFor("name")
        val username = createRefFor("username")
        val quesStats = createRefFor("quesStats")
        val about = createRefFor("about")
        val button = createRefFor("button")

        constrain(image){
            top.linkTo(parent.top, margin = 20.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(name){
            top.linkTo(image.bottom, margin = 10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(username){
            top.linkTo(name.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(quesStats) {
            top.linkTo(about.bottom)
        }

        constrain(about) {
            top.linkTo(username.bottom)
            start.linkTo(parent.start, margin = 20.dp)
            end.linkTo(parent.end, margin = 20.dp)
            width = Dimension.fillToConstraints
        }

        constrain(button){
            top.linkTo(about.bottom, margin = 15.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}

private fun landscapeConstraints() : ConstraintSet{
    return ConstraintSet {
        val image = createRefFor("image")
        val name = createRefFor("name")
        val quesStats = createRefFor("quesStats")
        val about = createRefFor("about")
        val button = createRefFor("button")

        constrain(image){
            top.linkTo(parent.top, margin = 20.dp)
            start.linkTo(parent.start, margin = 20.dp)
        }

        constrain(name){
            top.linkTo(image.top)
            start.linkTo(image.end, margin = 20.dp)
            end.linkTo(parent.end, margin = 20.dp)
        }

        constrain(quesStats) {
            top.linkTo(about.bottom)
            start.linkTo(image.end)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(about) {
            top.linkTo(name.bottom)
            start.linkTo(image.end, margin = 20.dp)
            end.linkTo(parent.end, margin = 20.dp)
            width = Dimension.wrapContent
        }

        constrain(button){
            top.linkTo(quesStats.bottom, margin = 15.dp)
            start.linkTo(image.end)
            end.linkTo(parent.end)
        }
    }
}

@Composable
fun QuestionStats(title : String, value : Int, color : Color){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(text = value.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = title, fontSize = 16.sp, color = color)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePagePreview(){
    ProfilePage()
}

@Preview(
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 1024
)
@Composable
private fun ProfilePageLandscapePreview(){
    ProfilePage()
}
