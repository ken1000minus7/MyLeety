package com.ken.myapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import com.ken.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(){
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
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Image of ma boi",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = "Leety lover",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.layoutId("name")
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("quesStats")
            ) {
                QuestionStats(title = "Easy", value = 20, color = Color.Green)
                QuestionStats(title = "Medium", value = 90, color = Color(0xFFC94E0C))
                QuestionStats(title = "Hard", value = 11, color = Color.Red)
            }
            Text(
                text = "I love leety more than anything else, please leety give me lyf",
                modifier = Modifier.layoutId("about")
            )
            Button(
                onClick = {},
                modifier = Modifier.layoutId("button")
            ) {
                Text(text = "Clicky ma boi")
            }
        }
    }
}

private fun portraitConstraints() : ConstraintSet{
    return ConstraintSet {
        val image = createRefFor("image")
        val name = createRefFor("name")
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

        constrain(quesStats) {
            top.linkTo(name.bottom)
        }

        constrain(about) {
            top.linkTo(quesStats.bottom)
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
        Text(text = value.toString(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = title, fontSize = 18.sp, color = color)
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
