package io.github.skenvy;

import java.util.ArrayList;
import java.util.List;

////////////////////////////////////////////////////////////////////////////////
// New classes; WIP rebuild of Sudoku, SudokuGui, and PermutationComputation. //
////////////////////////////////////////////////////////////////////////////////

/**
 * A collection of cells that contains an amount of cells equal to the "size" of
 * the grid, in terms of its latin square size. In the standard sudoku that is a
 * latin square of size 9, a collection can be either a row, a column, or a box,
 * also known as a region or block.
 */
public class CellCollection {

  public final int size;

  private final List<Cell> cells;

  /**
   * Initialise an empty list with space to reference the default 9 cells.
   */
  public CellCollection() {
    this.size = 9;
    this.cells = new ArrayList<Cell>(9);
  }

  /**
   * Initialise an empty list with space to reference the N^2 cells,
   * where N^2 equals the latin square size of the grid.
   */
  public CellCollection(int boardSize) {
    this.size = boardSize * boardSize;
    this.cells = new ArrayList<Cell>(this.size);
  }

  /**
   * Get a collection of the cells.
   */
  public List<Cell> getCells() {
    // Return new list instance to prevent removal from underlying collection.
    return new ArrayList<Cell>(this.cells);
  }

  public void addCellToCollection(Cell cell) {
    this.cells.add(cell);
  }

  public int[] getValues() {
    int[] values = new int[this.cells.size()];
    for (int k = 0; k < this.cells.size(); k++) {
      values[k] = this.cells.get(k).getValue();
    }
    return values;
  }

  /**
   * Used for cell collection related exceptions.
   */
  public class SudokuCellCollectionException extends Exception {

    public SudokuCellCollectionException(String string) {
      super(string);
    }

  }

}
