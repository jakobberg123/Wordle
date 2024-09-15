package no.uib.inf101.wordle.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;

public class TestWordleBoard {
  @Test
  public void prettyStringTest() {
    WordleBoard board = new WordleBoard(3, 3);
    board.set(new CellPosition(0, 0), new TileObject('c', LetterCheckResult.EMPTY));
    board.set(new CellPosition(0, 1), new TileObject('a', LetterCheckResult.EMPTY));
    board.set(new CellPosition(0, 2), new TileObject('r', LetterCheckResult.EMPTY));
    board.set(new CellPosition(1, 0), new TileObject('h', LetterCheckResult.EMPTY));
    board.set(new CellPosition(1, 1), new TileObject('i', LetterCheckResult.EMPTY));
    String expected = String.join("\n", new String[] { "car", "hi-", "---" });
    assertEquals(expected, board.prettyString());
  }

  @Test
  public void testIsRowFull() {
    // Test that full rows gets asserted correctly.
    // test
    // not-
    // ----
   

    WordleBoard board = new WordleBoard(3, 4);
    board.set(new CellPosition(0, 0), new TileObject('t', LetterCheckResult.EMPTY));
    board.set(new CellPosition(0, 1), new TileObject('e', LetterCheckResult.EMPTY));
    board.set(new CellPosition(0, 2), new TileObject('s', LetterCheckResult.EMPTY));
    board.set(new CellPosition(0, 3), new TileObject('t', LetterCheckResult.EMPTY));
    board.set(new CellPosition(1, 0), new TileObject('n', LetterCheckResult.EMPTY));
    board.set(new CellPosition(1, 1), new TileObject('o', LetterCheckResult.EMPTY));
    board.set(new CellPosition(1, 2), new TileObject('t', LetterCheckResult.EMPTY));
    


    assertTrue(board.isRowFull(0));
    assertFalse(board.isRowFull(1));
    assertFalse(board.isRowFull(2));

    String expected = String.join("\n", new String[] { "test", "not-", "----"});
    assertEquals(expected, board.prettyString());
  }


}
