package com.ken.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainPage() {
    Column {
        Text(text = "Lol")
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview(){
    MainPage()
}