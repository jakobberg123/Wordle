package no.uib.inf101.wordle.model;

public class Statistics {
    private int gamesPlayed;
    private int gamesWon;
    private int totalTries;

    /**
     * Initializes the statistics with 0 games played, 0 games won and 0 total
     * tries.
     */
    public Statistics() {
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.totalTries = 0;

    }

    /**
     * Updates the game statistics based on the outcome of a played game.
     * Increments the total count of games played, updates the count of games won if
     * the game was won,
     * and accumulates the total number of tries across all games.
     *
     * @param isWin         a boolean value indicating if the game was won (true) or
     *                      not (false).
     * @param numberOfTries the number of tries taken in the game that just
     *                      concluded.
     */
    protected void updateGameStatistics(boolean isWin, int numberOfTries) {
        gamesPlayed++;
        if (isWin) {
            gamesWon++;
        }
        if (numberOfTries >= 0) {
            totalTries += numberOfTries;
        }
    }

    /**
     * Retrieves the total number of games played.
     *
     * @return the total number of games played.
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Retrieves the total number of games won.
     *
     * @return the total number of games won.
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Calculates and returns the average number of tries per game won. If no games
     * were won, returns 0.
     *
     * @return the average number of tries it has taken to win a game, or 0 if no
     *         games have been won.
     */
    public double getAverageTriesPerGameWon() {
        return gamesWon == 0 ? 0 : (double) totalTries / gamesWon;
    }

    /**
     * Calculates and returns the win rate as a percentage of games played.
     * If no games have been played, returns 0.
     *
     * @return the win rate as a percentage of the total games played.
     */
    public double getWinRate() {
        return gamesPlayed == 0 ? 0 : 100.0 * gamesWon / gamesPlayed;
    }

}
