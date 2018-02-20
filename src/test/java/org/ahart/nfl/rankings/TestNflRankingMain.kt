package org.ahart.nfl.rankings

import org.ahart.nfl.rankings.utils.readResultsFile
import org.junit.Test

class TestNflRankingMain {

    @Test
    fun runMain() {
        val driver = NflRankingMain()

        driver.run()
    }
}