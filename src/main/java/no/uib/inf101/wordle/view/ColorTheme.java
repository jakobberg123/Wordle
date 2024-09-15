package no.uib.inf101.wordle.view;

import java.awt.Color;

import no.uib.inf101.wordle.model.LetterCheckResult;

/**
 * Defines the color scheme for different components of the Wordle game view.
 */
public interface ColorTheme {

    /**
     * Determines the color of a cell in the game grid based on the letter checking
     * result.
     * 
     * @param result the outcome of the letter check (correct position, wrong
     *               position, not in word, or invalid)
     * @return the color corresponding to the result
     */
    Color getCellColor(LetterCheckResult result);

    /**
     * Retrieves the color used for the game frame.
     * 
     * @return the color of the frame
     */
    Color getFrameColor();

    /**
     * Retrieves the background color of the game view.
     * 
     * @return the background color
     */
    Color getBackgroundColor();

    /**
     * Retrieves the color used for letters in the game grid.
     * 
     * @return the color of the letters
     */
    Color getLetterColor(Color color);
}
