package org.ahart.nfl.rankings

//fun createTeamLookupFromSchedule(games: List<Game>) : Map<String, Team> {
//
//}

data class Team(
        val name: String,
        var wins: Int,
        var losses: Int,
        var rating: Int
)