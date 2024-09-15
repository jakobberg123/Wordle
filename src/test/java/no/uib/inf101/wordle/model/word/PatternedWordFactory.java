package no.uib.inf101.wordle.model.word;

public class PatternedWordFactory implements WordFactory {
    private String[] patterns;
    private int currentIndex;

    public PatternedWordFactory(String[] patterns) {
        this.patterns = patterns;
        this.currentIndex = 0;
    }

    @Override
    public Word createWord() {
        String currentWord = patterns[currentIndex];
        currentIndex = (currentIndex + 1) % patterns.length;
        return new Word(currentWord);
    }
}

