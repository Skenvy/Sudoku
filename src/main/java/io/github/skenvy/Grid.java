package io.github.skenvy;

import java.util.ArrayList;
import java.util.List;

////////////////////////////////////////////////////////////////////////////////
// New classes; WIP rebuild of Sudoku, SudokuGui, and PermutationComputation. //
////////////////////////////////////////////////////////////////////////////////

/**
 * A grid is the entire sudoku board, or latin square. The default size of 9 has
 * 9 rows, 9 columns, and 9 boxes.
 */
public class Grid {
  
  public final int size;
  private final List<CellCollection> rows;
  private final List<CellCollection> columns;
  private final List<CellCollection> boxes;

  /**
   * Initialise an empty grid for the default size of 9.
   */
  public Grid() {
    this.size = 9;
    this.rows = new ArrayList<CellCollection>(9);
    this.columns = new ArrayList<CellCollection>(9);
    this.boxes = new ArrayList<CellCollection>(9);
  }
}
