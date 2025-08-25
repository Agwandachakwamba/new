package com.agwanda.predicts.data.api

import retrofit2.http.GET
import retrofit2.http.Query

// The Odds API (https://the-odds-api.com/)
interface OddsApi {
    // Example: https://api.the-odds-api.com/v4/sports/soccer/odds/?regions=eu&markets=h2h&apiKey=KEY
    @GET("sports/soccer/odds/")
    suspend fun getSoccerOdds(
        @Query("apiKey") apiKey: String,
        @Query("regions") regions: String = "eu",
        @Query("markets") markets: String = "h2h",
        @Query("oddsFormat") oddsFormat: String = "decimal"
    ): List<OddsEvent>
}

data class OddsEvent(
    val id: String? = null,
    val sport_key: String? = null,
    val sport_title: String? = null,
    val commence_time: String? = null,
    val home_team: String? = null,
    val away_team: String? = null,
    val bookmakers: List<Bookmaker> = emptyList()
)

data class Bookmaker(
    val title: String? = null,
    val last_update: String? = null,
    val markets: List<Market> = emptyList()
)

data class Market(
    val key: String? = null,
    val last_update: String? = null,
    val outcomes: List<Outcome> = emptyList()
)

data class Outcome(
    val name: String? = null,  // "Home", "Draw", "Away" or team name depending on provider
    val price: Double? = null
)
