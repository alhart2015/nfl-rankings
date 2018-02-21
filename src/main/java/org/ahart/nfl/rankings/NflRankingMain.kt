package org.ahart.nfl.rankings

import org.ahart.nfl.rankings.utils.readResultsFile

// in case this gets built to a jar
fun main(args: Array<String>) {
    NflRankingMain().run()
}

class NflRankingMain {

    fun run() {

        val games2016 = readResultsFile("game_scores_2016.txt")
        val games2017 = readResultsFile("game_scores_2017.txt")

        val league = League()
        // In order to get a set of ratings without human bias, we'll start each team with a rating of 1500 at the
        // start of the 2016 season. We'll then evaluate every game in 2016 to get a state of the league at the end of
        // the year, and use that (or some modification of that with regression to the mean, TBD) as the starting point
        // for the 2017 ratings.
        league.populate(games2016)
        println("League ratings after 2016")
        league.printByRating()
        println()
        league.resetForNewSeason()
        league.update(games2017)
        println("League ratings after 2017")
        league.printByRating()

    }
}