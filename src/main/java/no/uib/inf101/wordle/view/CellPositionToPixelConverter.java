package no.uib.inf101.wordle.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;

/**
 * Converts cell positions within a grid to pixel coordinates for rendering.
 * Retrieved from: https://git.app.uib.no/ii/inf101/24v/assignments/Jakob.Berg_sem1-tetris
 */
public class CellPositionToPixelConverter {
  private Rectangle2D box;
  private GridDimension gd;
  private double margin;

  /**
   * Constructs a new CellPositionToPixelConverter.
   * 
   * @param box    The bounding box within which the grid is drawn.
   * @param gd     The dimensions of the grid (number of rows and columns).
   * @param margin The margin between cells in the grid.
   */
  public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double margin) {
    this.box = box;
    this.gd = gd;
    this.margin = margin;
  }

  /**
   * Gets the bounding box for a specific cell in the grid.
   * 
   * @param cp The position of the cell within the grid.
   * @return A Rectangle2D representing the pixel coordinates of the bounding box
   *         for the specified cell.
   */
  public Rectangle2D getBoundsForCell(CellPosition cp) {

    double cellWidth = (box.getWidth() - (gd.cols() + 1) * margin) / gd.cols();
    double cellHeight = (box.getHeight() - (gd.rows() + 1) * margin) / gd.rows();

    double cellX = box.getX() + margin + cp.col() * (cellWidth + margin);
    double cellY = box.getY() + margin + cp.row() * (cellHeight + margin);
    return new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);

  }
}