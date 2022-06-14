package com.ken.myapplication.data

data class UserCalendar(
    val activeYears: List<Int>,
    val dccBadges: List<DccBadge>,
    val streak: Int,
    val submissionCalendar: String,
    val totalActiveDays: Int
)