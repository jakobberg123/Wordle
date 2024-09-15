package no.uib.inf101.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import no.uib.inf101.wordle.model.LetterCheckResult;
import no.uib.inf101.wordle.view.ColorTheme;
import no.uib.inf101.wordle.view.DefaultColorTheme;

// Retrieved from: https://git.app.uib.no/ii/inf101/24v/assignments/Jakob.Berg_sem1-tetris

public class DefaultColorThemeTest {
  @Test
  public void sanityDefaultColorThemeTest() {
    ColorTheme colors = new DefaultColorTheme();
    assertEquals(Color.LIGHT_GRAY, colors.getBackgroundColor());
    assertEquals(Color.LIGHT_GRAY, colors.getFrameColor());
    assertEquals(new Color(83, 141, 78), colors.getCellColor(LetterCheckResult.CORRECT_POSITION));
    assertEquals(new Color(226, 188, 15), colors.getCellColor(LetterCheckResult.WRONG_POSITION));
    assertEquals(new Color(58, 58, 60), colors.getCellColor(LetterCheckResult.NOT_IN_WORD));
    assertEquals(new Color(255, 71, 77), colors.getCellColor(LetterCheckResult.INVALID));
    assertEquals(Color.GRAY, colors.getCellColor(LetterCheckResult.EMPTY));
    assertEquals(Color.WHITE, colors.getLetterColor(Color.WHITE));
    assertEquals(Color.BLACK, colors.getLetterColor(Color.BLACK));

  }

}

