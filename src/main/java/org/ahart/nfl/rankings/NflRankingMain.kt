package org.ahart.nfl.rankings

import org.ahart.nfl.rankings.utils.readResultsFile

class NflRankingMain {

    fun main(args: Array<String>) {

        val games2016 = readResultsFile("game_scores_2016.txt")
        val games2017 = readResultsFile("game_scores_2017.txt")

        println(games2016.size)
        println(games2017.size)
    }

    fun test() {
        val games = readResultsFile("game_scores_2017.txt")

        games.subList(0, 10).forEach({
            println(it)
        })
    }
}