package no.uib.inf101.wordle.model.word;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestPatternedWordFactory {
  @Test
  public void sanityTestPatternedTetrominoFactory() {
    WordFactory factory = new PatternedWordFactory(new String[]{"HELLO", "TESTS", "PAPER"});

    assertEquals("HELLO",factory.createWord().toStringFormat());
    assertEquals("TESTS",factory.createWord().toStringFormat());
    assertEquals("PAPER",factory.createWord().toStringFormat());

    // Check cyclic behavior
    assertEquals("HELLO", factory.createWord().toStringFormat());
    assertEquals("TESTS", factory.createWord().toStringFormat());
  }

}
