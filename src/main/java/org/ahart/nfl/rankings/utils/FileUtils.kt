package org.ahart.nfl.rankings.utils

import org.ahart.nfl.rankings.Game
import org.ahart.nfl.rankings.createGameFromLine

fun readResultsFile(filename: String): List<Game> {
    val classloader = Thread.currentThread().contextClassLoader
    val inputStream = classloader.getResourceAsStream(filename)

    val gameList = mutableListOf<Game>()

    inputStream.bufferedReader().useLines { lines ->
        lines.forEach {
            val splitLine = it.split(",")
            try {
                val gameFromLine = createGameFromLine(splitLine)
                gameList.add(gameFromLine)
            } catch (e: NumberFormatException) {
                // this is a header row, don't try to add it
            }
        }
    }
    return gameList
}

