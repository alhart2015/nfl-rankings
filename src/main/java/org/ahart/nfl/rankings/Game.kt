package org.ahart.nfl.rankings

import org.ahart.nfl.rankings.utils.ResultsSchema

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

data class Game constructor(
        val week: String,
        val day: String,
        val date: String,
        val time: String,
        val winner: String,
        val loser: String,
        val homeTeam: String,
        val winnerScore: Int,
        val loserScore: Int
)