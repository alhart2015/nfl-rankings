package org.ahart.nfl.rankings

//fun createTeamLookupFromSchedule(games: List<Game>) : Map<String, Team> {
//
//}

data class Team(
        val name: String,
        val wins: Int,
        val losses: Int,
        val rating: Int
) {
    /**
     * Don't want any confusion with mutability.
     */
    fun copyWithRatingChange(delta: Int) : Team {
        return Team(
                name,
                wins,
                losses,
                rating + delta
        )
    }
}