package io.github.skenvy;

////////////////////////////////////////////////////////////////////////////////
// New classes; WIP rebuild of Sudoku, SudokuGui, and PermutationComputation. //
////////////////////////////////////////////////////////////////////////////////

/**
 * An individual cell in the whole grid.
 */
public class Cell {

  /**
   * The initial value that a cell starts with. 0 for empty cells.
   */
  private final int initialValue;

  /**
   * The current value of a cell, whether assigned at the start, or
   * determined through solving. 0 for empty cells.
   */
  private int value;

  /**
   * An array to track the possibilities for an individual cell. 
   */
  private final boolean[] possibleValues;

  /**
   * The row that this cell is a member of.
   */
  private CellCollection row;

  /**
   * The column that this cell is a member of.
   */
  private CellCollection column;

  /**
   * The box that this cell is a member of.
   */
  private CellCollection box;

  /**
   * Initialise an empty cell for a standard sudoku board of size 3*3*3*3.
   */
  public Cell() {
    this.initialValue = 0;
    this.value = 0;
    this.possibleValues = initialisePossibleValues(3);
  }

  /**
   * Initialise a non-empty cell for a standard sudoku board of size 3*3*3*3.
   *
   * @param initialValue is used to provide an initial value.
   * @throws SudokuCellInvalidInitialValueException when attempting to create a
   *     new Cell with an invalid value
   */
  public Cell(int initialValue) throws SudokuCellInvalidInitialValueException {
    this.initialValue = initialValue;
    this.value = initialValue;
    this.possibleValues = initialisePossibleValues(3);
    if (initialValue < 0 || initialValue > 9) {
      throw new SudokuCellInvalidInitialValueException(initialValue, 3);
    }
  }

  /**
   * Initialise a non-empty cell for a sudoku board of arbitrary size.
   *
   * @param initialValue is used to provide an initial value.
   * @param boardSize is used to specify a row / column length other than 9.
   * @throws SudokuCellInvalidInitialValueException when attempting to create a
   *     new Cell with an invalid value
   */
  public Cell(int initialValue, int boardSize) throws SudokuCellInvalidInitialValueException {
    this.initialValue = initialValue;
    this.value = initialValue;
    this.possibleValues = initialisePossibleValues(boardSize);
    if (initialValue < 0 || initialValue > (boardSize * boardSize)) {
      throw new SudokuCellInvalidInitialValueException(initialValue, boardSize);
    }
  }

  /**
   * Initialise a non-empty cell for a sudoku board of arbitrary size.
   *
   * @param initialValue is used to provide an initial value.
   * @param boardSize is used to specify a row / column length other than 9.
   * @throws SudokuCellInvalidInitialValueException when attempting to create a
   *     new Cell with an invalid value
   */
  public Cell(int initialValue, int boardSize, CellCollection row, CellCollection column, CellCollection box) throws SudokuCellInvalidInitialValueException {
    this.initialValue = initialValue;
    this.value = initialValue;
    this.possibleValues = initialisePossibleValues(boardSize);
    if (initialValue < 0 || initialValue > (boardSize * boardSize)) {
      throw new SudokuCellInvalidInitialValueException(initialValue, boardSize);
    }
    this.row = row;
    this.column = column;
    this.box = box;
  }

  public int getValue() {
    return this.value;
  }

  public void setValue(int value) throws SudokuCellCantSetValueOfPredeterminedCellException {
    if (this.wasCellPredetermined()) {
      throw new SudokuCellCantSetValueOfPredeterminedCellException();
    }
    this.value = value;
  }

  /**
   * True if the cell was not originally blank.
   */
  boolean wasCellPredetermined() {
    return (this.initialValue != 0);
  }

  /**
   * True if the cell is not currently blank.
   */
  boolean isCellFilled() {
    return (this.value != 0);
  }

  boolean[] initialisePossibleValues(int boardSize) {
    boolean[] possibilities = new boolean[(boardSize * boardSize)];
    for (int i = 0; i < (boardSize * boardSize); i++) {
      possibilities[i] = true;
    }
    return possibilities;
  }

  /**
   * Used for cell related exceptions.
   */
  public class SudokuCellException extends Exception {
    public SudokuCellException(String string) {
      super(string);
    }
  }

  public final class SudokuCellInvalidInitialValueException extends SudokuCellException {
    public SudokuCellInvalidInitialValueException(int initialValue, int boardSize) {
      super("Invalid initial value. Board size of " + boardSize + " allows for values from 1 to " + ((boardSize * boardSize)) + "; was given initial value of " + initialValue);
    }
  }

  public class SudokuCellCantSetValueOfPredeterminedCellException extends SudokuCellException {
    public SudokuCellCantSetValueOfPredeterminedCellException() {
      super("You cannot change the value of a predetermined cell.");
    }
  }
}
