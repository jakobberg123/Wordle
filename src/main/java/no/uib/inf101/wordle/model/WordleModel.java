package no.uib.inf101.wordle.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.wordle.controller.ControllableWordleModel;
import no.uib.inf101.wordle.model.word.Word;
import no.uib.inf101.wordle.model.word.WordFactory;
import no.uib.inf101.wordle.model.word.WordLoader;


import java.util.List;



public class WordleModel implements ControllableWordleModel {
  private WordleBoard wordleBoard;
  private WordFactory wordFactory;
  private Word currentWord;
  private GameState gameState;
  private Statistics statistics;
  private Keyboard keyboard;
  private int currentRow = 0;
  private int currentCol = 0;
  private List<String> validWords;
  private boolean lastGuessWasInvalid = false;
  private boolean usingCustomWord = false;
  private WordLoader wordLoader;

  /**
   * Constructs a new WordleModel with a specified Wordle board and Word factory.
   * Initializes the game state to MAIN_MENU, creates a keyBoard and a statistic
   * object,
   * and loads the valid guessing words.
   * 
   *
   * @param wordleBoard The Wordle board to be used by this model.
   * @param wordFactory The factory for creating random words.
   */
  public WordleModel(WordleBoard wordleBoard, WordFactory wordFactory) {
    this.wordleBoard = wordleBoard;
    this.wordFactory = wordFactory;
    this.gameState = GameState.MAIN_MENU;
    this.keyboard = new Keyboard(3, 10);
    this.statistics = new Statistics();
    this.wordLoader = new WordLoader();
    this.validWords = wordLoader.loadValidWords("wordle-allowed-guesses.txt");
    
  }

  @Override
  public boolean setUserChosenWord(String userWord) {
    if (isWordValid(userWord) && userWord.length() == wordleBoard.cols()) {
      currentWord = new Word(userWord.toUpperCase());
      usingCustomWord = true;
      startGame(); // Start the game with the user's chosen word
      return true;
    }
    return false; // Word was invalid or the wrong length
  }

  @Override
  public GridDimension getDimension() {
    return new GridDimension() {
      @Override
      public int rows() {
        return wordleBoard.rows(); // Number of rows in the keyboard
      }

      @Override
      public int cols() {
        return wordleBoard.cols(); // Number of columns in the keyboard
      }
    };
  }

  @Override
  public Statistics getStatistics() {
    return statistics;
  }


  @Override
  public GameState getGameState() {
    return gameState;
  }

  @Override
  public void startGame() {
    if (!usingCustomWord) {
      currentWord = wordFactory.createWord();
    }
    gameState = GameState.ACTIVE_GAME;
    clearBoard();
    this.keyboard = new Keyboard(3, 10);
    currentRow = 0;
    currentCol = 0;
  }

  @Override
  public void setGameState(GameState currentGameState) {
    this.gameState = currentGameState;
  }

  @Override
  public boolean moveBox(int deltaRow, int deltaCol) {
    int newRow = currentRow + deltaRow;
    int newCol = currentCol + deltaCol;
    if (newRow >= wordleBoard.rows()) {
      gameState = GameState.GAME_OVER;
    }

    if (isPositionValid(newRow, newCol)) {
      // Update to the new positions if they are valid
      currentRow = newRow;
      currentCol = newCol;
      return true; // Successfully moved
    } else {
      return false; // Move failed due to out of bounds
    }
  }

  @Override
  public Iterable<GridCell<TileObject>> getTilesOnBoard() {
    return wordleBoard;

  }

  @Override
  public void handleLettersInput(char ch) {
    lastGuessWasInvalid = false;
    if (isPositionValid(currentRow, currentCol)) {
      setCellToValue(currentRow, currentCol, ch);

      // Move to the next column
      currentCol++;
    }
  }

  private boolean isPositionValid(int row, int col) {
    return row >= 0 && row < wordleBoard.rows() && col >= 0 && col < wordleBoard.cols();
  }

  @Override
  public void setCellToValue(int row, int col, char ch) {
    wordleBoard.set(new CellPosition(row, col), new TileObject(ch, LetterCheckResult.EMPTY));
  }

  @Override
  public void checkRowCorrectness() {
    // Prepare the temporary word for comparison
    currentWord.createTempWord();

    // Creates a string from the char value in columns.
    String guess = buildGuessString();

    // Firstly check if the word is valid
    if (!isWordValid(guess)) {
      handleInvalidGuess();
      return;
    }

    // Then check if the word is correct
    if (guess.equals(currentWord.toStringFormat())) {
      gameState = GameState.GAME_WON;
      statistics.updateGameStatistics(true, currentRow + 1);
    }

    // Then check if any letters are correctly placed
    checkCorrectPlacements(guess);

    // Lastly checks if any letters are misplaced.
    checkMisplacedLetters(guess);

    // Prepare for the next guess if the game is still active.
    if (gameState == GameState.ACTIVE_GAME) {
      prepareForNextGuess(guess);
    }
  }

  /**
   * Builds a string from the characters in the columns of current row.
   * 
   * @return String representation of the columns.
   */
  protected String buildGuessString() {
    StringBuilder guessBuilder = new StringBuilder();
    for (int col = 0; col < wordleBoard.cols(); col++) {
      char guessChar = wordleBoard.get(new CellPosition(currentRow, col)).character();
      guessBuilder.append(guessChar);
    }
    return guessBuilder.toString();
  }

  // Checks for all columns in row if they have the same value in the same index
  // as current word
  private void checkCorrectPlacements(String guess) {
    for (int col = 0; col < guess.length(); col++) {
        char guessChar = guess.charAt(col);
        if (currentWord.checkCharacterAtPosition(guessChar, col)) {
            wordleBoard.set(new CellPosition(currentRow, col), new TileObject(guessChar, LetterCheckResult.CORRECT_POSITION));
            keyboard.updateKeyStatus(guessChar, LetterCheckResult.CORRECT_POSITION);
            currentWord.removeCharacterAtPosFromTempWord(guessChar, col);
        }
    }
}

  // Checks for all columns in row if they have the same value as any letter in
  // current word.
  private void checkMisplacedLetters(String guess) {
    for (int col = 0; col < guess.length(); col++) {
        char guessChar = guess.charAt(col);
        TileObject tile = wordleBoard.get(new CellPosition(currentRow, col));

        if (tile.result() != LetterCheckResult.CORRECT_POSITION) {
            if (currentWord.isCharacterInTempWord(guessChar)) {
                wordleBoard.set(new CellPosition(currentRow, col), new TileObject(guessChar, LetterCheckResult.WRONG_POSITION));
                keyboard.updateKeyStatus(guessChar, LetterCheckResult.WRONG_POSITION);
                currentWord.removeCharacterFromTempWord(guessChar);
            } else {
                wordleBoard.set(new CellPosition(currentRow, col), new TileObject(guessChar, LetterCheckResult.NOT_IN_WORD));
                keyboard.updateKeyStatus(guessChar, LetterCheckResult.NOT_IN_WORD);
            }
        }
    }
}
  // Move down one row and to first column if we haven´t lost.
  private void prepareForNextGuess(String guess) {
    currentRow++;
    if (currentRow >= wordleBoard.rows() && !guess.equals(currentWord.toString())) {
      gameState = GameState.GAME_OVER;
      statistics.updateGameStatistics(false, 0);
    }
    currentCol = 0;
  }

  @Override
  public void removeLetter() {
    setCellToValue(currentRow, currentCol, '-');
  }

  @Override
  public boolean checkIfRowFull() {
    return wordleBoard.isRowFull(currentRow);
  }

  @Override
  public int getCurrentRow() {
    return currentRow;
  }

  /**
   * Gets the current column
   * 
   * @return current column
   */
  protected int getCurrentCol() {
    return currentCol;
  }

  @Override
  public String getCurrentWord() {
    if (currentWord != null) {
      return currentWord.toStringFormat();
    } else {
      return null;
    }
  }

  private void clearBoard() {
    usingCustomWord = false;
    for (int col = 0; col < wordleBoard.cols(); col++) {
      for (int row = 0; row < wordleBoard.rows(); row++) {
        wordleBoard.set(new CellPosition(row, col), new TileObject('-', LetterCheckResult.EMPTY));

      }
    }
  }

  @Override
  public boolean isWordValid(String guess) {
    return validWords.contains(guess.toLowerCase());

  }

  private void handleInvalidGuess() {
    lastGuessWasInvalid = true;
    clearCurrentRowWithInvalidIndicator();

  }

  private void clearCurrentRowWithInvalidIndicator() {
    for (int col = 0; col < wordleBoard.cols(); col++) {
      TileObject currentTile = wordleBoard.get(new CellPosition(currentRow, col));
      TileObject updatedTile = new TileObject(currentTile.character(), LetterCheckResult.INVALID);
      wordleBoard.set(new CellPosition(currentRow, col), updatedTile);
    }
  }

  @Override
  public boolean isLastGuessInvalid() {
    return lastGuessWasInvalid;
  }

  @Override
  public Iterable<GridCell<TileObject>> getKeyboardTiles() {
    return keyboard; 
  }

  @Override
  public GridDimension getKeyboardDimension() {
    return new GridDimension() {
      @Override
      public int rows() {
        return 3; // Number of rows in the keyboard
      }

      @Override
      public int cols() {
        return 10; // Number of columns in the keyboard
      }
    };
  }

}