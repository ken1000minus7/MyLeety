package com.ken.myapplication.data

import com.ken.myapplication.room.SavedUser

data class User(
    val activeBadge: Badge? = null,
    val badges: List<Badge> = listOf(),
    val contestBadge: Badge? = null,
    val contributions: Contributions? = null,
    val email: String? = null,
    val emails: List<Email> = listOf(),
    val firstName: String? = null,
    val githubUrl: String? = null,
    val id: Any? = null,
    val isActive: Boolean = false,
    val isCurrentUserPremium: Boolean = false,
    val isCurrentUserVerified: Boolean = false,
    val isDiscussAdmin: Boolean = false,
    val isDiscussStaff: Boolean = false,
    val joinedTimestamp: Int? = null,
    val languageProblemCount: List<LanguageProblemCount> = listOf(),
    val lastName: String? = null,
    val linkedinUrl: String? = null,
    val nameColor: Any? = null,
    val phone: Any? = null,
    val problemsSolvedBeatsStats: List<ProblemsSolvedBeatsStat> = listOf(),
    val profile: Profile,
    val socialAccounts: List<String> = listOf(),
    val submitStats: SubmitStats,
    val submitStatsGlobal: SubmitStats,
    val tagProblemCounts: TagProblemCounts,
    val twitterUrl: String? = null,
    val upcomingBadges: List<UpcomingBadge> = listOf(),
    val userCalendar: UserCalendar,
    val username: String,
    val yearJoined: Int? = null
){
    fun toSavedUser() : SavedUser{
        return SavedUser(
            username,
            profile.userAvatar,
            profile.aboutMe,
            firstName,
            lastName
        )
    }
}