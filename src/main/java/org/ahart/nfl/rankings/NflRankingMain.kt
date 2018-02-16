package org.ahart.nfl.rankings

import org.ahart.nfl.rankings.utils.readResultsFile

class NflRankingMain {

    fun main(args: Array<String>) {

    }

    fun test() {
        val games = readResultsFile("game_scores_2017.txt")

        games.subList(0, 10).forEach({
            println(it)
        })
    }
}