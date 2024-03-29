package com.ken.myapplication.data

data class Profile(
    val aboutMe: String? = null,
    val acStats: AcStats,
    val age: Int? = null,
    val birthday: String? = null,
    val categoryDiscussCount: Int,
    val categoryDiscussCountDiff: Int,
    val company: String? = null,
    val contestCount: Int,
    val countryCode: String? = null,
    val countryName: String? = null,
    val education: String? = null,
    val gender: String? = null,
    val globalRanking: Int? = null,
    val jobTitle: String? = null,
    val lastModified: String? = null,
    val location: String? = null,
    val occupation: String? = null,
    val postViewCount: Int,
    val postViewCountDiff: Int,
    val privacyContact: Boolean,
    val publicBadgeType: Int? = null,
    val ranking: Int,
    val realName: String? = null,
    val reputation: Int,
    val reputationDiff: Int,
    val rewardStats: List<String> = listOf(),
    val school: String? = null,
    val skillTags: List<String> = listOf(),
    val solutionCount: Int,
    val solutionCountDiff: Int,
    val starRating: Double,
    val userAvatar: String? = null,
    val userSlug: String? = null,
    val websites: List<String> = listOf(),
    val yearsOfExperience: Int? = null
)