package com.ken.myapplication.data

data class SubmitStats(
    val acSubmissionNum: List<SubmissionNum> = listOf(),
    val totalSubmissionNum: List<SubmissionNum> = listOf()
)