package no.uib.inf101.wordle.model.word;

import java.util.Random;
import java.io.IOException;
import java.util.List;



public class RandomWordFactory implements WordFactory {

    private String[] validWords;
    private Random random = new Random();

    /**
 * Constructs a new RandomWordFactory which loads a list of valid words from a specified resource file.
 * This constructor initializes the valid words array by reading them from a text file containing
 * one word per line. The words are loaded using a {@link WordLoader} utility, which abstracts the
 * details of resource loading and text reading.
 * @throws IOException if there are issues accessing or reading from the resource file. This exception is
 *                     caught internally, logged, and not rethrown, and an empty array is used as a fallback
 *                     to ensure continued operation.
 */
    public RandomWordFactory() {
        WordLoader wordLoader = new WordLoader();
        try {
            List<String> words = wordLoader.loadValidWords("wordle-answers-alphabetical.txt");
            if (words.isEmpty()) {
                throw new IOException("No words loaded, possible empty file or wrong file path.");
            }
            validWords = words.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
            validWords = new String[] {}; // Fallback to an empty array
        }
    }
    
    

    @Override
    public Word createWord() {

        if (validWords.length == 0) {
            throw new IllegalStateException("No valid words available");
        }
        int index = random.nextInt(validWords.length);
        return new Word(validWords[index].toUpperCase());
    }
}
