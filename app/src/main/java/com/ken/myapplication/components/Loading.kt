package com.ken.myapplication.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.ken.myapplication.R

@Composable
fun Loading(){
    val composition = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
    val progress = animateLottieCompositionAsState(composition = composition.value, iterations = LottieConstants.IterateForever)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition = composition.value, progress = progress.value, modifier = Modifier
            .size(120.dp)
            .padding(10.dp))
        Text(text = "Loading", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }
}