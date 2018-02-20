package org.ahart.nfl.rankings

import org.ahart.nfl.rankings.utils.ResultsSchema
import kotlin.math.max

val SCORE_IMPACT_FACTOR = 30

fun createGameFromLine(line: List<String>): Game {

    val homeTeam = if (line[ResultsSchema.HOME.ordinal] == "@") {
        line[ResultsSchema.LOSER.ordinal]
    } else {
        line[ResultsSchema.WINNER.ordinal]
    }

    return Game(
            line[ResultsSchema.WEEK.ordinal],
            line[ResultsSchema.DAY.ordinal],
            line[ResultsSchema.DATE.ordinal],
            line[ResultsSchema.TIME.ordinal],
            line[ResultsSchema.WINNER.ordinal],
            line[ResultsSchema.LOSER.ordinal],
            homeTeam,
            line[ResultsSchema.POINTS_W.ordinal].toInt(),
            line[ResultsSchema.POINTS_L.ordinal].toInt()
    )
}

data class Game(
        val week: String,
        val day: String,
        val date: String,
        val time: String,
        val winner: String,
        val loser: String,
        val homeTeam: String,
        val winnerScore: Int,
        val loserScore: Int
) {
    /**
     * A game's impact affects both teams equally and in opposite amounts. A great team blowing out a bad team should
     * have a small impact. A team beating a slightly worse team in a close game should have a small impact. A team
     * blowing out a slightly worse team should have a big impact. A big underdog winning a close game should have a big
     * impact. Et cetera.
     *
     * @param team1: the first team in the game
     * @param team2: the second team in the game
     *
     * @return a Pair<Team, Team> (team1, team2) with each team's rating updated
     */
    fun assessImpact(team1: Team, team2: Team) : Pair<Team, Team> {

        checkRightTeams(team1, team2)

        val ratingDiff = max(team1.rating - team2.rating, 1) // avoid divide by 0
        val scoreDiff = max(winnerScore - loserScore, 1)

        var gameImpact = scoreDiff * SCORE_IMPACT_FACTOR / ratingDiff

        if (wasUpset(team1, team2)) {
            gameImpact = ratingDiff / scoreDiff
        }

        // if team1 won, gameImpact will be positive. if team2 won gameImpact will be negative
        val resultTeam1: Team
        val resultTeam2: Team
        if (team1.name == winner) {
            resultTeam1 = team1.copyWithRatingChange(gameImpact)
            resultTeam2 = team2.copyWithRatingChange(-gameImpact)
        } else {
            resultTeam1 = team1.copyWithRatingChange(-gameImpact)
            resultTeam2 = team2.copyWithRatingChange(gameImpact)
        }


        return Pair(resultTeam1, resultTeam2)
    }

    fun wasUpset(team1: Team, team2: Team) : Boolean {
        // this check is redundant in assessImpact, but you might need it elsewhere
        checkRightTeams(team1, team2)

        if (team1.rating > team2.rating) {
            // team 1 is favored. if they won, it's not an upset. if they didn't win, it is
            return winner != team1.name
        }

        // otherwise team 2 is favored. did they win?
        return winner != team2.name
    }

    fun checkRightTeams(team1: Team, team2: Team) {
        if (team1.name != winner && team1.name != loser) {
            throw RuntimeException("team1 supplied to Game.wasUpset() is not represented in the Game. \nteam1: $team1, \ngame: $this")
        }
        if (team2.name != winner && team2.name != loser) {
            throw RuntimeException("team2 supplied to Game.wasUpset() is not represented in the Game. \nteam2: $team2, \ngame: $this")
        }
    }
}