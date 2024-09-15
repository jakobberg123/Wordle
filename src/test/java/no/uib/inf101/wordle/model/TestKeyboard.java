package no.uib.inf101.wordle.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;


import no.uib.inf101.grid.CellPosition;

public class TestKeyboard {
    private Keyboard keyboard;

    @BeforeEach
    public void setUp(){
        this.keyboard = new Keyboard(3, 10);

    }
    @Test
    public void testInitializingOfKeyboard() {
        String[] keyRows = {
            "QWERTYUIOP",
            "ASDFGHJKL",
            "ZXCVBNM"
        };

        // Test each row
        for (int row = 0; row < keyRows.length; row++) {
            String keys = keyRows[row];
            for (int col = 0; col < keys.length(); col++) {
                char expectedChar = keys.charAt(col);
                TileObject tile = keyboard.get(new CellPosition(row, col));
                assertNotNull(tile, "Tile should not be null");
                assertEquals(expectedChar, tile.character(), "Check character at position");
                assertEquals(LetterCheckResult.EMPTY, tile.result(), "Check status is EMPTY");
            }
        }
    }
    @Test
    public void testUpdateKeyboard(){
        keyboard.updateKeyStatus('Q', LetterCheckResult.CORRECT_POSITION);
        keyboard.updateKeyStatus('R', LetterCheckResult.CORRECT_POSITION);
        keyboard.updateKeyStatus('Z', LetterCheckResult.WRONG_POSITION);
        
        assertEquals(LetterCheckResult.CORRECT_POSITION, keyboard.get(new CellPosition(0, 0)).result(), "Check Q is CORRECT_POSITION");
        assertEquals(LetterCheckResult.CORRECT_POSITION, keyboard.get(new CellPosition(0, 3)).result(), "Check R is CORRECT_POSITION");
        assertEquals(LetterCheckResult.WRONG_POSITION, keyboard.get(new CellPosition(2, 0)).result(), "Check Z is WRONG_POSITION");

        // Check that other keys are still in the default state
        assertEquals(LetterCheckResult.EMPTY, keyboard.get(new CellPosition(0, 1)).result(), "Check W is still EMPTY");
    }
    @Test
public void testMultipleUpdatesSameKey() {
    keyboard.updateKeyStatus('A', LetterCheckResult.WRONG_POSITION);
    keyboard.updateKeyStatus('A', LetterCheckResult.CORRECT_POSITION);
    assertEquals(LetterCheckResult.CORRECT_POSITION, keyboard.get(new CellPosition(1, 0)).result(), "A should be in CORRECT_POSITION after multiple updates");
}
@Test
public void testAllKeysUpdate() {
    // Define the keyboard layout explicitly
    String[] keyRows = {
        "QWERTYUIOP",  // row 0
        "ASDFGHJKL",   // row 1
        "ZXCVBNM"      // row 2
    };

    // Update the status of all keys to NOT_IN_WORD
    for (char key : "QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray()) {
        keyboard.updateKeyStatus(key, LetterCheckResult.NOT_IN_WORD);
    }

    // Assert that all keys have the status updated to NOT_IN_WORD
    for (int row = 0; row < keyRows.length; row++) {
        String keys = keyRows[row];
        for (int col = 0; col < keys.length(); col++) {
            TileObject tile = keyboard.get(new CellPosition(row, col));
            assertNotNull(tile, "Tile should not be null");
            assertEquals(keys.charAt(col), tile.character(), "Check character at position matches expected");
            assertEquals(LetterCheckResult.NOT_IN_WORD, tile.result(), "Check status is updated to NOT_IN_WORD");
        }
    }
}

}
