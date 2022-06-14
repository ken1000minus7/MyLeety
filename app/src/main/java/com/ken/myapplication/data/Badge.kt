package com.ken.myapplication.data

data class Badge(
    val badge: String,
    val category: String,
    val creationDate: String,
    val displayName: String,
    val expired: Boolean,
    val expiredDate: Any,
    val hoverText: String,
    val icon: String,
    val id: String,
    val name: String,
    val shortName: String,
    val updationDate: String,
    val userCount: Int
)