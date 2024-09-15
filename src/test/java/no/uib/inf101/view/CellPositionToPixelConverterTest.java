package no.uib.inf101.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.awt.geom.Rectangle2D;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.wordle.model.WordleBoard;
import no.uib.inf101.wordle.view.CellPositionToPixelConverter;

// Retrieved from: https://git.app.uib.no/ii/inf101/24v/assignments/Jakob.Berg_sem1-tetris

public class CellPositionToPixelConverterTest {

  @Test
  public void sanityTest() {
    GridDimension gd = new WordleBoard(3, 4);
    CellPositionToPixelConverter converter = new CellPositionToPixelConverter(new Rectangle2D.Double(29, 29, 340, 240),
        gd, 30);
    Rectangle2D expected = new Rectangle2D.Double(214, 129, 47.5, 40);
    assertEquals(expected, converter.getBoundsForCell(new CellPosition(1, 2)));
  }



}