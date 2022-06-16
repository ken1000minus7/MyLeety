package com.ken.myapplication.api

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class LeetyApiRepositoryImpli @Inject constructor(
    val api: LeetyApi,
    val sharedPreferences: SharedPreferences
    ) : LeetyApiRepository {

    override suspend fun getUser() : LeetyApiResult {
        val username = sharedPreferences.getString("username",null) ?: return LeetyApiResult.NonExistentUser()
        val query = getQueryString(username)
        val response = api.getUser(query)

        Log.d("lol", response.body()?.data?.matchedUser?.profile?.toString().toString())
        Log.d("lol", response.code().toString())

        if(response.body()==null)
            return LeetyApiResult.Failed()
        if(response.body()!!.data.matchedUser==null)
            return LeetyApiResult.NonExistentUser()

        return LeetyApiResult.Success(response.body()!!.data.matchedUser)
    }

    override suspend fun getUser(username: String): LeetyApiResult {
        val query = getQueryString(username)
        val response = api.getUser(query)

        Log.d("lol", response.body()?.data?.matchedUser?.profile?.toString().toString())
        Log.d("lol", response.code().toString())

        if(response.body()==null)
            return LeetyApiResult.Failed()
        if(response.body()!!.data.matchedUser==null)
            return LeetyApiResult.NonExistentUser()

        return LeetyApiResult.Success(response.body()!!.data.matchedUser)
    }

    private fun getQueryString(username : String) : String{
        return "{ matchedUser(username : \"$username\" ){ username firstName lastName isActive isCurrentUserPremium isCurrentUserVerified profile{ userSlug realName birthday aboutMe occupation lastModified countryName countryCode userAvatar location gender privacyContact websites rewardStats skillTags age education school company jobTitle yearsOfExperience ranking globalRanking contestCount acStats{ acQuestionCount acSubmissionCount totalSubmissionCount acRate } starRating publicBadgeType postViewCount postViewCountDiff solutionCount solutionCountDiff categoryDiscussCount categoryDiscussCountDiff reputation reputationDiff  } socialAccounts email emails{ email verified primary  } phone isDiscussAdmin isDiscussStaff submitStats { acSubmissionNum{ difficulty submissions count  } totalSubmissionNum { difficulty submissions count } } submitStatsGlobal { acSubmissionNum{ difficulty submissions count  } totalSubmissionNum{ difficulty submissions count  }  } problemsSolvedBeatsStats{ difficulty percentage } contributions{ points questionCount testcaseCount  } activeBadge{ id creationDate updationDate badge expiredDate name icon shortName displayName expired userCount category hoverText } contestBadge{ id creationDate updationDate badge expiredDate name icon shortName displayName expired userCount category hoverText } badges{ id creationDate updationDate badge expiredDate name icon shortName displayName expired userCount category hoverText } upcomingBadges{ name icon progress } nameColor id userCalendar{ activeYears submissionCalendar streak totalActiveDays dccBadges{ timestamp badge{ id creationDate updationDate badge expiredDate name icon shortName displayName expired userCount category hoverText } } } yearJoined languageProblemCount{ languageName problemsSolved  } tagProblemCounts{ advanced{ tagName problemsSolved tagSlug } intermediate{ tagName problemsSolved tagSlug } fundamental{ tagName problemsSolved tagSlug  } } githubUrl linkedinUrl twitterUrl } }"
    }
}