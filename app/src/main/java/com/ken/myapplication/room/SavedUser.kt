package com.ken.myapplication.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedUser(
    val username : String,
    val userAvatar : String?,
    val aboutMe : String?,
    val name : String?,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
)
