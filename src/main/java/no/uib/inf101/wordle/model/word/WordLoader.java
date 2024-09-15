package no.uib.inf101.wordle.model.word;

import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WordLoader {
    /**
     * Loads a list of valid words from a specified file within the classpath. This
     * method is intended
     * to read a text file where each line contains one valid word. The path
     * provided should be relative
     * to the root of the classpath, typically located in the 'resources' directory.
     * Referance:
     * https://stackoverflow.com/questions/15749192/how-do-i-load-a-file-from-resource-folder
     * Paul Varga
     *
     * @param resourcePath The path to the resource file relative to the classpath
     *                     root.
     * @return A set of valid words.
     */
    public List<String> loadValidWords(String resourcePath) {
        List<String> words = new ArrayList<>();

        // Using the class loader from the current class
        InputStream inputStream = WordLoader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            System.err.println("Resource not found: " + resourcePath);
            return words; // Early return for not found resource
        }

        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim()); // Processing each line as a word
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return words;
    }
}
