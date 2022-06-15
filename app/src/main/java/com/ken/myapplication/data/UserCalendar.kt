package com.ken.myapplication.data

data class UserCalendar(
    val activeYears: List<Int> = listOf(),
    val dccBadges: List<DccBadge> = listOf(),
    val streak: Int = 0,
    val submissionCalendar: String? = null,
    val totalActiveDays: Int = 0
)