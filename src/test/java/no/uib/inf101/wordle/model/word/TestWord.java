package no.uib.inf101.wordle.model.word;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWord {
    private Word word;

    @BeforeEach
    public void setup() {
        word = new Word("hello");
        word.createTempWord(); // Prepare the temp word for manipulation
    }

    @Test
    public void testToStringFormat() {
        assertEquals("hello", word.toStringFormat());
    }

    @Test
    public void testRemoveCharacterAtPosFromTempWord() {
        word.removeCharacterAtPosFromTempWord('e', 1);
        assertFalse(word.isCharacterInTempWord('e'), "Character 'e' should be marked as used and not found.");
    }

    @Test
    public void testRemoveCharacterFromTempWord() {
        word.removeCharacterFromTempWord('l');
        assertEquals("he*lo", word.tempWord.toString(), "First 'l' should be replaced with '*'.");
    }

    @Test
    public void testIsCharacterInTempWord() {
        assertTrue(word.isCharacterInTempWord('h'), "Character 'h' should be present in temp word.");
        word.removeCharacterFromTempWord('h');
        assertFalse(word.isCharacterInTempWord('h'),
                "Character 'h' should be marked as used and not found after removal.");
    }

    @Test
    public void testCheckCharacterAtPosition() {
        assertTrue(word.checkCharacterAtPosition('h', 0), "Character at position 0 should be 'h'.");
        assertFalse(word.checkCharacterAtPosition('h', 1), "Character at position 1 should not be 'h'.");
    }

    @Test
    public void testEmptyWord() {
        word = new Word("");
        word.createTempWord();
        assertTrue(word.toStringFormat().isEmpty(), "Word should be empty.");
        assertFalse(word.isCharacterInTempWord('a'), "No characters should be found in an empty word.");
    }

    @Test
    public void testRepeatedCharacters() {
        word = new Word("level");
        word.createTempWord();
        word.removeCharacterFromTempWord('l');
        assertEquals("*evel", word.tempWord.toString(), "First 'l' should be replaced with '*'.");
        word.removeCharacterFromTempWord('l');
        assertEquals("*eve*", word.tempWord.toString(), "Second l should be replaced with '*'.");
    }

    @Test
    public void testConsistencyAfterCharacterRemoval() {
        word.removeCharacterAtPosFromTempWord('l', 2);
        assertTrue(word.checkCharacterAtPosition('l', 3), "Second 'l' should still exist and be correct.");
        word.removeCharacterFromTempWord('l');
        assertFalse(word.isCharacterInTempWord('l'), "All 'l' characters should now be marked as used.");
    }

}
