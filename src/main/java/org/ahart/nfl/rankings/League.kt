package org.ahart.nfl.rankings

class League(
        val year: Int,
        val weeksCompleted: Int,
        val teams: MutableMap<String, Team>
) {

    fun populate(games: List<Game>) {

        games.forEach({
            // if you haven't seen either of these teams yet, make a new entry in the set
            val winner = teams.getOrDefault(it.winner, newTeam(it.winner))
            val loser = teams.getOrDefault(it.loser, newTeam(it.loser))

            // assess the impact of game
            winner.wins++
            loser.losses++

            val (newWinner, newLoser) = it.assessImpact(winner, loser)

            teams[it.winner] = newWinner
            teams[it.loser] = newLoser
        })
    }

    fun printByRating() {
        teams.toList()
                .map { it.second }
                .sortedBy { -it.rating } // negative sign so we sort highest to lowest
                .forEach({ println(it) })
    }
}