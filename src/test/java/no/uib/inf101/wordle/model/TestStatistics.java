package no.uib.inf101.wordle.model;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStatistics {
    private Statistics statistics;

    @BeforeEach
    public void setUp() {
        this.statistics = new Statistics();
    }

    @Test
    public void testInitializingOfGameStatistics() {
        assertEquals(0, statistics.getGamesWon());
        assertEquals(0.0, statistics.getWinRate());
        assertEquals(0, statistics.getGamesPlayed());
        assertEquals(0.0, statistics.getAverageTriesPerGameWon());
    }

    @Test
    public void testUpdateStatistics() {
        statistics.updateGameStatistics(true, 4);
        statistics.updateGameStatistics(true, 4);
        statistics.updateGameStatistics(false, 0);
        statistics.updateGameStatistics(false, 0);
        assertEquals(4, statistics.getGamesPlayed());
        assertEquals(2, statistics.getGamesWon());
        assertEquals(50.0, statistics.getWinRate());
        assertEquals(4.0, statistics.getAverageTriesPerGameWon());
    }

    @Test
    public void testAllGamesLost() {
        statistics.updateGameStatistics(false, 3);
        statistics.updateGameStatistics(false, 2);
        assertEquals(0, statistics.getGamesWon(), "No games should be won");
        assertEquals(2, statistics.getGamesPlayed(), "Two games should be played");
        assertEquals(0.0, statistics.getAverageTriesPerGameWon(), "Average tries per game won should be 0");
    }

    @Test
    public void testLargeNumberOfGames() {
        for (int i = 0; i < 10000; i++) {
            statistics.updateGameStatistics(true, 3);
        }
        assertEquals(10000, statistics.getGamesPlayed());
        assertEquals(10000, statistics.getGamesWon());
        assertEquals(100.0, statistics.getWinRate());
        assertEquals(3.0, statistics.getAverageTriesPerGameWon());
    }

    @Test
    public void testWinRatePrecision() {
        statistics.updateGameStatistics(true, 5);
        statistics.updateGameStatistics(false, 0);
        statistics.updateGameStatistics(false, 0);
        statistics.updateGameStatistics(false, 0);
        statistics.updateGameStatistics(false, 0);
        assertEquals(20.0, statistics.getWinRate(), "Win rate should be calculated with correct precision");
    }

    @Test
    public void testVaryingTriesPerGameWon() {
        statistics.updateGameStatistics(true, 6);
        statistics.updateGameStatistics(true, 2);
        statistics.updateGameStatistics(true, 4);
        assertEquals(4.0, statistics.getAverageTriesPerGameWon(),
                "Average tries per game won should handle varying numbers of tries");
    }

    @Test
    public void testInvalidUpdateOfStats() {
        statistics.updateGameStatistics(true, -1);
        statistics.updateGameStatistics(true, -1);
        statistics.updateGameStatistics(true, -1);
        assertEquals(0, statistics.getAverageTriesPerGameWon(),
                "Average tries cant be less than zero");
    }

    @Test
    public void testNegativeTriesDoNotAffectTotal() {
        statistics.updateGameStatistics(true, -5);
        assertEquals(0, statistics.getAverageTriesPerGameWon(),
                "Negative tries should not affect the total tries count.");
    }

}
