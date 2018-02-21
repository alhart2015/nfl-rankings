package org.ahart.nfl.rankings

import org.junit.Test

class TestNflRankingMain {

    @Test
    fun runMain() {
        // since this is a personal project and I just need to read the results from the console, this test is how I
        // interact with the program
        val driver = NflRankingMain()

        driver.run()
    }
}