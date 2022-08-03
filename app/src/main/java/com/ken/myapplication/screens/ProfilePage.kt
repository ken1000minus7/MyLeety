package com.ken.myapplication.screens

import android.widget.CalendarView
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ken.myapplication.R
import com.ken.myapplication.api.LeetyApiResult
import com.ken.myapplication.components.Loading
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
            ConstraintLayout(
                modifier = Modifier
                    .wrapContentSize()
                    .layoutId("image")
            ) {
                val (userAvatar, activeBadge) = createRefs()
                ElevatedCard(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .constrainAs(userAvatar) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    GlideImage(
                        imageModel = user.profile.userAvatar,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                user.activeBadge?.let { badge->
                    val toolTipVisible = rememberSaveable {
                        mutableStateOf(false)
                    }
                    val interactionSource = remember {
                        MutableInteractionSource()
                    }
                    val badgeImage = (if(badge.icon[0]=='h') "" else "https://www.leetcode.com") + badge.icon
                    GlideImage(
                        imageModel = badgeImage,
                        modifier = Modifier
                            .size(40.dp)
                            .constrainAs(activeBadge) {
                                end.linkTo(parent.end, margin = 5.dp)
                                bottom.linkTo(parent.bottom, margin = 5.dp)
                            }
                            .clickable(interactionSource = interactionSource, indication = null) {
                                toolTipVisible.value = !toolTipVisible.value
                            },
                        contentScale = ContentScale.Crop
                    )
                    if(toolTipVisible.value){
                        Popup(
                            onDismissRequest = {
                                toolTipVisible.value = false
                            },
                            alignment = Alignment.BottomCenter,
                            offset = IntOffset(150,40)
                        ) {
                            ElevatedCard {
                                Text(
                                    text = badge.hoverText,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
                }
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
                    val acRateVisible = rememberSaveable {
                        mutableStateOf(false)
                    }
                    ElevatedCard(
                        modifier = Modifier
                            .padding(20.dp)
                            .size(150.dp),
                        shape = CircleShape,
                        onClick = {
                            acRateVisible.value = !acRateVisible.value
                        }
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if(acRateVisible.value){
                                Text(
                                    text = "${user.profile.acStats.acRate}%",
                                    fontSize = 35.sp,
                                    textAlign = TextAlign.Center
                                )
                                Text(text = "Acceptance")
                            }
                            else {
                                Text(
                                    text = if (user.submitStats.acSubmissionNum.size == 4) user.submitStats.acSubmissionNum[0].count.toString() else "0",
                                    fontSize = 50.sp,
                                    textAlign = TextAlign.Center
                                )
                                Text(text = "Solved")
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        QuestionStats(
                            title = "Easy",
                            value = if(user.submitStats.acSubmissionNum.size==4) user.submitStats.acSubmissionNum[1].count else 0,
                            color = Color.Green
                        )
                        QuestionStats(
                            title = "Medium",
                            value = if(user.submitStats.acSubmissionNum.size==4) user.submitStats.acSubmissionNum[2].count else 0,
                            color = Color(0xFFC94E0C)
                        )
                        QuestionStats(
                            title = "Hard",
                            value =  if(user.submitStats.acSubmissionNum.size==4) user.submitStats.acSubmissionNum[3].count else 0,
                            color = Color.Red
                        )
                    }
                }
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .layoutId("heatmap"),
                shape = RoundedCornerShape(15.dp)
            ){
                AndroidView(factory = {
                    CalendarView(it)
                })
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .layoutId("badges"),
                shape = RoundedCornerShape(15.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxSize().padding(bottom = 10.dp)
                ) {
                    Row(
                        Modifier.padding(top = 10.dp, start = 12.dp)
                    ) {
                        Text(text = "Badges: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = user.badges.size.toString(), fontSize = 22.sp)
                    }
                    if(user.badges.isNotEmpty()){
                        LazyRow(
                            Modifier.padding(10.dp)
                        ){
                            items(user.badges){ badge->
                                val badgeImage = (if(badge.icon[0]=='h') "" else "https://www.leetcode.com") + badge.icon
                                Column(
                                    modifier = Modifier.height(180.dp).width(180.dp).padding(horizontal = 10.dp),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    GlideImage(
                                        imageModel = badgeImage,
                                        modifier = Modifier.height(100.dp).padding(bottom = 10.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                    Text(text = badge.displayName, fontSize = 12.sp, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                    else{
                        val composition = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(
                            R.raw.empty_animation))
                        val progress = animateLottieCompositionAsState(composition = composition.value, iterations = LottieConstants.IterateForever)
                        Column(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LottieAnimation(composition = composition.value, progress = progress.value, modifier = Modifier
                                .size(120.dp))
                            Text(text = "No badges yet", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
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
        val heatmap = createRefFor("heatmap")
        val badges = createRefFor("badges")

        constrain(image){
            top.linkTo(parent.top)
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

        constrain(heatmap){
            top.linkTo(quesStats.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(badges){
            top.linkTo(heatmap.bottom)
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
