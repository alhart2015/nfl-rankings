package org.ahart.nfl.rankings

import org.ahart.nfl.rankings.utils.readResultsFile
import org.junit.Test

class TestNflRankingMain {

    @Test
    fun runMain() {
        var driver = NflRankingMain()

        driver.test()
    }

    @Test
    fun testReadResults() {
//        val classloader = Thread.currentThread().contextClassLoader
//        val inputStream = classloader.getResourceAsStream("game_scores_2017.txt")
//
//        val games = readResultsFile(inputStream)
//
//        games.subList(0, 10).forEach({
//            println(it)
//        })
    }
}