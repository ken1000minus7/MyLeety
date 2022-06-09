package com.ken.myapplication.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserList(paddingValues: PaddingValues){
    val temp = listOf(
        "Hello",
        "Bye",
        "Life",
        "Sad",
        "Here we go",
        "I am not going down",
        "I",
        "am",
        "standing",
        "my",
        "ground",
        "you",
        "haven't",
        "seen",
        "the",
        "last",
        "of",
        "me"
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
    ){
        items(temp){ item->
            UserListItem(item = item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListItem(item : String){
    ElevatedCard(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .border(2.dp, Color.Black, RoundedCornerShape(5.dp)),
    ) {
        Text(
            text = item,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}