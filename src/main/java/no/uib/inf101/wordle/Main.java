package no.uib.inf101.wordle;

import javax.swing.JFrame;

import no.uib.inf101.wordle.controller.WordleController;
import no.uib.inf101.wordle.model.WordleBoard;
import no.uib.inf101.wordle.model.WordleModel;
import no.uib.inf101.wordle.model.word.RandomWordFactory;
import no.uib.inf101.wordle.model.word.WordFactory;
import no.uib.inf101.wordle.view.WordleView;

public class Main {

  public static void main(String[] args) {
    final String WINDOW_TITLE = "INF101 Tetris";

    WordleBoard wordleBoard = new WordleBoard(6, 5);
    WordFactory wordFactory = new RandomWordFactory();
    WordleModel wordleModel = new WordleModel(wordleBoard, wordFactory);
    WordleView view = new WordleView(wordleModel);
    new WordleController(wordleModel, view);

    // The JFrame is the "root" application window.
    // We here set som properties of the main window,
    // and tell it to display our tetrisView
    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Here we set which component to view in our window
    frame.setContentPane(view);

    // Call these methods to actually display the window
    frame.pack();
    frame.setVisible(true);

  }
}
