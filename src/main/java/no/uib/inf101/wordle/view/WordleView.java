package no.uib.inf101.wordle.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import javax.swing.JPanel;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.wordle.model.GameState;
import no.uib.inf101.wordle.model.Statistics;
import no.uib.inf101.wordle.model.TileObject;

public class WordleView extends JPanel {
  private final ViewableWordleModel viewableWordleModel;
  private final ColorTheme colorTheme;
  private JButton chooseWordButton;
  private static final Dimension PREFERRED_SIZE = new Dimension(600, 800);
  private static final double MARGIN = 15;
  private static final int FONT_SIZE_LARGE = 90;
  private static final int FONT_SIZE_MEDIUM = 40;
  private static final int FONT_SIZE_SMALL = 25;
  private static final int TRANSPARENCY_OVERLAY = 128;
  private String currentWord;
  

  /**
   * Constructs a WordleView with a given model.
   * 
   * @param viewableWordlesModel The model to be visualized by this view.
   */
  public WordleView(ViewableWordleModel viewableWordleModel) {
    this.viewableWordleModel = viewableWordleModel;
    this.colorTheme = new DefaultColorTheme();
    setupView();
    
  }

  private void setupView() {
    setFocusable(true);
    setPreferredSize(PREFERRED_SIZE);
    setBackground(colorTheme.getBackgroundColor());
    currentWord = viewableWordleModel.getCurrentWord();
    initializeChooseWordButton();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    this.currentWord = viewableWordleModel.getCurrentWord();
    GameState currentGameState = viewableWordleModel.getGameState();
    updateButtonVisibility(viewableWordleModel.getGameState());
    drawGame(g2);
    drawKeyboard(g2);

    switch (currentGameState) {
      case GAME_OVER:
        drawGameFinished(g2, GameState.GAME_OVER);
        drawStatistics(g2);
        break;
      case GAME_WON:
        drawGameFinished(g2, GameState.GAME_WON);
        drawStatistics(g2);
        break;
      case MAIN_MENU:
        drawMainMenu(g2);
        break;
      default:
        break;
    }
  }
  private void drawCenteredText(Graphics2D g2, String text, Font font, int y) {
    g2.setFont(font);
    FontMetrics fm = g2.getFontMetrics();
    int x = (getWidth() - fm.stringWidth(text)) / 2;
    g2.drawString(text, x, y);
}

  // https://www.developer.com/java/java-buttons/ Used as resource
  private void initializeChooseWordButton() {
    chooseWordButton = new JButton("Choose Your Own Word");
    chooseWordButton.setBounds(200, 350, 200, 50);
    add(chooseWordButton);
  }

  /**
   * Gets the JButton for the controller
   * 
   * @return JButton of choose word.
   */
  public JButton getChooseWordButton() {
    return chooseWordButton;
  }

  /**
   * Updates the visibility of button according to the gameState.
   * The button should be visible if it´s not in an active game.
   * 
   * @param gameState
   */
  public void updateButtonVisibility(GameState gameState) {
    chooseWordButton.setVisible(gameState != GameState.ACTIVE_GAME);
  }

  private void drawGame(Graphics2D g2) {
    double x = MARGIN;
    double additionalVerticalOffset = 30;
    double y = MARGIN + additionalVerticalOffset;
    double width = getWidth() - 2 * MARGIN;
    double height = getHeight() - 2 * MARGIN - additionalVerticalOffset - 150;

    Rectangle2D.Double rect = new Rectangle2D.Double(x, y, width, height);
    ColorTheme colorTheme = new DefaultColorTheme();
    g2.setColor(colorTheme.getFrameColor());

    CellPositionToPixelConverter converter = new CellPositionToPixelConverter(rect, viewableWordleModel.getDimension(),
        MARGIN);

    Iterable<GridCell<TileObject>> cellsGrid = viewableWordleModel.getTilesOnBoard();

    drawCells(g2, cellsGrid, converter, colorTheme);

    // Draw the title
    String title = "Wordle";
    Font titleFont = new Font("Arial", Font.BOLD, FONT_SIZE_MEDIUM);
    g2.setFont(titleFont);
    g2.setColor(colorTheme.getLetterColor(Color.WHITE));

    // Calculate the width of the title
    FontMetrics fm = g2.getFontMetrics(titleFont);

    // Calculate the starting x and y position for the text so it's centered
    int titleY = (int) (additionalVerticalOffset / 2) + fm.getAscent();

    drawCenteredText(g2, title, titleFont, titleY);
    if (viewableWordleModel.isLastGuessInvalid()) {
      drawInvalidWordMessage(g2);
    }
  }

  private void drawCells(Graphics2D graphics2d, Iterable<GridCell<TileObject>> cells,
      CellPositionToPixelConverter converter, ColorTheme colorTheme) {
    for (GridCell<TileObject> cell : cells) {
      CellPosition cp = cell.pos();
      Rectangle2D cellBounds = converter.getBoundsForCell(cp);

      // Default color for cells not yet guessed or beyond the current guess
      Color cellColor = Color.GRAY;

      cellColor = colorTheme.getCellColor(cell.value().result());

      graphics2d.setColor(cellColor);
      graphics2d.fill(cellBounds);

      // Draw the letter, if present
      if (cell.value() != null && cell.value().character() != '-') {
        graphics2d.setColor(colorTheme.getLetterColor(Color.WHITE)); // Default text color for letters
        Font charFont = new Font("Arial", Font.BOLD, FONT_SIZE_MEDIUM);
        graphics2d.setFont(charFont);
        FontMetrics fm = graphics2d.getFontMetrics();
        String letter = "" + cell.value().character();
        int x = (int) (cellBounds.getX() + (cellBounds.getWidth() - fm.stringWidth(letter)) / 2);
        int y = (int) (cellBounds.getY() + (cellBounds.getHeight() - fm.getHeight()) / 2 + fm.getAscent());
        graphics2d.drawString(letter, x, y);
      }
    }
  }

  private void drawKeyboard(Graphics2D g2) {
    Iterable<GridCell<TileObject>> keys = viewableWordleModel.getKeyboardTiles();
    CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
        new Rectangle2D.Double(10, getHeight() - 150, getWidth() - 20, 140), // Specify the area for the keyboard
        viewableWordleModel.getKeyboardDimension(), // You need to provide dimensions for the keyboard grid
        5 // Margin between keys
    );

    for (GridCell<TileObject> key : keys) {
      CellPosition cp = key.pos();
      TileObject tile = key.value();
      Rectangle2D keyBounds = converter.getBoundsForCell(cp);
      Color keyColor = colorTheme.getCellColor(tile.result()); // Apply color based on the key's result state

      g2.setColor(keyColor);
      g2.fill(keyBounds);

      // Draw the letter on the key
      g2.setColor(Color.BLACK); // Text color
      Font keyFont = new Font("Arial", Font.BOLD, 18);
      g2.setFont(keyFont);
      FontMetrics fm = g2.getFontMetrics();
      String letter = Character.toString(tile.character());
      int x = (int) (keyBounds.getX() + (keyBounds.getWidth() - fm.stringWidth(letter)) / 2);
      int y = (int) (keyBounds.getY() + (keyBounds.getHeight() - fm.getHeight()) / 2 + fm.getAscent());
      g2.drawString(letter, x, y);
    }
  }

  private void drawInvalidWordMessage(Graphics2D g2) {
    String invalidMessage = "Not in word list";
    Font messageFont = new Font("Arial", Font.BOLD, FONT_SIZE_SMALL);
    g2.setFont(messageFont);
    g2.setColor(Color.RED); // Red to indicate error

    FontMetrics fm = g2.getFontMetrics(messageFont);

    // Position the message just above the board or at a fixed position
    int messageY = (int) MARGIN / 2 + fm.getAscent() - 10; // Draw at the top margin

    drawCenteredText(g2, invalidMessage, messageFont, messageY);
  }

  private void drawMainMenu(Graphics2D g2) {
    int menuAreaWidth = getWidth();
    int menuAreaHeight = getHeight();

    // Load and draw the main menu background image
    BufferedImage image = Inf101Graphics.loadImageFromResources("/WordleMainMenu.png");
    double scaleWidth = (double) menuAreaWidth / image.getWidth();
    double scaleHeight = (double) menuAreaHeight / image.getHeight();
    double scale = Math.max(scaleWidth, scaleHeight);

    int scaledWidth = (int) (image.getWidth() * scale);
    int scaledHeight = (int) (image.getHeight() * scale);
    int menuAreaX = (menuAreaWidth - scaledWidth) / 2;
    int menuAreaY = (menuAreaHeight - scaledHeight) / 2;

    // Fill background before placing image
    g2.setColor(colorTheme.getBackgroundColor());
    g2.fill(new Rectangle2D.Double(0, 0, menuAreaWidth, menuAreaHeight));
    g2.drawImage(image, menuAreaX, menuAreaY, scaledWidth, scaledHeight, null);

    
    // Draw main menu text
    g2.setColor(colorTheme.getLetterColor(Color.BLACK));
    Font pressToPlayFont = new Font("DIALOG", Font.BOLD, FONT_SIZE_MEDIUM);
    drawCenteredText(g2, "PRESS SPACE TO PLAY", pressToPlayFont, menuAreaHeight / 2 + 90);

    Font howToPlayFont = new Font("DIALOG", Font.BOLD, FONT_SIZE_SMALL);
    drawCenteredText(g2, "HOW TO PLAY", howToPlayFont, menuAreaHeight / 2 + 130);

    // Draw legend for indicators
    drawIndicators(g2, menuAreaHeight);
}
private void drawIndicators(Graphics2D g2, int menuAreaHeight) {

  //Load the images
  BufferedImage greenImage = Inf101Graphics.loadImageFromResources("/GreenIndicator.png");
  BufferedImage yellowImage = Inf101Graphics.loadImageFromResources("/YellowIndicator.png");
  BufferedImage grayImage = Inf101Graphics.loadImageFromResources("/GrayIndicator.png");

  double scaleIndicator = 0.4;  // Scaling factor for indicator images
  int scaledIndicatorWidth = (int) (greenImage.getWidth() * scaleIndicator);
  int scaledIndicatorHeight = (int) (greenImage.getHeight() * scaleIndicator);

  int indicatorX = scaledIndicatorWidth;
  int indicatorY = menuAreaHeight - 3 * scaledIndicatorHeight - 60;  // Base position for indicators

  // Draw indicator images
  g2.drawImage(greenImage, indicatorX, indicatorY, scaledIndicatorWidth, scaledIndicatorHeight, null);
  g2.drawImage(yellowImage, indicatorX, indicatorY + scaledIndicatorHeight + 10, scaledIndicatorWidth, scaledIndicatorHeight, null);
  g2.drawImage(grayImage, indicatorX, indicatorY + 2 * (scaledIndicatorHeight + 10), scaledIndicatorWidth, scaledIndicatorHeight, null);

  // Draw text for indicators
  String green = "Correctly placed";
  String yellow = "In word but not correctly placed";
  String gray = "Not in word";
  Font indicatorFont = new Font("DIALOG", Font.BOLD, FONT_SIZE_SMALL);
  g2.setFont(indicatorFont);
  g2.setColor(Color.BLACK);
  g2.drawString(green, indicatorX + scaledIndicatorHeight + 30, indicatorY + 40);
  g2.drawString(yellow, indicatorX + scaledIndicatorHeight + 30, indicatorY + 110);
  g2.drawString(gray, indicatorX + scaledIndicatorHeight + 30, indicatorY + 180);
}

  

  private void drawGameFinished(Graphics2D g2, GameState currentGameState) {
    // Overlay
    Shape overlay = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
    g2.setColor(new Color(0, 0, 0, TRANSPARENCY_OVERLAY));
    g2.fill(overlay);

    // Set color for text
    g2.setColor(colorTheme.getLetterColor(Color.WHITE));

    // Base Y position for the first line of text
    int baseY = 200;

    // Game Over / Game Won text
    Font gameOverFont = new Font("DIALOG", Font.BOLD, FONT_SIZE_LARGE);
    drawCenteredText(g2, (currentGameState == GameState.GAME_WON ? "GAME WON" : "GAME LOST"), gameOverFont, baseY);

    // Correctness message
    Font smallFont = new Font("DIALOG", Font.BOLD, FONT_SIZE_SMALL);
    String wordCorrectness = currentGameState == GameState.GAME_WON ? "You correctly guessed the word!" : "Nice try, the word was:";
    drawCenteredText(g2, wordCorrectness, smallFont, baseY + 50); // Offset by 50 pixels for the next line

    // Current word displayed
    Font mediumFont = new Font("DIALOG", Font.BOLD, FONT_SIZE_MEDIUM);
    drawCenteredText(g2, currentWord, mediumFont, baseY + 100); // Further offset by another 100 pixels
    

    // Play Again message
    drawCenteredText(g2, "Press ENTER to restart", mediumFont, baseY + 500); // Offset by 500 pixels from the base

    // Back to Main Menu message
    drawCenteredText(g2, "Press ESC to main menu", mediumFont, baseY + 550); // Offset by 550 pixels from the base
}

  private void drawStatistics(Graphics2D g2) {
    Statistics stats = viewableWordleModel.getStatistics();
    String[] statsTexts = {
        String.format("Games Played: %d", stats.getGamesPlayed()),
        String.format("Games Won: %d", stats.getGamesWon()),
        String.format("Win Rate: %.2f%%", stats.getWinRate()),
        String.format("Avg. Tries/Game won: %.2f", stats.getAverageTriesPerGameWon())
    };

    Font statsFont = new Font("Arial", Font.BOLD, FONT_SIZE_MEDIUM);
    g2.setFont(statsFont);
    g2.setColor(colorTheme.getLetterColor(Color.WHITE));

    int baseY = getHeight() / 2;  // Start drawing from the middle of the screen, adjusted downwards
    int lineHeight = g2.getFontMetrics(statsFont).getHeight(); // Height of one line of text to calculate vertical spacing

    for (int i = 0; i < statsTexts.length; i++) {
        drawCenteredText(g2, statsTexts[i], statsFont, baseY + i * lineHeight);
    }
}

}
