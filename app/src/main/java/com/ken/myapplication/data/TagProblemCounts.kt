package com.ken.myapplication.data

data class TagProblemCounts(
    val advanced: List<TagProblem> = listOf(),
    val fundamental: List<TagProblem> = listOf(),
    val intermediate: List<TagProblem> = listOf()
)