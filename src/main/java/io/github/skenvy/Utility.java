package io.github.skenvy;

////////////////////////////////////////////////////////////////////////////////
// New classes; WIP rebuild of Sudoku, SudokuGui, and PermutationComputation. //
////////////////////////////////////////////////////////////////////////////////

/**
 * A stubbed utility class that is WIP.
 *
 */
public class Utility {
  protected Utility() {
    throw new UnsupportedOperationException();
  }

  public static boolean isIntegerSquared(final int square) {
    double root = Math.sqrt(square);
    return (Math.floor(root) == Math.ceil(root));
  }

  public static int integerSquareRoot(final int square) {
    return (int) (Math.sqrt(square));
  }
}
