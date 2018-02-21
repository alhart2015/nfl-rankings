package org.ahart.nfl.rankings

import junit.framework.Assert.assertFalse
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestGame {

    @Test
    fun testAssessImpact() {

        val recordIsIrrelevant = 0

        val greatRating = 1800
        val greatTeamName = "great-team"
        val greatTeam = Team(
                greatTeamName,
                recordIsIrrelevant,
                recordIsIrrelevant,
                greatRating
        )

        val slightlyWorseRating = 1750
        val slightlyWorseTeamName = "not-as-good-as-greatTeam"
        val slightlyWorseTeam = Team(
                slightlyWorseTeamName,
                recordIsIrrelevant,
                recordIsIrrelevant,
                slightlyWorseRating
        )

        val terribleRating = 1300
        val terribleTeamName = "terrible-team"
        val terribleTeam = Team(
                terribleTeamName,
                recordIsIrrelevant,
                recordIsIrrelevant,
                terribleRating
        )

        val weekIrrelevant = "week-irrelevant"
        val dayIrrelevant = "day-irrelevant"
        val dateIrrelevant = "date-irrelevant"
        val timeIrrelevant = "time-irrelevant"
        val blowoutWinnerScore = 30
        val blowoutLoserScore = 3
        val closeWinnerScore = 20
        val closeLoserScore = 17

        // a great team blowing out a terrible team should have a small impact
        val blowoutBetweenGreatAndTerrible = Game(
                weekIrrelevant,
                dayIrrelevant,
                dateIrrelevant,
                timeIrrelevant,
                greatTeamName,
                terribleTeamName,
                greatTeamName,
                blowoutWinnerScore,
                blowoutLoserScore
        )

        val (greatAfterBlowout, terribleAfterBlowout) = blowoutBetweenGreatAndTerrible.assessImpact(greatTeam, terribleTeam)

        // any change to the impact formula will cause these to change, of course
        val expectedGreatRating =  1801
        val expectedTerribleRating = 1299

        assertEquals(expectedGreatRating, greatAfterBlowout.rating)
        assertEquals(1, greatAfterBlowout.wins)
        assertEquals(0, greatAfterBlowout.losses)

        assertEquals(expectedTerribleRating, terribleAfterBlowout.rating)
        assertEquals(0, terribleAfterBlowout.wins)
        assertEquals(1, terribleAfterBlowout.losses)

        // a terrible team barely beating a great team should have a big impact
        val closeUpset = Game(
                weekIrrelevant,
                dayIrrelevant,
                dateIrrelevant,
                timeIrrelevant,
                terribleTeamName,
                greatTeamName,
                greatTeamName,
                closeWinnerScore,
                closeLoserScore
        )

        val (greatAfterUpset, terribleAfterUpset) = closeUpset.assessImpact(greatTeam, terribleTeam)
        val expectedGreatRatingAfterUpset = 1634 // any change to the impact formula will cause these to change, of course
        val expectedTerribleRatingAfterUpset = 1466
        assertEquals(expectedGreatRatingAfterUpset, greatAfterUpset.rating)
        assertEquals(0, greatAfterUpset.wins)
        assertEquals(1, greatAfterUpset.losses)
        assertEquals(expectedTerribleRatingAfterUpset, terribleAfterUpset.rating)
        assertEquals(1, terribleAfterUpset.wins)
        assertEquals(0, terribleAfterUpset.losses)
    }

    @Test
    fun testUpset() {
        val recordIsIrrelevant = 0

        val greatRating = 1800
        val greatTeamName = "great-team"
        val greatTeam = Team(
                greatTeamName,
                recordIsIrrelevant,
                recordIsIrrelevant,
                greatRating
        )

        val slightlyWorseRating = 1750
        val slightlyWorseTeamName = "not-as-good-as-greatTeam"
        val slightlyWorseTeam = Team(
                slightlyWorseTeamName,
                recordIsIrrelevant,
                recordIsIrrelevant,
                slightlyWorseRating
        )

        val terribleRating = 1300
        val terribleTeamName = "terrible-team"
        val terribleTeam = Team(
                terribleTeamName,
                recordIsIrrelevant,
                recordIsIrrelevant,
                terribleRating
        )

        val weekIrrelevant = "week-irrelevant"
        val dayIrrelevant = "day-irrelevant"
        val dateIrrelevant = "date-irrelevant"
        val timeIrrelevant = "time-irrelevant"
        val blowoutWinnerScore = 30
        val blowoutLoserScore = 3
        val closeWinnerScore = 20
        val closeLoserScore = 17

        // greatTeam beats terribleTeam. not an upset
        val greatBeatsTerrible = Game(
                weekIrrelevant,
                dayIrrelevant,
                dateIrrelevant,
                timeIrrelevant,
                greatTeamName,
                terribleTeamName,
                greatTeamName,
                closeWinnerScore,
                closeLoserScore
        )
        assertFalse(greatBeatsTerrible.wasUpset(greatTeam, terribleTeam))
        // still not an upset regardless of the order of the args
        assertFalse(greatBeatsTerrible.wasUpset(terribleTeam, greatTeam))

        // terribleTeam beats greatTeam. upset
        val terribleBeatsGreat = Game(
                weekIrrelevant,
                dayIrrelevant,
                dateIrrelevant,
                timeIrrelevant,
                terribleTeamName,
                greatTeamName,
                terribleTeamName,
                blowoutWinnerScore,
                blowoutLoserScore
        )
        assertTrue(terribleBeatsGreat.wasUpset(greatTeam, terribleTeam))
        // still an upset regardless of the order of the args
        assertTrue(terribleBeatsGreat.wasUpset(terribleTeam, greatTeam))

        // greatTeam beats slightlyWorseTeam. not an upset
        val greatBeatsWorse = Game(
                weekIrrelevant,
                dayIrrelevant,
                dateIrrelevant,
                timeIrrelevant,
                greatTeamName,
                slightlyWorseTeamName,
                greatTeamName,
                closeWinnerScore,
                closeLoserScore
        )
        assertFalse(greatBeatsWorse.wasUpset(greatTeam, slightlyWorseTeam))
        // still not an upset regardless of the order of the args
        assertFalse(greatBeatsWorse.wasUpset(slightlyWorseTeam, greatTeam))

        // slightlyWorseTeam beats greatTeam. upset
        val worseBeatsGreat = Game(
                weekIrrelevant,
                dayIrrelevant,
                dateIrrelevant,
                timeIrrelevant,
                slightlyWorseTeamName,
                greatTeamName,
                slightlyWorseTeamName,
                blowoutWinnerScore,
                blowoutLoserScore
        )
        assertTrue(worseBeatsGreat.wasUpset(greatTeam, slightlyWorseTeam))
        // still an upset regardless of the order of the args
        assertTrue(worseBeatsGreat.wasUpset(slightlyWorseTeam, greatTeam))

        // slightlyWorstTeam beats terribleTeam. not an upset
        val worseBeatsTerrible = Game(
                weekIrrelevant,
                dayIrrelevant,
                dateIrrelevant,
                timeIrrelevant,
                slightlyWorseTeamName,
                terribleTeamName,
                slightlyWorseTeamName,
                closeWinnerScore,
                closeLoserScore
        )
        assertFalse(worseBeatsTerrible.wasUpset(terribleTeam, slightlyWorseTeam))
        // still not an upset regardless of the order of the args
        assertFalse(worseBeatsTerrible.wasUpset(slightlyWorseTeam, terribleTeam))

        // terribleTeam beats slightlyWorseTeam (not great names here). upset
        val terribleBeatsWorse = Game(
                weekIrrelevant,
                dayIrrelevant,
                dateIrrelevant,
                timeIrrelevant,
                terribleTeamName,
                slightlyWorseTeamName,
                terribleTeamName,
                blowoutWinnerScore,
                blowoutLoserScore
        )
        assertTrue(terribleBeatsWorse.wasUpset(terribleTeam, slightlyWorseTeam))
        // still an upset regardless of the order of the args
        assertTrue(terribleBeatsWorse.wasUpset(slightlyWorseTeam, terribleTeam))
    }

//    @Test(expected = RuntimeException.class)
//            fun wasUpsetThrowsExceptionForTeam1() {
//// team1 is wrong. throw exception
//        val wrongTeam = Game(
//                weekIrrelevant,
//                dayIrrelevant,
//                dateIrrelevant,
//                timeIrrelevant,
//                terribleTeamName,
//                slightlyWorseTeamName,
//                terribleTeamName,
//                blowoutWinnerScore,
//                blowoutLoserScore
//        )
//        assertThrows (terribleBeatsWorse.wasUpset(terribleTeam, slightlyWorseTeam))
//
//        // team2 is wrong. throw exception
//    }
}