package no.uib.inf101.wordle.model;

import java.util.Map;
import java.util.HashMap;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class Keyboard extends Grid<TileObject> {
    private Map<Character, LetterCheckResult> keyStatus;
    private Map<Character, CellPosition> keyPositions;

    /**
     * Constructs a new Keyboard with given rows and columns.
     * Sets the keyboard character and result as empty.
     * Generates a hashmap for storing each keys status.
     * Generates a hashmap for storing each keys position
     * Initializes the keyboard
     * 
     * @param rows rows of the keyboard
     * @param cols columns of the keyboard
     */
    public Keyboard(int rows, int cols) {
        super(rows, cols, new TileObject('-', LetterCheckResult.EMPTY)); // Initialize with EMPTY keys
        this.keyStatus = new HashMap<>();
        this.keyPositions = new HashMap<>();
        initializeKeys();
    }

    // Sets the keyboards default values.
    private void initializeKeys() {
        String[] keyRows = {
                "QWERTYUIOP",
                "ASDFGHJKL",
                "ZXCVBNM"
        };

        int row = 0;
        for (String keys : keyRows) {
            for (int col = 0; col < keys.length(); col++) {
                char ch = keys.charAt(col);
                CellPosition position = new CellPosition(row, col);

                // Store the default status of the key as EMPTY in a map tracking each key's status.
                keyStatus.put(ch, LetterCheckResult.EMPTY);

                // Store the position of each key in a map for quick access during updates.
                keyPositions.put(ch, position);

                // Set the tile at the specified position in the grid with the character and its default status.
                this.set(new CellPosition(row, col), new TileObject(ch, LetterCheckResult.EMPTY));
            }
            row++;
        }

    }

    /**
     * Updates the display and status of a specific key on the keyboard based on the
     * result of a letter check. This method updates the visual representation on the
     * grid and modifies the internal map that tracks the status of each key.
     *
     * Each key's status is updated to reflect its relevance in the context of a guessed word:
     * The method will only update the key if it exists within the keyboard's defined keys.
     *
     * @param ch     the character of the key to update. This character must be one of
     *               the keys defined during the initialization of the keyboard.
     * @param status the new status for the specified key, reflecting its role in
     *               the guessed word. This status is one of the enums from {@link LetterCheckResult}.
     */
    protected void updateKeyStatus(char ch, LetterCheckResult status) {
        if (keyPositions.containsKey(ch)) {
            CellPosition position = keyPositions.get(ch);
            keyStatus.put(ch, status);
            super.set(position, new TileObject(ch, status));
        }
    }
}

