package com.ken.myapplication

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainPage() {
    var nameState by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = nameState,
            onValueChange = {
                nameState = it
            },
            placeholder = {
                Text(text = "elo")
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedButton(onClick = {
            Log.d("kaka",nameState)
        }) {
            Text(text = "Submit")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPagePreview(){
    MainPage()
}