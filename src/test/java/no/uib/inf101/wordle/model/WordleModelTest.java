package no.uib.inf101.wordle.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.wordle.model.word.PatternedWordFactory;
import no.uib.inf101.wordle.model.word.WordFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;

public class WordleModelTest {
    private WordleModel model;
    private WordleBoard board;
    private WordFactory wordFactory;

    @BeforeEach
    public void setUp() {
        board = new WordleBoard(6, 5);  // Assuming a 6x5 board (rows x cols)
        wordFactory = new PatternedWordFactory(new String[]{"HELLO", "WORLD", "AGAIN"});
        model = new WordleModel(board, wordFactory);
        model.startGame();  // Start the game which resets everything
    }

    @Test
    public void testGameStart() {
        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
        assertEquals(0, model.getCurrentRow());
        assertEquals(0, model.getCurrentCol());
        assertNotNull(model.getCurrentWord());
        assertEquals("HELLO", model.getCurrentWord());
    }
    @Test
    public void testGameRestart(){
        model.handleLettersInput('H');
        model.handleLettersInput('E');
        model.handleLettersInput('L');
        model.handleLettersInput('L');
        model.handleLettersInput('O');
        model.checkRowCorrectness();
        assertEquals(GameState.GAME_WON, model.getGameState());
        model.startGame();

        // The board should restart and have the default values, and the next word should be chosen.
        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
        assertEquals(0, model.getCurrentRow());
        assertEquals(0, model.getCurrentCol());
        assertEquals("WORLD", model.getCurrentWord());

        // Test that the board has been cleared 
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                char cellChar =board.get(new CellPosition(row, col)).character();
                LetterCheckResult cellResult =board.get(new CellPosition(row, col)).result();
                assertEquals('-', cellChar);
                assertEquals(cellResult, LetterCheckResult.EMPTY);
            }
        }
    }

    @Test
    public void testHandleLettersInput() {
        model.handleLettersInput('H');
        model.handleLettersInput('E');
        model.handleLettersInput('L');
        model.handleLettersInput('L');
        model.handleLettersInput('O');
        assertTrue(model.checkIfRowFull());
        model.checkRowCorrectness();
        assertEquals(GameState.GAME_WON, model.getGameState());
    }
    @Test
    public void testRowNotFull(){
        model.handleLettersInput('h');
        model.handleLettersInput('-'); // Indicating empty cell
        model.handleLettersInput('l');
        model.handleLettersInput('l');
        model.handleLettersInput('o');
        assertFalse(model.checkIfRowFull());
    }

    @Test
    public void testInvalidWordHandling() {
        model.handleLettersInput('a');
        model.handleLettersInput('b');
        model.handleLettersInput('c');
        model.handleLettersInput('d');
        model.handleLettersInput('e');
        model.checkRowCorrectness();
        assertTrue(model.isLastGuessInvalid());
    }
    @Test
    public void testValidWordHandling() {
        model.handleLettersInput('b');
        model.handleLettersInput('r');
        model.handleLettersInput('e');
        model.handleLettersInput('a');
        model.handleLettersInput('d');
        model.checkRowCorrectness();
        assertFalse(model.isLastGuessInvalid());
    }

    @Test
    public void testGameEndOnRowOverflow() {
        for (int i = 0; i < 6; i++) {  // Fill all rows
            for (int j = 0; j < 5; j++) {
                model.handleLettersInput('t'); 
                model.handleLettersInput('e'); 
                model.handleLettersInput('s'); 
                model.handleLettersInput('t'); 
                model.handleLettersInput('s');  // Incorrect guess
            }
            model.checkRowCorrectness();
        }
        assertEquals(GameState.GAME_OVER, model.getGameState());
    }

@Test
public void testMoveBoxWithValidMoves() {
    model.moveBox(1, 0); // Move down one row
    assertEquals(1, model.getCurrentRow(), "Should move to the next row.");
    model.moveBox(0, 1); // Move right one column
    assertEquals(1, model.getCurrentCol(), "Should move to the next column.");
}

@Test
public void testMoveBoxInvalidMoves() {
// Assume starting at position 0,0 and the board is 6x5 (rows x cols)
assertFalse(model.moveBox(-1, 0), "Moving up when already at the top should fail.");
assertFalse(model.moveBox(0, -1), "Moving left when already at the first column should fail.");
assertFalse(model.moveBox(6, 0)); // Set to last row
assertFalse(model.moveBox(0, 6)); // Set to last column

}

@Test
public void testSetGameState() {
model.setGameState(GameState.GAME_OVER);
assertEquals(GameState.GAME_OVER, model.getGameState(), "Game state should be set to GAME_OVER.");
}


@Test
public void testRemoveLetter() {
model.handleLettersInput('H');
model.handleLettersInput('O');
model.moveBox(0, -1);
model.removeLetter();
model.handleLettersInput('E');
model.handleLettersInput('L');
model.handleLettersInput('L');
model.handleLettersInput('O');
model.checkRowCorrectness();

assertEquals(GameState.GAME_WON, model.getGameState());


}
@Test
public void testInputBeyondColumnLimit() {
    for (int i = 0; i < 5; i++) { // Correct number of columns
        model.handleLettersInput('A');
    }
    model.handleLettersInput('B'); // This should not be accepted
    assertEquals("AAAAA", model.buildGuessString(), 
    "Extra input beyond column limit should not affect the board");
}



@Test
public void testUserChosenWord(){
    model.setUserChosenWord("house");
    model.setCellToValue(0, 0, 'H');
    model.setCellToValue(0, 1, 'O');
    model.setCellToValue(0, 2, 'U');
    model.setCellToValue(0, 3, 'S');
    model.setCellToValue(0, 4, 'E');
    assertEquals("HOUSE", model.getCurrentWord());
    model.checkRowCorrectness();
    assertEquals(GameState.GAME_WON, model.getGameState());

    // Checks that it works for both upper and lowercase input.
    model.setUserChosenWord("PAPER");
    assertEquals("PAPER", model.getCurrentWord());

}
@Test
public void testInvalidUserChosenWord(){
    // Test for invalid word format.
    assertEquals(false, model.setUserChosenWord("blablabla"));

    // Checks that an invalid word wont get set as the current word.
    model.setUserChosenWord("blablabla");
    assertEquals("HELLO", model.getCurrentWord());

    // Word not in dictionairy cant get set as current word.
    model.setUserChosenWord("abcde");
    assertEquals("HELLO", model.getCurrentWord());
    
    
}

@Test
public void testSpecialCharacterInputHandling() {
    // Assuming the game should ignore or reject non-alphabet characters
    model.handleLettersInput('#');
    model.handleLettersInput('!');
    model.handleLettersInput('-');
    model.handleLettersInput('L');
    model.handleLettersInput('L');
    model.handleLettersInput('O');
    model.checkRowCorrectness();
    
    // Check that the row does not consider non-alphabet characters
    assertFalse(model.checkIfRowFull(), "Row should not be full with special characters ignored.");
    assertFalse(model.isWordValid(model.buildGuessString()), "Guess with special characters should be invalid.");
}
@Test
public void testMultipleGameRestarts() {
    model.startGame();
    model.handleLettersInput('H');
    model.checkRowCorrectness();
    model.startGame();  // Restart the game once
    model.startGame();  // Restart the game again
    assertEquals(GameState.ACTIVE_GAME, model.getGameState(), "Game state should reset to ACTIVE_GAME after restart.");
    assertTrue(model.getCurrentRow() == 0 && model.getCurrentCol() == 0, "Board position should reset after game restart.");
}
}