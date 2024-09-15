package no.uib.inf101.wordle.model.word;

public interface WordFactory {
  /**
   * Creates a new random word from a list.
   * 
   * @throws IllegalStateException if it fails to load valid words
   * @return a new {@link Word} object
   */
  Word createWord();
}
