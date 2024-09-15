package no.uib.inf101.wordle.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JOptionPane;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.wordle.model.GameState;
import no.uib.inf101.wordle.model.TileObject;
import no.uib.inf101.wordle.view.CellPositionToPixelConverter;
import no.uib.inf101.wordle.view.WordleView;

public class WordleController implements java.awt.event.KeyListener, MouseListener {
  private ControllableWordleModel model;
  private WordleView view;

  /**
   * Constructs a new WordleController.
   * Initializes the game model and view.
   * The controller also registers itself as a key listener to the view to handle
   * user input.
   *
   * @param controllableWordleModel The game model that this controller will
   *                                manage.
   * @param wordleView              The game view that this controller will update
   *                                based on game state changes.
   */
  public WordleController(ControllableWordleModel controllableWordleModel, WordleView wordleView) {
    this.model = controllableWordleModel;
    this.view = wordleView;
    wordleView.addKeyListener(this);
    wordleView.setFocusable(true);
    this.view.addMouseListener(this);
    initView();
  }

  private void initView() {
    // Chat gpt as resource
    view.getChooseWordButton().addActionListener(e -> handleChooseWord());
  }

  @Override
  public void keyTyped(KeyEvent e) {
    char charPressed = e.getKeyChar();
    charPressed = Character.toUpperCase(charPressed);
    if (Character.isLetter(charPressed)) {
      model.handleLettersInput(charPressed);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        handleArrowLeft();
        break;
      case KeyEvent.VK_RIGHT:
        handleArrowRight();
        break;
      case KeyEvent.VK_BACK_SPACE:
        handleBackspace();
        break;
      case KeyEvent.VK_ENTER:
        handleEnter();
        break;
      case KeyEvent.VK_SPACE:
        handleSpace();
        break;
      case KeyEvent.VK_ESCAPE:
        handleEscape();
        break;
    }

    view.repaint();

  }

  private void handleArrowLeft() {
    model.moveBox(0, -1);
  }

  private void handleArrowRight() {
    model.moveBox(0, 1);
  }

  private void handleBackspace() {
    handleArrowLeft();
    model.removeLetter();
  }

  private void handleEnter() {
    switch (model.getGameState()) {
      case ACTIVE_GAME:
        if (model.checkIfRowFull()) {
          model.checkRowCorrectness();
        }
        break;
      case GAME_OVER:
        model.startGame();
      case GAME_WON:
        model.startGame();
        break;
      default:
        break;
    }
  }

  private void handleSpace() {
    if (model.getGameState() == GameState.MAIN_MENU) {
      model.startGame();
    }
  }

  private void handleEscape() {
    if (model.getGameState() == GameState.GAME_OVER
        || model.getGameState() == GameState.GAME_WON) {
      model.setGameState(GameState.MAIN_MENU);
    }
  }

  // https://www.developer.com/java/java-buttons/ Used as resource
  private void handleChooseWord() {
    String userWord = JOptionPane.showInputDialog(view, "Enter a 5-letter word:");
    if (userWord != null && model.isWordValid(userWord)) {
      model.setUserChosenWord(userWord.toUpperCase());
      JOptionPane.showMessageDialog(view, "Word set to: " + userWord.toUpperCase());
      view.updateButtonVisibility(GameState.ACTIVE_GAME);
    } else {
      JOptionPane.showMessageDialog(view,
          "Invalid word. Please enter a 5-letter word in the english dictionary.", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Point clickPoint = e.getPoint();
    Iterable<GridCell<TileObject>> keys = model.getKeyboardTiles();
    CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
        new Rectangle2D.Double(10, view.getHeight() - 150, view.getWidth() - 20, 140), model.getKeyboardDimension(), 5);
    // Checks with the converter which cell has been clicked.
    for (GridCell<TileObject> key : keys) {
      CellPosition cp = key.pos();
      Rectangle2D keyBounds = converter.getBoundsForCell(cp);
      if (keyBounds.contains(clickPoint)) {
        char keyPressed = key.value().character();
        model.handleLettersInput(keyPressed);
        break;
      }
    }
    view.repaint();
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // Ignore
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // Ignore
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // Ignore
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // Ignore
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // ignore
  }
}
