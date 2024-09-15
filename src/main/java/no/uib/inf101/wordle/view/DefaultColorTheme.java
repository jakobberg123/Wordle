package no.uib.inf101.wordle.view;

import java.awt.Color;

import no.uib.inf101.wordle.model.LetterCheckResult;

public class DefaultColorTheme implements ColorTheme {
    @Override
    public Color getCellColor(LetterCheckResult result) {
        switch (result) {
            case CORRECT_POSITION:
                return new Color(83, 141, 78);
            case WRONG_POSITION:
                return new Color(226, 188, 15);
            case NOT_IN_WORD:
                return new Color(58, 58, 60);
            case INVALID:
                return new Color(255, 71, 77);
            default:
                return Color.GRAY;
        }
    }

    @Override
    public Color getFrameColor() {
        return Color.LIGHT_GRAY;
    }

    @Override
    public Color getBackgroundColor() {
        return Color.LIGHT_GRAY;
    }

    @Override
    public Color getLetterColor(Color color) {
        return color;
        
    }

}
