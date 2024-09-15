package no.uib.inf101.wordle.controller;

import no.uib.inf101.wordle.model.GameState;
import no.uib.inf101.wordle.view.ViewableWordleModel;

public interface ControllableWordleModel extends ViewableWordleModel {

  /**
   * Attempts to move the current cursor position by a specified number of rows
   * and columns.
   * The movement is only performed if it results in a valid position within the
   * board's boundaries.
   *
   * @param deltaRow the number of rows to move the cursor (positive for down,
   *                 negative for up)
   * @param deltaCol the number of columns to move the cursor (positive for right,
   *                 negative for left)
   * @return true if the cursor position is successfully updated, false if the
   *         movement is out of bounds
   */
  public boolean moveBox(int deltaRow, int deltaCol);

  /**
   * Retrieves the current game state. This method reflects any state changes
   * triggered by game actions,
   * starting with MAIN_MENU when the game instance is initialized.
   *
   * @return the current state of the game
   */
  public GameState getGameState();

  /**
   * Sets a specified character value to a cell in the grid at the given row and
   * column.
   *
   * @param row the row index of the cell to modify
   * @param col the column index of the cell to modify
   * @param ch  the character to set in the specified cell
   */
  public void setCellToValue(int row, int col, char ch);

  /**
   * Handles the input of a letter by setting it to the current cell and then
   * moving the cursor to the next column.
   * If the input position is invalid, the input is ignored.
   * 
   * @param ch the character to be input
   */
  void handleLettersInput(char ch);

  /**
   * Verifies the correctness of the current row against the solution word. It
   * first creates a temporary version of
   * the word to check for duplicate letters, then updates each cell's status
   * based on the guess. It finishes by
   * preparing for the next guess by moving to the next row.
   */
  void checkRowCorrectness();

  /**
   * Checks if all cells in the current row have been filled.
   * 
   * @return true if all cells in the row are filled, otherwise false
   */
  boolean checkIfRowFull();

  /**
   * Removes the letter from the current cell and resets its status.
   */
  void removeLetter();

  /**
   * Initializes or restarts the game by generating a new word, resetting the
   * board, and setting the game state to active.
   */
  void startGame();

  /**
   * Sets the current game state to a specified state.
   * 
   * @param currentGameState the new game state to set
   */
  void setGameState(GameState currentGameState);

  /**
   * Checks if a given string is a valid word according to the game's dictionary.
   * 
   * @param guess the word to check
   * @return true if the word is valid, otherwise false
   */
  boolean isWordValid(String guess);

}
