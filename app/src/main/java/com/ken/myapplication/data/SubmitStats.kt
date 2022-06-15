package com.ken.myapplication.data

data class SubmitStats(
    val submissionNum: List<SubmissionNum> = listOf(),
    val totalSubmissionNum: List<SubmissionNum> = listOf()
)