package com.ken.myapplication.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ken.myapplication.model.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoarding(navController: NavController){
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    BackHandler(enabled = pagerState.currentPage>0) {
        scope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage-1)
        }
    }
    BoxWithConstraints(
        Modifier.fillMaxSize()
    ) {
        val constraintSet = pageConstraints()
        ConstraintLayout(
            constraintSet = constraintSet,
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                count = 3,
                modifier = Modifier
                    .fillMaxSize()
                    .layoutId("pageContent"),
                state = pagerState
            ) { page->
                when(page){
                    0 -> {
                        Page1()
                    }
                    1 ->{
                        Page2()
                    }
                    2 ->{
                        Page3(navController)
                    }
                }
            }
            DotIndicator(
                pagerState = pagerState,
                modifier = Modifier.layoutId("dotIndicator")
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DotIndicator(
    pagerState : PagerState,
    modifier: Modifier = Modifier
){
    val scope = rememberCoroutineScope()
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ){
        items(pagerState.pageCount){ index ->
            Icon(
                imageVector = if(index==pagerState.currentPage) Icons.Default.Circle else Icons.Outlined.Circle,
                contentDescription = "dot",
                modifier = Modifier.clickable {
                    if(index!=pagerState.currentPage){
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                }
            )
        }
    }
}

private fun pageConstraints() : ConstraintSet{
    return ConstraintSet {
        val pageContent = createRefFor("pageContent")
        val dotIndicator = createRefFor("dotIndicator")
        constrain(dotIndicator){
            bottom.linkTo(parent.bottom, margin = 40.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}

@Composable
fun Page1(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "MyLeety",
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
        Text(
            text = "Stay up-to-date with your Leetcode stats !",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
    }
}

@Composable
fun Page2(){
    Text(text = "leety op")
}

@Composable
fun Page3(navController: NavController){
    val navControllerBackStackEntry = navController.currentBackStackEntryAsState()
    Button(onClick = {
        navController.navigate(Routes.LoginPage){
            popUpTo(navControllerBackStackEntry.value?.destination?.route!!){
                inclusive = true
            }
        }
    }) {
        Text(text = "lexgo")
    }
}

@Preview(showBackground = true)
@Composable
private fun OnBoardingPreview(){
    OnBoarding(rememberNavController())
}