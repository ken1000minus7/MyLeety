package com.ken.myapplication.components

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainFab(lazylistState : LazyListState){
    val expandedState = remember {
        derivedStateOf {
            lazylistState.firstVisibleItemIndex < 2
        }
    }

    val context = LocalContext.current
    ExtendedFloatingActionButton(
        text = {
            Text(text = "Search")
        },
        icon = {
            Icon(Icons.Default.Search,"Search")
        },
        onClick = { Toast.makeText(context,"Search up ma boi",Toast.LENGTH_SHORT).show() },
        expanded = expandedState.value
    )
}