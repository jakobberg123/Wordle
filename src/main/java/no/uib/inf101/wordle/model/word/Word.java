package no.uib.inf101.wordle.model.word;

public class Word {
  private String word;
  StringBuilder tempWord;

  /**
   * Takes in a string, and sets it as the current word object.
   * 
   * @param word
   */
  public Word(String word) {
    this.word = word;
  }

  /**
   * @return String representation of current word
   */
  public String toStringFormat() {
    return word;
  }

  /**
   * Creates a temporary word, used for deciding cell values, and avoiding
   * duplicate values
   */
  public void createTempWord() {
    tempWord = new StringBuilder(word);

  }

  /**
   * Remove a character at a given position in temporary word
   * 
   * @param ch       character
   * @param position in string
   */
  public void removeCharacterAtPosFromTempWord(char ch, int position) {
    if (tempWord.charAt(position) == ch) {
      tempWord.setCharAt(position, '*'); // Use '*' to indicate a used character
    }
  }

  /**
   * Removes the first given character in the temporary word
   * 
   * @param ch character
   */
  public void removeCharacterFromTempWord(char ch) {
    int index = tempWord.indexOf(String.valueOf(ch));
    if (index != -1) {
      tempWord.setCharAt(index, '*'); // Replace the first occurrence of 'ch' with '*'
    }
  }

  /**
   * Checks if a character is in temporary word
   * 
   * @param ch character
   * @return boolean deciding if character is in word
   */
  public boolean isCharacterInTempWord(char ch) {
    return tempWord.toString().contains(String.valueOf(ch));
  }

  /**
   * Checks a character at a position, if that is in the same position in the
   * guessing word
   * 
   * @param ch       character
   * @param position in string
   * @return boolean deciding if the two strings have the same placement of the
   *         character
   */
  public boolean checkCharacterAtPosition(char ch, int position) {
    if (position >= 0 && position < word.length()) {
      return word.charAt(position) == ch;
    }
    ;

    return false;

  }
}
