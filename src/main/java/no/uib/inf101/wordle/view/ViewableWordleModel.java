package no.uib.inf101.wordle.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.wordle.model.GameState;
import no.uib.inf101.wordle.model.Statistics;
import no.uib.inf101.wordle.model.TileObject;

public interface ViewableWordleModel {
  /**
   * Retrieves the dimensions of the Wordle game board.
   * 
   * @return the dimensions of the board as a GridDimension object.
   */
  GridDimension getDimension();

  /**
   * Provides an iterable collection of all the tiles currently on the Wordle
   * board.
   * 
   * @return an Iterable object of GridCell containing TileObject instances for
   *         each tile on the board.
   */
  Iterable<GridCell<TileObject>> getTilesOnBoard();

  /**
   * Retrieves the current game state. The initial state is MAIN_MENU, which can
   * change based on gameplay.
   * 
   * @return the current GameState reflecting the state of the game.
   */
  GameState getGameState();

  /**
   * Retrieves the index of the current active row in the game grid.
   * 
   * @return the index of the current row being played.
   */
  int getCurrentRow();

  /**
   * Retrieves the string representation of the current word being guessed in the
   * game.
   * 
   * @return the current word as a string, or null if no current word is set.
   */
  String getCurrentWord();

  /**
   * Determines if the last guess made was invalid, by checking against a list of
   * allowed words.
   * 
   * @return true if the last guess was invalid, otherwise false.
   */
  boolean isLastGuessInvalid();

  /**
   * @return a Statistics object cointaining containing games played, games won,
   *         etc.
   *         Displayed at the end Game screen.
   */
  Statistics getStatistics();

  /**
   * Provides an iterable collection of all the keys currently on the Keyboard.
   * 
   * @return an Iterable object of GridCell containing TileObject instances for
   *         each key on the keyboard.
   */
  Iterable<GridCell<TileObject>> getKeyboardTiles();

  /**
   * Handles the input of a letter by setting it to the current cell and then
   * moving the cursor to the next column.
   * If the input position is invalid, the input is ignored.
   * 
   * @param ch the character to be input
   */
  void handleLettersInput(char ch);

  /**
   * Retrieves the dimensions of the keyBoard.
   * 
   * @return the dimensions of the keyBoard as a GridDimension object.
   */
  GridDimension getKeyboardDimension();

  /**
   * Sets the user-chosen word for the game, if it meets validity criteria.
   * The word must be of the correct length, matching the number of columns on the
   * Wordle board,
   * and must pass the validity check defined by isWordValid.
   * 
   * If the word is valid and of the correct length, the game initializes with
   * this word
   * and marks the game to use a custom word.
   * 
   * @param userWord The word provided by the user to be used as the secret word
   *                 in the game.
   * 
   * @return true if the word was successfully set and the game was started,
   *         false if the word was invalid or not of the required length.
   * 
   * 
   */
  boolean setUserChosenWord(String userWord);

  /**
   * Checks if a given string is a valid word according to the game's dictionary.
   * 
   * @param guess the word to check
   * @return true if the word is valid, otherwise false
   */
  boolean isWordValid(String word);

}
