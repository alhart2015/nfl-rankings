package org.ahart.nfl.rankings

//fun createTeamLookupFromSchedule(games: List<Game>) : Map<String, Team> {
//
//}

fun newTeam(name: String): Team {
    return Team(name, 0, 0, 1500)
}

/**
 * Databean to represent a Team. This is hashed and compared on team name
 */
data class Team(
        val name: String,
        var wins: Int,
        var losses: Int,
        val rating: Int
) {

    /**
     * Don't want any confusion with mutability.
     */
    fun copyWithRatingChange(delta: Int): Team {
        return Team(
                name,
                wins,
                losses,
                rating + delta
        )
    }

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other == null || javaClass != other.javaClass) return false
//        val that = other as Team?
//        return name == that!!.name
//    }
//
//    override fun hashCode(): Int {
//        return name.hashCode()
//    }
}