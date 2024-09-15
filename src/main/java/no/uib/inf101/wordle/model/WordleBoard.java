package no.uib.inf101.wordle.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class WordleBoard extends Grid<TileObject> {
  private int rows;
  private int cols;

  /**
   * Initializes a new Wordle board with the specified number of rows and columns,
   * filling the grid with '-' to indicate empty cells.
   * 
   * @param rows number of rows.
   * @param cols number of columns.
   */
  public WordleBoard(int rows, int cols) {
    super(rows, cols, new TileObject('-', LetterCheckResult.EMPTY));
    this.rows = rows;
    this.cols = cols;

  }

  /**
   * Returns a string representation of the board for easier visualization and
   * debugging.
   * Each row of the board is shown by a single string separated by newline
   * characters.
   * 
   * @return String representation of board
   */
  protected String prettyString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        stringBuilder.append(get(new CellPosition(row, col)).character());
      }
      stringBuilder.append("\n");
    }
    return stringBuilder.toString().trim();

  }

  /**
   * Takes in a row and check each column, if it does not contain '-'
   * 
   * @param row
   * @return boolean deciding if row is full
   */
  protected boolean isRowFull(int row) {
    for (int col = 0; col < cols; col++) {
      if (get(new CellPosition(row, col)).character() == '-') {
        return false;
      }

    }
    return true;
  }

}
