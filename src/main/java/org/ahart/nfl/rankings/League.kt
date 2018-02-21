package org.ahart.nfl.rankings

class League(
        val teams: MutableMap<String, Team>
) {

    // secondary constructors in kotlin are kinda messy
    constructor() : this(mutableMapOf())

    fun populate(games: List<Game>) {

        games.forEach({
            // if you haven't seen either of these teams yet, make a new entry in the set
            val winner = teams.getOrDefault(it.winner, newTeam(it.winner))
            val loser = teams.getOrDefault(it.loser, newTeam(it.loser))

            val (newWinner, newLoser) = it.assessImpact(winner, loser)

            teams[it.winner] = newWinner
            teams[it.loser] = newLoser
        })
    }

    fun resetForNewSeason() {

        teams.forEach({ _, team ->
            run {
                team.wins = 0
                team.losses = 0
            }
        })
    }

    fun update(games: List<Game>) {
        games.forEach({
            val winner = teams[it.winner]
            val loser = teams[it.loser]

            // if either team is null, it's bad data. crash.
            if (winner == null) {
                throw RuntimeException("Unexpected team name: ${it.winner}")
            }

            if (loser == null) {
                throw RuntimeException("Unexpected team name: ${it.loser}")
            }

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