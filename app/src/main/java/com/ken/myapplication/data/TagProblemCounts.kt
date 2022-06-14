package com.ken.myapplication.data

data class TagProblemCounts(
    val advanced: List<TagProblem>,
    val fundamental: List<TagProblem>,
    val intermediate: List<TagProblem>
)