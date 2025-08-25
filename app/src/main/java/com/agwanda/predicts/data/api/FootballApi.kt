package com.agwanda.predicts.data.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

// Football-Data.org v4
interface FootballApi {
    // Simple endpoints scaffolding
    @Headers("X-Auth-Token: DYNAMIC") // real token injected via OkHttp interceptor
    @GET("matches")
    suspend fun getMatches(@Query("status") status: String = "SCHEDULED"): MatchesResponse

    @Headers("X-Auth-Token: DYNAMIC")
    @GET("competitions/{code}/matches")
    suspend fun getCompetitionMatches(
        @Path("code") code: String,
        @Query("status") status: String = "SCHEDULED"
    ): MatchesResponse
}

data class MatchesResponse(val matches: List<Match> = emptyList())
data class Match(
    val id: Long,
    val utcDate: String? = null,
    val status: String? = null,
    val homeTeam: Team? = null,
    val awayTeam: Team? = null,
    val score: Score? = null
)
data class Team(val id: Long? = null, val name: String? = null, val shortName: String? = null)
data class Score(val fullTime: ScoreDetail? = null)
data class ScoreDetail(val home: Int? = null, val away: Int? = null)
