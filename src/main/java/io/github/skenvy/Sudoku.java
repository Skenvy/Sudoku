package io.github.skenvy;

import java.util.ArrayList;
import java.util.Arrays;

public class Sudoku {

  /**
   * The individual cell width/length of a single inner grid. For a normal
   * sudoku board, this is 3. A grid/row/column has "boardSize^2" many cells,
   * and the overall board has "(boardSize^2)^2" ~ "boardSize^4" many cells.
   */
  private final int boardSize;

  /**
   * The known and determined values that populate the grid. Is initialised
   * with the already known cell values, and is then populated with those
   * determined through solving.
   */
  public int[][][][] board;

  /**
   * Whether a cell is filled or not.
   */
  public boolean[][][][] cellFilled;

  //Stores what numbers can go in what squares.
  boolean[][][][][] placeCanContain;

  //whether a small grid contains a number
  boolean[/*#*/][][] numberPresentG;

  //whether a row/column contains a number
  boolean[/*R|C*/][/*#*/][/*RC-#*/] numberPresentRowCol;

  //Records if a square that is entered is in error.
  public boolean[][][][] errorInSquare;

  //
  ArrayList<String> digest = new ArrayList<String>();

  //
  ArrayList<String> operations = new ArrayList<String>();

  //Default is to make the board with the standard 3*3*3*3
  public Sudoku() {
    this.boardSize = 3;
    initialiseBoardVariables();
  }

  //Specify some size k to make a k*k*k*k size board!
  public Sudoku(int size) {
    this.boardSize = size;
    initialiseBoardVariables();
  }

  //You set up a new instance using an already setup board
  public Sudoku(int[][][][] boardIn) throws InvlalidSudokuCongiguration {
    this.boardSize = boardIn.length;
    if (boardIn.length == boardIn[0].length & boardIn.length == boardIn[0][0].length & boardIn.length == boardIn[0][0][0].length) {
      initialiseBoardVariables();
      this.board = boardIn;
    } else {
      throw new InvlalidSudokuCongiguration("Invalid board lengths in incoming array, not a proper matrix of grids, each a matrix of cells");
    }
  }

  /**
   * Used for sudoku related exceptions.
   */
  public final class InvlalidSudokuCongiguration extends Exception {

    public InvlalidSudokuCongiguration(String string) {
      super(string);
    }

  }

  //Used after assigning the boardSize to appropriately size all member variables.
  private void initialiseBoardVariables() {
    board = new int[boardSize][boardSize][boardSize][boardSize];
    cellFilled = new boolean[boardSize][boardSize][boardSize][boardSize];
    placeCanContain = new boolean[boardSize][boardSize][boardSize][boardSize][(boardSize * boardSize)];
    numberPresentG = new boolean[(boardSize * boardSize)][boardSize][boardSize];
    numberPresentRowCol = new boolean[2][(boardSize * boardSize)][(boardSize * boardSize)];
    errorInSquare = new boolean[boardSize][boardSize][boardSize][boardSize];
  }

  public static void main(String[] args) {
    final Sudoku puzzle = new Sudoku(2);
    String[] boardContents = new String[4];
    //Zero Refill "S_0_0_0_0_0_0_0_0_0_E"
    ////XY                 000102101112202122
    //boardContents[0] = "S_0_0_0_0_5_0_6_0_0_E"; /*0,X,0,Y*/
    //boardContents[1] = "S_9_0_0_0_0_0_4_5_0_E"; /*0,X,1,Y*/
    //boardContents[2] = "S_0_2_7_1_0_0_0_0_0_E"; /*0,X,2,Y*/
    //boardContents[3] = "S_2_9_0_0_0_0_0_0_0_E"; /*1,X,0,Y*/
    //boardContents[4] = "S_1_0_3_0_8_0_2_0_5_E"; /*1,X,1,Y*/
    //boardContents[5] = "S_0_0_0_0_0_0_0_4_6_E"; /*1,X,2,Y*/
    //boardContents[6] = "S_0_0_0_0_0_6_7_3_0_E"; /*2,X,0,Y*/
    //boardContents[7] = "S_0_5_9_0_0_0_0_0_4_E"; /*2,X,1,Y*/
    //boardContents[8] = "S_0_0_1_0_2_0_0_0_0_E"; /*2,X,2,Y*/
    //XY                 000102101112202122
    //boardContents[0] = "S_0_8_0_0_0_0_0_5_2_E"; /*0,X,0,Y*/
    //boardContents[1] = "S_0_0_0_4_0_0_3_0_0_E"; /*0,X,1,Y*/
    //boardContents[2] = "S_0_0_3_2_5_0_0_0_0_E"; /*0,X,2,Y*/
    //boardContents[3] = "S_8_0_0_6_0_0_0_0_3_E"; /*1,X,0,Y*/
    //boardContents[4] = "S_0_0_7_8_0_9_4_0_0_E"; /*1,X,1,Y*/
    //boardContents[5] = "S_9_0_0_0_0_1_0_0_5_E"; /*1,X,2,Y*/
    //boardContents[6] = "S_0_0_0_0_3_2_9_0_0_E"; /*2,X,0,Y*/
    //boardContents[7] = "S_0_0_1_0_0_8_0_0_0_E"; /*2,X,1,Y*/
    //boardContents[8] = "S_6_2_0_0_0_0_0_3_0_E"; /*2,X,2,Y*/
    //XY                 000102101112202122
    //boardContents[0] = "S_0_0_0_0_3_0_0_5_9_E"; /*0,X,0,Y*/
    //boardContents[1] = "S_0_0_0_0_0_5_6_8_0_E"; /*0,X,1,Y*/
    //boardContents[2] = "S_0_0_0_0_0_1_0_2_0_E"; /*0,X,2,Y*/
    //boardContents[3] = "S_0_5_0_0_7_8_0_9_0_E"; /*1,X,0,Y*/
    //boardContents[4] = "S_3_0_0_0_0_0_0_0_6_E"; /*1,X,1,Y*/
    //boardContents[5] = "S_0_2_0_5_6_0_0_4_0_E"; /*1,X,2,Y*/
    //boardContents[6] = "S_0_6_0_2_0_0_0_0_0_E"; /*2,X,0,Y*/
    //boardContents[7] = "S_0_9_8_6_0_0_0_0_0_E"; /*2,X,1,Y*/
    //boardContents[8] = "S_4_7_0_0_8_0_0_0_0_E"; /*2,X,2,Y*/

    boardContents[0] = "S_1_2_3_4_E"; /*0,X,0,Y*/
    boardContents[1] = "S_4_3_2_1_E"; /*0,X,1,Y*/
    boardContents[2] = "S_2_0_0_0_E"; /*0,X,2,Y*/
    boardContents[3] = "S_0_1_0_0_E"; /*1,X,0,Y*/
    puzzle.readBoardIn(boardContents);
    puzzle.initialiseBoard();
    puzzle.solveBoard();
    puzzle.printBoardOut();
    System.out.println("done");
    puzzle.printDigest();
    puzzle.printOperations();
  }

  //Read in the stringy form of the board; i.e.
  //    boardContents[0] = "S_1_2_3_4_E";
  //    boardContents[1] = "S_4_3_2_1_E";
  //    boardContents[2] = "S_2_0_0_0_E";
  //    boardContents[3] = "S_0_1_0_0_E";
  public void readBoardIn(String[] boardRows) {
    int boardRow = 0;
    int boardColumn = 0;
    int gridRow = 0;
    int gridColumn = 0;
    int previousIndex = 0;
    //Iterate the rows
    for (int j0 = 0; j0 < (boardSize * boardSize); j0++) {
      // i.e. "0 then 1 then 2 then 0 then 1 then 2 then 0 then 1 then 2"
      gridRow = j0 % boardSize;
      // i.e. "0 then 0 then 0 then 1 then 1 then 1 then 2 then 2 then 2" < thanks to java always rounding down.
      boardRow = (j0 - gridRow) / boardSize;
      //Keep track of index where we found the last delimiter
      previousIndex = 0;
      //Iterate the columns
      for (int j1 = 0; j1 < (boardSize * boardSize); j1++) {
        // i.e. "0 then 1 then 2 then 0 then 1 then 2 then 0 then 1 then 2"
        gridColumn = j1 % boardSize;
        // i.e. "0 then 0 then 0 then 1 then 1 then 1 then 2 then 2 then 2" < thanks to java always rounding down.
        boardColumn = (j1 - gridColumn) / boardSize;
        //Find the next value between two instances of the delimiter
        int stringIntStartIndex = boardRows[j0].indexOf('_', previousIndex) + 1;
        int stringIntEndIndex   = boardRows[j0].indexOf('_', stringIntStartIndex);
        String stringInt = boardRows[j0].substring(stringIntStartIndex, stringIntEndIndex);
        int parsedInt = Integer.parseInt(stringInt);
        //Assign the value to the location on the board and record a message
        board[boardRow][boardColumn][gridRow][gridColumn] = parsedInt;
        previousIndex = stringIntEndIndex;
        if (parsedInt != 0) {
          operations.add("Read Board In: Place " + boardRow + ", " + boardColumn + ", " + gridRow + ", " + gridColumn + " contains " + stringInt);
        }
      }
    }
  }

  //The corollary to "readBoardIn"
  public void printBoardOut() {
    int boardRow = 0;
    int boardColumn = 0;
    int gridRow = 0;
    int gridColumn = 0;
    String mess;
    for (int j0 = 0; j0 < (boardSize * boardSize); j0++) {
      mess = "Row " + (j0) + ": S_";
      gridRow = j0 % boardSize;
      boardRow = (j0 - gridRow) / boardSize;
      for (int j1 = 0; j1 < (boardSize * boardSize); j1++) {
        gridColumn = j1 % boardSize;
        boardColumn = (j1 - gridColumn) / boardSize;
        mess = mess.concat("" + board[boardRow][boardColumn][gridRow][gridColumn] + "_");
      }
      mess = mess.concat("E");
      textFieldAdd(mess);
    }
  }

  //Checks board is valid and generates list of possibilities
  public boolean initialiseBoard() { 
    digest.add("Digest Start");
    operations.add("Operations Start");
    if (!boardCheckValid()) {
      textFieldAdd("Board Check: Failure");
      return false;
    }
    textFieldAdd("Board Check: Pass");
    initialisePlaceCanContain(false);
    return true;
  }

  public void initialisePlaceFilled() {
    for (int k0 = 0; k0 < boardSize; k0++) { //grid row iterate
      for (int k1 = 0; k1 < boardSize; k1++) { //grid column iterate
        for (int k2 = 0; k2 < boardSize; k2++) { //small grid row iterate
          for (int k3 = 0; k3 < boardSize; k3++) { //small grid column iterate
            if ((board[k0][k1][k2][k3] >= 1) && (board[k0][k1][k2][k3] <= (boardSize * boardSize))) {
              cellFilled[k0][k1][k2][k3] = true;
              operations.add("Place " + k0 + ", " + k1 + ", " + k2 + ", " + k3 + " filled with " + board[k0][k1][k2][k3]);
            } else {
              operations.add("Place " + k0 + ", " + k1 + ", " + k2 + ", " + k3 + " is empty");
              cellFilled[k0][k1][k2][k3] = false;
            }
          }
        }
      }
    }
  }

  public void initialisePlaceCanContain(boolean clear) {
    if (clear) {
      for (int k0 = 0; k0 < boardSize; k0++) {
        for (int k1 = 0; k1 < boardSize; k1++) {
          for (int k2 = 0; k2 < boardSize; k2++) {
            for (int k3 = 0; k3 < boardSize; k3++) {
              for (int k4 = 0; k4 < (boardSize * boardSize); k4++) {
                placeCanContain[k0][k1][k2][k3][k4] = true;
              }
            }
          }
        }
      }
    } else {
      initialisePlaceCanContain(true);
      initialiseNumberPresentGridRowCol(false);
      updatePlaceCanContain();
    }
  }

  public void updatePlaceCanContain() {
    for (int k0 = 0; k0 < boardSize; k0++) {
      for (int k1 = 0; k1 < boardSize; k1++) {
        for (int k2 = 0; k2 < boardSize; k2++) {
          for (int k3 = 0; k3 < boardSize; k3++) {
            if (!cellFilled[k0][k1][k2][k3]) {
              for (int k4 = 0; k4 < (boardSize * boardSize); k4++) {
                if (numberPresentRowCol[0][k4][k2 + k0 * board.length] || numberPresentRowCol[1][k4][k3 + k1 * board.length] || numberPresentG[k4][k0][k1]) {
                  placeCanContain[k0][k1][k2][k3][k4] = false;
                }
              }
            }
          }
        }
      }
    }
  }

  public void initialiseNumberPresentGridRowCol(boolean clear) {
    if (clear) {
      for (int k0 = 0; k0 < (boardSize * boardSize); k0++) {
        for (int k1 = 0; k1 < boardSize; k1++) {
          for (int k2 = 0; k2 < boardSize; k2++) {
            numberPresentG[k0][k1][k2] = false;
          }
        }
      }
      for (int k0 = 0; k0 < 2; k0++) {
        for (int k1 = 0; k1 < (boardSize * boardSize); k1++) {
          for (int k2 = 0; k2 < (boardSize * boardSize); k2++) {
            numberPresentRowCol[k0][k1][k2] = false;
          }
        }
      }
    } else {
      updateNumberPresentGridRowCol();
    }
  }

  public void updateNumberPresentGridRowCol() {
    for (int k0 = 0; k0 < boardSize; k0++) {
      for (int k1 = 0; k1 < boardSize; k1++) {
        for (int k2 = 0; k2 < boardSize; k2++) {
          for (int k3 = 0; k3 < boardSize; k3++) {
            if (cellFilled[k0][k1][k2][k3]) {
              numberPresentRowCol[0][board[k0][k1][k2][k3] - 1][k2 + k0 * board.length] = true;
              numberPresentRowCol[1][board[k0][k1][k2][k3] - 1][k3 + k1 * board.length] = true;
              numberPresentG[board[k0][k1][k2][k3] - 1][k0][k1] = true;
            }
          }
        }
      }
    }
  }

  public boolean boardCheckValid() {
    initialisePlaceFilled();
    initialiseNumberPresentGridRowCol(true);
    for (int k0 = 0; k0 < boardSize; k0++) { //grid row iterate
      for (int k1 = 0; k1 < boardSize; k1++) { //grid column iterate
        for (int k2 = 0; k2 < boardSize; k2++) { //small grid row iterate
          for (int k3 = 0; k3 < boardSize; k3++) { //small grid column iterate
            if (cellFilled[k0][k1][k2][k3]) {
              if (numberPresentRowCol[0][board[k0][k1][k2][k3] - 1][k2 + k0 * boardSize]) {
                textFieldAdd("Error in row " + (k2 + k0 * boardSize));
                return false;
              } else if (numberPresentRowCol[1][board[k0][k1][k2][k3] - 1][k3 + k1 * boardSize]) {
                textFieldAdd("Error in column " + (k3 + k1 * boardSize));
                return false;
              } else if (numberPresentG[board[k0][k1][k2][k3] - 1][k0][k1]) {
                textFieldAdd("Error in grid (R,C): (" + k0 + ", " + k1 + ")");
                return false;
              } else {
                numberPresentRowCol[0][board[k0][k1][k2][k3] - 1][k2 + k0 * boardSize] = true;
                numberPresentRowCol[1][board[k0][k1][k2][k3] - 1][k3 + k1 * boardSize] = true;
                numberPresentG[board[k0][k1][k2][k3] - 1][k0][k1] = true;
              }
            }
          }
        }
      }
    }
    return true;
  }

  public void boardCheckErrors() {
    int[/*GridRowCol*/][/*#-GridRowCol*/][/*#*/] appearsTimes = new int[3][(boardSize * boardSize)][(boardSize * boardSize)];
    for (int j0 = 0; j0 < 3; j0++) {
      for (int j1 = 0; j1 < (boardSize * boardSize); j1++) {
        for (int j2 = 0; j2 < (boardSize * boardSize); j2++) {
          appearsTimes[j0][j1][j2] = 0;
        }
      }
    }
    for (int j0 = 0; j0 < boardSize; j0++) {
      for (int j1 = 0; j1 < boardSize; j1++) {
        for (int k0 = 0; k0 < boardSize; k0++) {
          for (int k1 = 0; k1 < boardSize; k1++) {
            if (cellFilled[j0][j1][k0][k1]) {
              if (0 < board[j0][j1][k0][k1] && board[j0][j1][k0][k1] <= (boardSize * boardSize)) {
                appearsTimes[0][j0 * boardSize + j1][board[j0][j1][k0][k1] - 1]++;
                appearsTimes[1][j0 * boardSize + k0][board[j0][j1][k0][k1] - 1]++;
                appearsTimes[2][j1 * boardSize + k1][board[j0][j1][k0][k1] - 1]++;
              }
            }
          }
        }
      }
    }
    for (int j0 = 0; j0 < boardSize; j0++) {
      for (int j1 = 0; j1 < boardSize; j1++) {
        for (int k0 = 0; k0 < boardSize; k0++) {
          for (int k1 = 0; k1 < boardSize; k1++) {
            if (0 < board[j0][j1][k0][k1] && board[j0][j1][k0][k1] <= (boardSize * boardSize)) {
              if (appearsTimes[0][j0 * 3 + j1][board[j0][j1][k0][k1] - 1] > 1 || appearsTimes[1][j0 * 3 + k0][board[j0][j1][k0][k1] - 1] > 1 || appearsTimes[2][j1 * 3 + k1][board[j0][j1][k0][k1] - 1] > 1) {
                errorInSquare[j0][j1][k0][k1] = true;
              } else {
                errorInSquare[j0][j1][k0][k1] = false;
              }
            }
          }
        }
      }
    }
  }

  public boolean errorExists() {
    boardCheckErrors();
    boolean check = false;
    for (int k0 = 0; k0 < boardSize; k0++) { //grid row iterate
      for (int k1 = 0; k1 < boardSize; k1++) { //grid column iterate
        for (int k2 = 0; k2 < boardSize; k2++) { //small grid row iterate
          for (int k3 = 0; k3 < boardSize; k3++) { //small grid column iterate
            if (errorInSquare[k0][k1][k2][k3]) {
              check = true;
            }
          }
        }
      }
    }
    return check;
  }

  public boolean boardCheckComplete() {
    for (int k0 = 0; k0 < boardSize; k0++) { //grid row iterate
      for (int k1 = 0; k1 < boardSize; k1++) { //grid column iterate
        for (int k2 = 0; k2 < boardSize; k2++) { //small grid row iterate
          for (int k3 = 0; k3 < boardSize; k3++) { //small grid column iterate
            if (!cellFilled[k0][k1][k2][k3]) {
              return false;
            } 
          }
        }
      }
    }
    return true;
  }

  public int boardCheckPlacesFilled() {
    int count = 0;
    for (int k0 = 0; k0 < boardSize; k0++) { //grid row iterate
      for (int k1 = 0; k1 < boardSize; k1++) { //grid column iterate
        for (int k2 = 0; k2 < boardSize; k2++) { //small grid row iterate
          for (int k3 = 0; k3 < boardSize; k3++) { //small grid column iterate
            if (cellFilled[k0][k1][k2][k3]) {
              count++;
            } 
          }
        }
      }
    }
    return count;
  }

  public void printDigest() {
    for (int k = 0; k < digest.size(); k++) {
      System.out.println(digest.get(k));
    }
  }

  public String[] digestAsArray() {
    String[] out = new String[digest.size()];
    for (int k = 0; k < digest.size(); k++) {
      out[k] = digest.get(k);
    }
    return out;
  }

  public void printOperations() {
    for (int k = 0; k < operations.size(); k++) {
      System.out.println(operations.get(k));
    }
  }

  public String[] operationsAsArray() {
    String[] out = new String[operations.size()];
    for (int k = 0; k < operations.size(); k++) {
      out[k] = operations.get(k);
    }
    return out;
  }

  public void textFieldAdd(String a) {
    digest.add(a);
    operations.add(a);
  }

  public void solveBoard() {
    if (!solveBoardEasy()) {
      if (!solveBoardModerate()) {
        printBoardOut();
        if (!solveBoardHard()) {
          textFieldAdd("Couldn't solve this board with existing techniques.");
        }
      }
    }
  }

  public boolean solveBoardEasy() {
    if (boardCheckComplete()) {
      textFieldAdd("Board already solved");
      return true;
    }
    int unsolvedSquares = (boardSize * boardSize) * (boardSize * boardSize) - boardCheckPlacesFilled();
    int countSoleCandidates = 0;
    int countUniqueCandidates = 0;
    for (int q = 0; q < unsolvedSquares; q++) {
      countSoleCandidates += iterateSoleCandidate();
      if (countSoleCandidates == unsolvedSquares) {
        break;
      } else {
        unsolvedSquares -= countSoleCandidates;
      }
      countUniqueCandidates += iterateUniqueCandidate();
      if (countUniqueCandidates == unsolvedSquares) {
        break;
      } else {
        unsolvedSquares -= countUniqueCandidates;
      }
      if ((countSoleCandidates == 0) && (countUniqueCandidates == 0)) {
        break;
      }
    }
    if (boardCheckComplete()) {
      textFieldAdd("Board was easy!");
      textFieldAdd("Board solved in " + countSoleCandidates + " solo candidate steps");
      textFieldAdd("Board solved in " + countUniqueCandidates + " unique candidate steps");
      return true;
    }
    textFieldAdd("Board was not easy!");
    textFieldAdd("Board Unsolved; Performed " + countSoleCandidates + " solo candidate steps");
    textFieldAdd("Board Unsolved; Performed " + countUniqueCandidates + " unique candidate steps");
    return false;
  }

  public boolean solveBoardModerate() {
    if (boardCheckComplete()) {
      return true;
    }
    int unsolvedSquares = (boardSize * boardSize) * (boardSize * boardSize) - boardCheckPlacesFilled();
    int countSoleCandidates = 0;
    int countUniqueCandidates = 0;
    int countReduceRowColByGrid = 0;
    int countReduceGridByRowCol = 0;
    for (int q = 0; q < unsolvedSquares; q++) {
      countSoleCandidates += iterateSoleCandidate();
      if (countSoleCandidates == unsolvedSquares) {
        break;
      } else {
        unsolvedSquares -= countSoleCandidates;
      }
      countUniqueCandidates += iterateUniqueCandidate();
      if (countUniqueCandidates == unsolvedSquares) {
        break;
      } else {
        unsolvedSquares -= countUniqueCandidates;
      }
      countReduceRowColByGrid += iterateReductionOfCanContainInRowColByGrid();
      countReduceGridByRowCol += iterateReductionOfCanContainInGridByRowCol();
    }
    if (boardCheckComplete()) {
      textFieldAdd("Board was moderate!");
      textFieldAdd("Board solved with " + countReduceRowColByGrid + " row/column reductions by grid steps");
      textFieldAdd("Board solved with " + countReduceGridByRowCol + " grid reductions by grid steps");
      textFieldAdd("Board solved in a further " + countSoleCandidates + " solo candidate steps");
      textFieldAdd("Board solved in a further " + countUniqueCandidates + " unique candidate steps");
      return true;
    }
    textFieldAdd("Board was not moderate!");
    textFieldAdd("Board Unsolved; Performed " + countReduceRowColByGrid + " row/column reductions by grid steps");
    textFieldAdd("Board Unsolved; Performed " + countReduceGridByRowCol + " grid reductions by grid steps");
    textFieldAdd("Board Unsolved; Performed a further " + countSoleCandidates + " solo candidate steps");
    textFieldAdd("Board Unsolved; Performed a further " + countUniqueCandidates + " unique candidate steps");
    return false;
  }

  public boolean solveBoardHard() {
    if (boardCheckComplete()) {
      return true;
    }
    int unsolvedSquares = (boardSize * boardSize) * (boardSize * boardSize) - boardCheckPlacesFilled();
    int countSoleCandidates = 0;
    int countUniqueCandidates = 0;
    int countReduceRowColByGrid = 0;
    int countReduceGridByRowCol = 0;
    int countReduceSubVis = 0;
    int countReduceSubHid = 0;
    for (int q = 0; q < unsolvedSquares; q++) {
      countSoleCandidates += iterateSoleCandidate();
      if (countSoleCandidates == unsolvedSquares) {
        break;
      } else {
        unsolvedSquares -= countSoleCandidates;
      }
      countUniqueCandidates += iterateUniqueCandidate();
      if (countUniqueCandidates == unsolvedSquares) {
        break;
      } else {
        unsolvedSquares -= countUniqueCandidates;
      }
      countReduceRowColByGrid += iterateReductionOfCanContainInRowColByGrid();
      countReduceGridByRowCol += iterateReductionOfCanContainInGridByRowCol();
      countReduceSubVis += iterateReductionOfCanContainSubsetVisible();
      countReduceSubHid += iterateReductionOfCanContainSubsetHidden();
    }
    if (boardCheckComplete()) {
      textFieldAdd("Board was hard!");
      textFieldAdd("Board solved with " + countReduceSubVis + " subset reductions by visible subsets steps");
      textFieldAdd("Board solved with " + countReduceSubHid + " subset reductions by hidden subsets steps");
      textFieldAdd("Board solved in a further " + countReduceRowColByGrid + " row/column reductions by grid steps");
      textFieldAdd("Board solved in a further " + countReduceGridByRowCol + " grid reductions by grid steps");
      textFieldAdd("Board solved in a further " + countSoleCandidates + " solo candidate steps");
      textFieldAdd("Board solved in a further " + countUniqueCandidates + " unique candidate steps");
      return true;
    }
    textFieldAdd("Board was not hard!");
    textFieldAdd("Board Unsolved; Performed " + countReduceSubVis + " subset reductions by visible subsets steps");
    textFieldAdd("Board Unsolved; Performed " + countReduceSubHid + " subset reductions by hidden subsets steps");
    textFieldAdd("Board Unsolved; Performed a further " + countReduceRowColByGrid + " row/column reductions by grid steps");
    textFieldAdd("Board Unsolved; Performed a further " + countReduceGridByRowCol + " grid reductions by grid steps");
    textFieldAdd("Board Unsolved; Performed a further " + countSoleCandidates + " solo candidate steps");
    textFieldAdd("Board Unsolved; Performed a further " + countUniqueCandidates + " unique candidate steps");
    return false;
  }

  public int iterateSoleCandidate() {
    //System.out.println("Called: IterateSinglePossibility");
    int count = 0;
    while (checkSoleCandidate()) {
      //System.out.println("Looped: IterateSinglePossibility");
      count++;
    }
    return count;
  }

  public boolean checkSoleCandidate() {
    int candidates = 0;
    int candidate = 0;
    for (int k0 = 0; k0 < boardSize; k0++) {
      for (int k1 = 0; k1 < boardSize; k1++) {
        for (int k2 = 0; k2 < boardSize; k2++) {
          for (int k3 = 0; k3 < boardSize; k3++) {
            if (!cellFilled[k0][k1][k2][k3]) {
              candidates = 0;
              for (int k4 = 0; k4 < (boardSize * boardSize); k4++) {
                if (placeCanContain[k0][k1][k2][k3][k4]) {
                  candidate = k4;
                  candidates += 1;
                }
              }
              if (candidates == 1) {
                board[k0][k1][k2][k3] = candidate + 1;
                cellFilled[k0][k1][k2][k3] = true;
                deliminatePotential(k0, k1, k2, k3, candidate);
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  public int iterateUniqueCandidate() {
    //System.out.println("Called: IterateSinglePossibility");
    int count = 0;
    while (checkUniqueCandidate()) {
      //System.out.println("Looped: IterateSinglePossibility");
      count++;
    }
    return count;
  }

  public boolean checkUniqueCandidate() {
    int gridRowColX = 0;
    int gridRowColY = 0;
    int grcX = 0;
    int grcY = 0;
    int candidates = 0;
    int candR0 = 0;
    int candC0 = 0;
    int candR1 = 0;
    int candC1 = 0;
    for (int val = 0; val < (boardSize * boardSize); val++) {
      for (int outerGridRowCol = 0; outerGridRowCol < (boardSize * boardSize); outerGridRowCol++) { //Which G,R,C
        gridRowColY = outerGridRowCol % boardSize;
        gridRowColX = (outerGridRowCol - gridRowColY) / boardSize;
        //Grid Check
        if (!numberPresentG[val][gridRowColX][gridRowColY]) {
          candidates = 0;
          candR0 = gridRowColX;
          candC0 = gridRowColY;
          candR1 = 0;
          candC1 = 0;
          for (int innerGridRowCol = 0; innerGridRowCol < (boardSize * boardSize); innerGridRowCol++) { //Check elements of G,R,C
            grcY = innerGridRowCol % boardSize;
            grcX = (innerGridRowCol - grcY) / boardSize;
            if (!cellFilled[gridRowColX][gridRowColY][grcX][grcY] && placeCanContain[gridRowColX][gridRowColY][grcX][grcY][val]) {
              candR1 = grcX;
              candC1 = grcY;
              candidates += 1;
            }
          }
          if (candidates == 1) {
            board[candR0][candC0][candR1][candC1] = val + 1;
            cellFilled[candR0][candC0][candR1][candC1] = true;
            deliminatePotential(candR0, candC0, candR1, candC1, val);
            //System.out.println("grid returns " + val + " in " + candR0 + ", " + candC0 + ", " + candR1 + ", " + candC1);
            return true;
          }
        }
        //Row Check
        if (!numberPresentRowCol[0][val][outerGridRowCol]) {
          candidates = 0;
          candR0 = gridRowColX;
          candC0 = 0;
          candR1 = gridRowColY;
          candC1 = 0;
          for (int innerGridRowCol = 0; innerGridRowCol < (boardSize * boardSize); innerGridRowCol++) { //Check elements of G,R,C
            grcY = innerGridRowCol % boardSize;
            grcX = (innerGridRowCol - grcY) / boardSize;
            if (!cellFilled[gridRowColX][grcX][gridRowColY][grcY] && placeCanContain[gridRowColX][grcX][gridRowColY][grcY][val]) {
              candC0 = grcX;
              candC1 = grcY;
              candidates += 1;
            }
          }
          if (candidates == 1) {
            board[candR0][candC0][candR1][candC1] = val + 1;
            cellFilled[candR0][candC0][candR1][candC1] = true;
            deliminatePotential(candR0, candC0, candR1, candC1, val);
            //System.out.println("row returns");
            return true;
          }
        }
        //Column Check
        if (!numberPresentRowCol[1][val][outerGridRowCol]) {
          candidates = 0;
          candR0 = 0;
          candC0 = gridRowColX;
          candR1 = 0;
          candC1 = gridRowColY;
          for (int innerGridRowCol = 0; innerGridRowCol < (boardSize * boardSize); innerGridRowCol++) { //Check elements of G,R,C
            grcY = innerGridRowCol % boardSize;
            grcX = (innerGridRowCol - grcY) / boardSize;
            if (!cellFilled[grcX][gridRowColX][grcY][gridRowColY] && placeCanContain[grcX][gridRowColX][grcY][gridRowColY][val]) {
              candR0 = grcX;
              candR1 = grcY;
              candidates += 1;
            }
          }
          if (candidates == 1) {
            board[candR0][candC0][candR1][candC1] = val + 1;
            cellFilled[candR0][candC0][candR1][candC1] = true;
            deliminatePotential(candR0, candC0, candR1, candC1, val);
            //System.out.println("column returns");
            return true;
          }
        }
      }
    }
    return false;
  }

  public int iterateReductionOfCanContainInRowColByGrid() {
    int count = 0;
    while (reductionOfCanContainInRowColByGrid()) {
      count++;
    }
    return count;
  }

  public boolean reductionOfCanContainInRowColByGrid() { //Block and column / Row Interaction
    int rowVal = 0;
    boolean[] rows = new boolean[boardSize];
    int rowsVal = 0;
    int colVal = 0;
    boolean[] cols = new boolean[boardSize];
    int colsVal = 0;
    int deliminatedSquares = 0;
    for (int jq = 0; jq < boardSize; jq++) {
      rows[jq] = false;
      cols[jq] = false;
    }
    for (int j0 = 0; j0 < boardSize; j0++) {
      for (int j1 = 0; j1 < boardSize; j1++) {
        for (int val = 0; val < (boardSize * boardSize); val++) {
          if (!numberPresentG[val][j0][j1]) {
            for (int jq = 0; jq < boardSize; jq++) {
              rows[jq] = false;
              cols[jq] = false;
            } //System.out.println("Stuck");
            for (int k0 = 0; k0 < boardSize; k0++) {
              for (int k1 = 0; k1 < boardSize; k1++) {
                if (!cellFilled[j0][j1][k0][k1] && placeCanContain[j0][j1][k0][k1][val]) {
                  rows[k0] = true;
                  cols[k1] = true;
                }
              }
            }
            rowsVal = 0;
            colsVal = 0;
            for (int jq = 0; jq < boardSize; jq++) {
              if (rows[jq]) {
                rowsVal++;
                rowVal = jq;
              }
              if (cols[jq]) {
                colsVal++;
                colVal = jq;
              }
            }
            if (rowsVal == 1) {
              for (int jthC = 0; jthC < (boardSize * boardSize); jthC++) {
                int jthC1 = jthC % boardSize;
                int jthC0 = (jthC - jthC1) / boardSize;
                if (j1 != jthC0) {
                  if (!cellFilled[j0][jthC0][rowVal][jthC1] && placeCanContain[j0][jthC0][rowVal][jthC1][val]) {
                    deliminatedSquares++;
                    placeCanContain[j0][jthC0][rowVal][jthC1][val] = false;
                  }
                }
              }
              if (deliminatedSquares > 0) {
                return true;
              }
            }
            if (colsVal == 1) {
              for (int jthR = 0; jthR < (boardSize * boardSize); jthR++) {
                int jthR1 = jthR % boardSize;
                int jthR0 = (jthR - jthR1) / boardSize;
                if (j0 != jthR0) {
                  if (!cellFilled[jthR0][j1][jthR1][colVal] && placeCanContain[jthR0][j1][jthR1][colVal][val]) {
                    deliminatedSquares++;
                    placeCanContain[jthR0][j1][jthR1][colVal][val] = false;
                  }
                }
              }
              if (deliminatedSquares > 0) {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  public int iterateReductionOfCanContainInGridByRowCol() {
    int count = 0;
    while (reductionOfCanContainInGridByRowCol()) {
      count++;
    }
    return count;
  }

  public boolean reductionOfCanContainInGridByRowCol() { //Block / Block Interaction
    int deliminations = 0;
    int candidates = 0;
    for (int val = 0; val < (boardSize * boardSize); val++) {
      for (int jthCheckRow = 0; jthCheckRow < (boardSize * boardSize); jthCheckRow++) {
        if (!numberPresentRowCol[0][val][jthCheckRow]) {
          int jthCheckRow1 = jthCheckRow % boardSize;
          int jthCheckRow0 = (jthCheckRow - jthCheckRow1) / boardSize;
          for (int jthIgnore = 0; jthIgnore < boardSize; jthIgnore++) {
            candidates = 0;
            for (int jthSelect = 0; jthSelect < boardSize; jthSelect++) {
              if (jthSelect != jthIgnore) {
                for (int jthRotate = 0; jthRotate < boardSize; jthRotate++) {
                  if (!cellFilled[jthCheckRow0][jthSelect][jthCheckRow1][jthRotate] && placeCanContain[jthCheckRow0][jthSelect][jthCheckRow1][jthRotate][val]) {
                    candidates++;
                  }
                }
              }
            }
            if (candidates == 0) {
              //In Grid (jthCheckRow0, jthIgnore)
              for (int jthRotateR = 0; jthRotateR < boardSize; jthRotateR++) {
                for (int jthRotateC = 0; jthRotateC < boardSize; jthRotateC++) {
                  if (jthRotateR != jthCheckRow1) {
                    if (!cellFilled[jthCheckRow0][jthIgnore][jthRotateR][jthRotateC] && placeCanContain[jthCheckRow0][jthIgnore][jthRotateR][jthRotateC][val]) {
                      placeCanContain[jthCheckRow0][jthIgnore][jthRotateR][jthRotateC][val] = false;
                      deliminations++;
                    }
                  }
                }
              }
              if (deliminations > 0) {
                return true;
              }
            }
          }
        }
      }
      for (int jthCheckC = 0; jthCheckC < (boardSize * boardSize); jthCheckC++) {
        if (!numberPresentRowCol[1][val][jthCheckC]) {
          int jthCheckC1 = jthCheckC % boardSize;
          int jthCheckC0 = (jthCheckC - jthCheckC1) / boardSize;
          for (int jthIgnore = 0; jthIgnore < boardSize; jthIgnore++) {
            candidates = 0;
            for (int jthSelect = 0; jthSelect < boardSize; jthSelect++) {
              if (jthSelect != jthIgnore) {
                for (int jthRotate = 0; jthRotate < boardSize; jthRotate++) {
                  if (!cellFilled[jthSelect][jthCheckC0][jthRotate][jthCheckC1] && placeCanContain[jthSelect][jthCheckC0][jthRotate][jthCheckC1][val]) {
                    candidates++;
                  }
                }
              }
            }
            if (candidates == 0) {
              //In Grid (jthIgnore, jthCheckC0)
              for (int jthRotateR = 0; jthRotateR < boardSize; jthRotateR++) {
                for (int jthRotateC = 0; jthRotateC < boardSize; jthRotateC++) {
                  if (jthRotateC != jthCheckC1) {
                    if (!cellFilled[jthIgnore][jthCheckC0][jthRotateR][jthRotateC] && placeCanContain[jthIgnore][jthCheckC0][jthRotateR][jthRotateC][val]) {
                      placeCanContain[jthIgnore][jthCheckC0][jthRotateR][jthRotateC][val] = false;
                      deliminations++;
                    }
                  }
                }
              }
              if (deliminations > 0) {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  public int iterateReductionOfCanContainSubsetVisible() {
    int count = 0;
    while (reductionOfCanContainSubsetVisible()) {
      count++;
    }
    return count;
  }


  public boolean reductionOfCanContainSubsetVisible() {
    int minimumSubsetPresent = 0;
    int numberOfTrueSubsets = 0;
    int deliminations = 0;
    int numberOfVisibleSubsets = 0;
    int visibleSubsetIndex = 0;
    int s00 = 0;
    int s01 = 0;
    int s10 = 0;
    int s11 = 0;
    for (int subsetSize = 2; subsetSize < (boardSize * boardSize) - 1; subsetSize++) {
      int[] theSubset = new int[subsetSize];
      boolean subsetFound = false;
      int[][][] subsets = new int[boardSize][boardSize][subsetSize];
      int[][] visibleSubsets = new int[(boardSize * boardSize)][subsetSize];
      int[] visibleSubsetCount = new int[(boardSize * boardSize)];
      int[] subsetFoundAt = new int[(boardSize * boardSize)];
      boolean[][] subsetSizeTrue = new boolean[boardSize][boardSize];
      for (int j0 = 0; j0 < boardSize; j0++) {
        for (int j1 = 0; j1 < boardSize; j1++) {
          subsetFound = false;
          //Grid Subset Capture
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              subsetSizeTrue[k0][k1] = false;
            }
          }
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              minimumSubsetPresent = 0;
              if (!cellFilled[j0][j1][k0][k1]) {
                for (int val = 0; val < (boardSize * boardSize); val++) {
                  if (placeCanContain[j0][j1][k0][k1][val]) {
                    if (minimumSubsetPresent < subsetSize) {
                      subsets[k0][k1][minimumSubsetPresent] = val;
                      minimumSubsetPresent++;
                      if (minimumSubsetPresent == subsetSize) {
                        subsetSizeTrue[k0][k1] = true;
                      } else {
                        subsetSizeTrue[k0][k1] = false;
                      }
                    } else {
                      subsetSizeTrue[k0][k1] = false;
                    }
                  }
                }
              }
            }
          } //(starts with subsetSizeTrue == true iff it only has the subset size many possibilities)
          //Grid Subset Check
          numberOfTrueSubsets = 0;
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              if (subsetSizeTrue[k0][k1]) {
                subsetFoundAt[numberOfTrueSubsets] = k0 * boardSize + k1;
                numberOfTrueSubsets++;
              }
              visibleSubsetCount[k0 * boardSize + k1] = 0;
            }
          }
          if (numberOfTrueSubsets >= subsetSize) {
            numberOfVisibleSubsets = 1;
            visibleSubsetIndex = 1;
            s01 = subsetFoundAt[0] % boardSize;
            s00 = (subsetFoundAt[0] - s01) / boardSize;
            visibleSubsets[0] = subsets[s00][s01];
            visibleSubsetCount[0] = 1; 
            outer: for (int q0 = 1; q0 < numberOfTrueSubsets; q0++) {
              s01 = subsetFoundAt[q0] % boardSize;
              s00 = (subsetFoundAt[q0] - s01) / boardSize;
              inner: for (int q1 = 0; q1 < visibleSubsetIndex; q1++) {
                //System.out.println("q0 " + q0 + " and q1 " + q1);
                if (Arrays.equals(subsets[s00][s01], visibleSubsets[q1])) {
                  //System.out.println(Arrays.toString(subsets[s00][s01]) + " equal to " + Arrays.toString(visibleSubsets[q1]) + " at visibility " + visibleSubsetIndex + " and q0 " + q0 + " and q1 " + q1);
                  visibleSubsetCount[q1]++;
                  break inner;
                } else if (q1 == (visibleSubsetIndex - 1)) {
                  visibleSubsets[visibleSubsetIndex] = subsets[s00][s01];
                  visibleSubsetCount[visibleSubsetIndex]++;
                  visibleSubsetIndex++;
                  break inner;
                }
              }
            }
            for (int q = 0; q < visibleSubsetIndex; q++) {
              if (visibleSubsetCount[q] == subsetSize) {
                subsetFound = true;
                theSubset = visibleSubsets[q];
                break;
              }
            }
          }
          //Grid Subset Action
          if (subsetFound) {
            /*System.out.println(numberOfTrueSubsets + " " + visibleSubsetIndex);
            System.out.println(Arrays.toString(visibleSubsets[0]) + " " + visibleSubsetCount[0]);
            System.out.println(Arrays.toString(visibleSubsets[1]) + " " + visibleSubsetCount[1]);
            System.out.println(Arrays.toString(visibleSubsets[2]) + " " + visibleSubsetCount[2]);
            System.out.println(Arrays.toString(placeCanContain[0][1][1][2]));
            System.out.println(Arrays.toString(placeCanContain[0][1][2][0]));
            System.out.println(Arrays.toString(placeCanContain[0][1][2][1]));*/
            for (int k0 = 0; k0 < boardSize; k0++) {
              for (int k1 = 0; k1 < boardSize; k1++) {
                if (!cellFilled[j0][j1][k0][k1]) {
                  if (Arrays.equals(subsets[k0][k1], theSubset) && subsetSizeTrue[k0][k1]) {
                    continue;
                  } else {
                    for (int subsetIter = 0; subsetIter < subsetSize; subsetIter++) {
                      if (placeCanContain[j0][j1][k0][k1][theSubset[subsetIter]]) {
                        placeCanContain[j0][j1][k0][k1][theSubset[subsetIter]] = false;
                        deliminations++;
                      }
                    }
                  }
                }
              }
            }
            if (deliminations > 0) {
              System.out.println("Visible subset in grid " + (j0 * 3 + j1) + " of size " + subsetSize + ", " + Arrays.toString(theSubset));
              /*System.out.println(Arrays.toString(subsetSizeTrue[0]) + Arrays.toString(subsetSizeTrue[1]) + Arrays.toString(subsetSizeTrue[2]));
              System.out.println(Arrays.toString(placeCanContain[0][1][1][2]));
              System.out.println(Arrays.toString(placeCanContain[0][1][2][0]));
              System.out.println(Arrays.toString(placeCanContain[0][1][2][1]));*/
              return true;
            } else {
              subsetFound = false;
            }
          }
          //Row Subset Capture
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              subsetSizeTrue[k0][k1] = false;
            }
          }
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              minimumSubsetPresent = 0;
              if (!cellFilled[j0][k0][j1][k1]) {
                for (int val = 0; val < (boardSize * boardSize); val++) {
                  if (placeCanContain[j0][k0][j1][k1][val]) {
                    if (minimumSubsetPresent < subsetSize) {
                      subsets[k0][k1][minimumSubsetPresent] = val;
                      minimumSubsetPresent++;
                      if (minimumSubsetPresent == subsetSize) {
                        subsetSizeTrue[k0][k1] = true;
                      } else {
                        subsetSizeTrue[k0][k1] = false;
                      }
                    } else {
                      subsetSizeTrue[k0][k1] = false;
                    }
                  }
                }
              }
            }
          }
          //Row Subset Check
          numberOfTrueSubsets = 0;
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              if (subsetSizeTrue[k0][k1]) {
                subsetFoundAt[numberOfTrueSubsets] = k0 * boardSize + k1;
                numberOfTrueSubsets++;
              }
              visibleSubsetCount[k0 * boardSize + k1] = 0;
            }
          }
          if (numberOfTrueSubsets >= subsetSize) {
            numberOfVisibleSubsets = 1;
            visibleSubsetIndex = 1;
            s01 = subsetFoundAt[0] % boardSize;
            s00 = (subsetFoundAt[0] - s01) / boardSize;
            visibleSubsets[0] = subsets[s00][s01];
            visibleSubsetCount[0] = 1;
            outer: for (int q0 = 1; q0 < numberOfTrueSubsets; q0++) {
              s01 = subsetFoundAt[q0] % boardSize;
              s00 = (subsetFoundAt[q0] - s01) / boardSize;
              inner: for (int q1 = 0; q1 < visibleSubsetIndex; q1++) {
                if (Arrays.equals(subsets[s00][s01], visibleSubsets[q1])) {
                  visibleSubsetCount[q1]++;
                  break inner;
                } else if (q1 == (visibleSubsetIndex - 1)) {
                  visibleSubsets[visibleSubsetIndex] = subsets[s00][s01];
                  visibleSubsetCount[visibleSubsetIndex]++;
                  visibleSubsetIndex++;
                  break inner;
                }
              }
            }
            for (int q = 0; q < visibleSubsetIndex; q++) {
              if (visibleSubsetCount[q] == subsetSize) {
                subsetFound = true;
                theSubset = visibleSubsets[q];
                break;
              }
            }
          }
          //Row Subset Action
          if (subsetFound) {
            for (int k0 = 0; k0 < boardSize; k0++) {
              for (int k1 = 0; k1 < boardSize; k1++) {
                if (!cellFilled[j0][k0][j1][k1]) {
                  if (Arrays.equals(subsets[k0][k1], theSubset) && subsetSizeTrue[k0][k1]) {
                    continue;
                  } else {
                    for (int subsetIter = 0; subsetIter < subsetSize; subsetIter++) {
                      if (placeCanContain[j0][k0][j1][k1][theSubset[subsetIter]]) {
                        placeCanContain[j0][k0][j1][k1][theSubset[subsetIter]] = false;
                        deliminations++;
                      }
                    }
                  }
                }
              }
            }
            if (deliminations > 0) {
              System.out.println("Visible subset in row " + (j0 * 3 + j1) + " of size " + subsetSize + ", " + Arrays.toString(theSubset));
              return true;
            } else {
              subsetFound = false;
            }
          }
          //Column Subset Capture
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              subsetSizeTrue[k0][k1] = false;
            }
          }
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              minimumSubsetPresent = 0;
              if (!cellFilled[k0][j0][k1][j1]) {
                for (int val = 0; val < (boardSize * boardSize); val++) {
                  if (placeCanContain[k0][j0][k1][j1][val]) {
                    if (minimumSubsetPresent < subsetSize) {
                      subsets[k0][k1][minimumSubsetPresent] = val;
                      minimumSubsetPresent++;
                      if (minimumSubsetPresent == subsetSize) {
                        subsetSizeTrue[k0][k1] = true;
                      } else {
                        subsetSizeTrue[k0][k1] = false;
                      }
                    } else {
                      subsetSizeTrue[k0][k1] = false;
                    }
                  }
                }
              }
            }
          }
          //Column Subset Check
          numberOfTrueSubsets = 0;
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              if (subsetSizeTrue[k0][k1]) {
                subsetFoundAt[numberOfTrueSubsets] = k0 * boardSize + k1;
                numberOfTrueSubsets++;
              }
              visibleSubsetCount[k0 * boardSize + k1] = 0;
            }
          }
          if (numberOfTrueSubsets >= subsetSize) {
            numberOfVisibleSubsets = 1;
            visibleSubsetIndex = 1;
            s01 = subsetFoundAt[0] % boardSize;
            s00 = (subsetFoundAt[0] - s01) / boardSize;
            visibleSubsets[0] = subsets[s00][s01];
            visibleSubsetCount[0] = 1;
            outer: for (int q0 = 1; q0 < numberOfTrueSubsets; q0++) {
              s01 = subsetFoundAt[q0] % boardSize;
              s00 = (subsetFoundAt[q0] - s01) / boardSize;
              inner: for (int q1 = 0; q1 < visibleSubsetIndex; q1++) {
                if (Arrays.equals(subsets[s00][s01], visibleSubsets[q1])) {
                  visibleSubsetCount[q1]++;
                  break inner;
                } else if (q1 == (visibleSubsetIndex - 1)) {
                  visibleSubsets[visibleSubsetIndex] = subsets[s00][s01];
                  visibleSubsetCount[visibleSubsetIndex]++;
                  visibleSubsetIndex++;
                  break inner;
                }
              }
            }
            for (int q = 0; q < visibleSubsetIndex; q++) {
              if (visibleSubsetCount[q] == subsetSize) {
                subsetFound = true;
                theSubset = visibleSubsets[q];
                break;
              }
            }
          }
          //Column Subset Action
          if (subsetFound) {
            for (int k0 = 0; k0 < boardSize; k0++) {
              for (int k1 = 0; k1 < boardSize; k1++) {
                if (!cellFilled[k0][j0][k1][j1]) {
                  if (Arrays.equals(subsets[k0][k1], theSubset) && subsetSizeTrue[k0][k1]) {
                    continue;
                  } else {
                    for (int subsetIter = 0; subsetIter < subsetSize; subsetIter++) {
                      if (placeCanContain[k0][j0][k1][j1][theSubset[subsetIter]]) {
                        placeCanContain[k0][j0][k1][j1][theSubset[subsetIter]] = false;
                        deliminations++;
                      }
                    }
                  }
                }
              }
            }
            if (deliminations > 0) {
              System.out.println("Visible subset in column " + (j0 * 3 + j1) + " of size " + subsetSize + ", " + Arrays.toString(theSubset));
              return true;
            } else {
              subsetFound = false;
            }
          }
        }
      }
    }
    return false;
  }

  public int iterateReductionOfCanContainSubsetHidden() {
    int count = 0;
    while (reductionOfCanContainSubsetHidden()) {
      count++;
    }
    return count;
  }

  public boolean reductionOfCanContainSubsetHidden() { //if there are n values that can only be found in n squares those squares can contain no other values
    int[] valueIncidence = new int[(boardSize * boardSize)];
    int valuesIncidental = 0;
    int subsetSquares = 0;
    int s0 = 0;
    int s1 = 0;
    boolean[] hiddenValue = new boolean[(boardSize * boardSize)];
    boolean subsetCaptured;
    boolean subsetChecked;
    ArrayList<Integer> hiddenValues = new ArrayList<Integer>();
    ArrayList<Integer> hiddenSubsetSquares = new ArrayList<Integer>();
    PermutationComputation permVals = new PermutationComputation();
    PermutationComputation permSquares = new PermutationComputation();
    int deliminations = 0;
    for (int subsetSize = 2; subsetSize < (boardSize * boardSize) - 1; subsetSize++) {
      int[] theSubset = new int[subsetSize];
      int[] theSquares = new int[subsetSize];
      for (int j0 = 0; j0 < boardSize; j0++) {
        for (int j1 = 0; j1 < boardSize; j1++) {
          //Grid Capture
          subsetCaptured = false;
          subsetChecked = false;
          valuesIncidental = 0;
          hiddenValues.clear();
          for (int q = 0; q < (boardSize * boardSize); q++) {
            valueIncidence[q] = 0;
            hiddenValue[q] = false;
          }
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              if (!cellFilled[j0][j1][k0][k1]) {
                for (int q = 0; q < (boardSize * boardSize); q++) {
                  if (placeCanContain[j0][j1][k0][k1][q]) {
                    valueIncidence[q]++;
                  }
                }
              }
            }
          }
          for (int q = 0; q < (boardSize * boardSize); q++) {
            if (valueIncidence[q] == subsetSize) {
              valuesIncidental++;
            }
          }
          if (valuesIncidental >= subsetSize) {
            subsetCaptured = true;
          }
          //Grid Check
          if (subsetCaptured) { //there are n or more values that appear only n times, now find the squares
            hiddenSubsetSquares.clear();
            subsetSquares = 0;
            for (int q = 0; q < (boardSize * boardSize); q++) {
              if (valueIncidence[q] == subsetSize) {
                hiddenValue[q] = true;
                hiddenValues.add(q);
              }
            }
            for (int k0 = 0; k0 < boardSize; k0++) {
              for (int k1 = 0; k1 < boardSize; k1++) {
                if (!cellFilled[j0][j1][k0][k1]) {
                  valuesIncidental = 0;
                  for (int q = 0; q < (boardSize * boardSize); q++) {
                    if (placeCanContain[j0][j1][k0][k1][q] && hiddenValue[q]) {
                      valuesIncidental++;
                    }
                  }
                  if (valuesIncidental >= subsetSize) {
                    hiddenSubsetSquares.add(k0 * 3 + k1);
                    subsetSquares++;
                  }
                }
              }
            }
            if (subsetSquares >= subsetSize) {
              boolean[][] subsets = new boolean[subsetSquares][hiddenValues.size()];
              boolean falseDeclared = true;
              for (int square = 0; square < subsetSquares; square++) {
                for (int vals = 0; vals < hiddenValues.size(); vals++) {
                  s1 = hiddenSubsetSquares.get(square) % boardSize;
                  s0 = (hiddenSubsetSquares.get(square) - s1) / boardSize;
                  if (placeCanContain[j0][j1][s0][s1][hiddenValues.get(vals)]) {
                    subsets[square][vals] = true;
                  } else {
                    subsets[square][vals] = false;
                  }
                }
              }
              permVals.clear();
              permVals.permutationCompute(subsetSize, hiddenValues.size());
              permSquares.clear();
              permSquares.permutationCompute(subsetSize, subsetSquares);
              outer: for (int square = 0; square < permSquares.size(); square++) {
                for (int vals = 0; vals < permVals.size(); vals++) {
                  falseDeclared = false;
                  for (int b0 = 0; b0 < subsetSize; b0++) {
                    for (int b1 = 0; b1 < subsetSize; b1++) {
                      if (!subsets[permSquares.getget(square, b1)][permVals.getget(vals, b0)]) {
                        falseDeclared = true;
                      }
                    }
                  }
                  if (!falseDeclared) {
                    theSubset = permVals.get(vals);
                    theSquares = permSquares.get(square);
                    for (int h = 0; h < subsetSize; h++) {
                      theSubset[h] = hiddenValues.get(theSubset[h]);
                      theSquares[h] = hiddenSubsetSquares.get(theSquares[h]);
                    }
                    subsetChecked = true;
                    break outer;
                  }
                }
              }
            }
          }
          //Grid Action
          if (subsetChecked) {
            ArrayList<Integer> subVoids = new ArrayList<Integer>();
            for (int h = 0; h < subsetSize; h++) {
              subVoids.add(theSubset[h]);
            }
            for (int h = 0; h < subsetSize; h++) {
              s1 = theSquares[h] % boardSize;
              s0 = (theSquares[h] - s1) / boardSize;
              for (int val = 0; val < (boardSize * boardSize); val++) {
                if (placeCanContain[j0][j1][s0][s1][val] && !subVoids.contains(val)) {
                  placeCanContain[j0][j1][s0][s1][val] = false;
                  deliminations++;
                }
              }
            }
            if (deliminations > 0) {
              System.out.println("Hidden subset in grid " + (j0 * 3 + j1) + " of size " + subsetSize + ", " + Arrays.toString(theSubset));
              return true;
            } else {
              subsetChecked = false;
            }
          }
          //Row Capture
          subsetCaptured = false;
          subsetChecked = false;
          valuesIncidental = 0;
          hiddenValues.clear();
          for (int q = 0; q < (boardSize * boardSize); q++) {
            valueIncidence[q] = 0;
            hiddenValue[q] = false;
          }
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              if (!cellFilled[j0][k0][j1][k1]) {
                for (int q = 0; q < (boardSize * boardSize); q++) {
                  if (placeCanContain[j0][k0][j1][k1][q]) {
                    valueIncidence[q]++;
                  }
                }
              }
            }
          }
          for (int q = 0; q < (boardSize * boardSize); q++) {
            if (valueIncidence[q] == subsetSize) {
              valuesIncidental++;
            }
          }
          if (valuesIncidental >= subsetSize) {
            subsetCaptured = true;
          }
          //Row Check
          if (subsetCaptured) {
            hiddenSubsetSquares.clear();
            subsetSquares = 0;
            for (int q = 0; q < (boardSize * boardSize); q++) {
              if (valueIncidence[q] == subsetSize) {
                hiddenValue[q] = true;
                hiddenValues.add(q);
              }
            }
            for (int k0 = 0; k0 < boardSize; k0++) {
              for (int k1 = 0; k1 < boardSize; k1++) {
                if (!cellFilled[j0][k0][j1][k1]) {
                  valuesIncidental = 0;
                  for (int q = 0; q < (boardSize * boardSize); q++) {
                    if (placeCanContain[j0][k0][j1][k1][q] && hiddenValue[q]) {
                      valuesIncidental++;
                    }
                  }
                  if (valuesIncidental >= subsetSize) {
                    hiddenSubsetSquares.add(k0 * 3 + k1);
                    subsetSquares++;
                  }
                }
              }
            }
            if (subsetSquares >= subsetSize) {
              boolean[][] subsets = new boolean[subsetSquares][hiddenValues.size()];
              boolean falseDeclared = true;
              for (int square = 0; square < subsetSquares; square++) {
                for (int vals = 0; vals < hiddenValues.size(); vals++) {
                  s1 = hiddenSubsetSquares.get(square) % boardSize;
                  s0 = (hiddenSubsetSquares.get(square) - s1) / boardSize;
                  if (placeCanContain[j0][s0][j1][s1][hiddenValues.get(vals)]) {
                    subsets[square][vals] = true;
                  } else {
                    subsets[square][vals] = false;
                  }
                }
              }
              permVals.clear();
              permVals.permutationCompute(subsetSize, hiddenValues.size());
              permSquares.clear();
              permSquares.permutationCompute(subsetSize, subsetSquares);
              outer: for (int square = 0; square < permSquares.size(); square++) {
                for (int vals = 0; vals < permVals.size(); vals++) {
                  falseDeclared = false;
                  for (int b0 = 0; b0 < subsetSize; b0++) {
                    for (int b1 = 0; b1 < subsetSize; b1++) {
                      if (!subsets[permSquares.getget(square, b1)][permVals.getget(vals, b0)]) {
                        falseDeclared = true;
                      }
                    }
                  }
                  if (!falseDeclared) {
                    theSubset = permVals.get(vals);
                    theSquares = permSquares.get(square);
                    for (int h = 0; h < subsetSize; h++) {
                      theSubset[h] = hiddenValues.get(theSubset[h]);
                      theSquares[h] = hiddenSubsetSquares.get(theSquares[h]);
                    }
                    subsetChecked = true;
                    break outer;
                  }
                }
              }
            }

          }
          //Row Action
          if (subsetChecked) {
            ArrayList<Integer> subVoids = new ArrayList<Integer>();
            for (int h = 0; h < subsetSize; h++) {
              subVoids.add(theSubset[h]);
            }
            for (int h = 0; h < subsetSize; h++) {
              s1 = theSquares[h] % boardSize;
              s0 = (theSquares[h] - s1) / boardSize;
              for (int val = 0; val < (boardSize * boardSize); val++) {
                if (placeCanContain[j0][s0][j1][s1][val] && !subVoids.contains(val)) {
                  placeCanContain[j0][s0][j1][s1][val] = false;
                  deliminations++;
                }
              }
            }
            if (deliminations > 0) {
              System.out.println("Hidden subset in row " + (j0 * 3 + j1) + " of size " + subsetSize + ", " + Arrays.toString(theSubset));
              return true;
            } else {
              subsetChecked = false;
            }
          }
          //Col Capture
          subsetCaptured = false;
          subsetChecked = false;
          valuesIncidental = 0;
          hiddenValues.clear();
          for (int q = 0; q < (boardSize * boardSize); q++) {
            valueIncidence[q] = 0;
            hiddenValue[q] = false;
          }
          for (int k0 = 0; k0 < boardSize; k0++) {
            for (int k1 = 0; k1 < boardSize; k1++) {
              if (!cellFilled[k0][j0][k1][j1]) {
                for (int q = 0; q < (boardSize * boardSize); q++) {
                  if (placeCanContain[k0][j0][k1][j1][q]) {
                    valueIncidence[q]++;
                  }
                }
              }
            }
          }
          for (int q = 0; q < (boardSize * boardSize); q++) {
            if (valueIncidence[q] == subsetSize) {
              valuesIncidental++;
            }
          }
          if (valuesIncidental >= subsetSize) {
            subsetCaptured = true;
          }
          //Col Check
          if (subsetCaptured) {
            hiddenSubsetSquares.clear();
            subsetSquares = 0;
            for (int q = 0; q < (boardSize * boardSize); q++) {
              if (valueIncidence[q] == subsetSize) {
                hiddenValue[q] = true;
                hiddenValues.add(q);
              }
            }
            for (int k0 = 0; k0 < boardSize; k0++) {
              for (int k1 = 0; k1 < boardSize; k1++) {
                if (!cellFilled[k0][j0][k1][j1]) {
                  valuesIncidental = 0;
                  for (int q = 0; q < (boardSize * boardSize); q++) {
                    if (placeCanContain[k0][j0][k1][j1][q] && hiddenValue[q]) {
                      valuesIncidental++;
                    }
                  }
                  if (valuesIncidental >= subsetSize) {
                    hiddenSubsetSquares.add(k0 * 3 + k1);
                    subsetSquares++;
                  }
                }
              }
            }
            if (subsetSquares >= subsetSize) {
              boolean[][] subsets = new boolean[subsetSquares][hiddenValues.size()];
              boolean falseDeclared = true;
              for (int square = 0; square < subsetSquares; square++) {
                for (int vals = 0; vals < hiddenValues.size(); vals++) {
                  s1 = hiddenSubsetSquares.get(square) % boardSize;
                  s0 = (hiddenSubsetSquares.get(square) - s1) / boardSize;
                  if (placeCanContain[s0][j0][s1][j1][hiddenValues.get(vals)]) {
                    subsets[square][vals] = true;
                  } else {
                    subsets[square][vals] = false;
                  }
                }
              }
              permVals.clear();
              permVals.permutationCompute(subsetSize, hiddenValues.size());
              permSquares.clear();
              permSquares.permutationCompute(subsetSize, subsetSquares);
              outer: for (int square = 0; square < permSquares.size(); square++) {
                for (int vals = 0; vals < permVals.size(); vals++) {
                  falseDeclared = false;
                  for (int b0 = 0; b0 < subsetSize; b0++) {
                    for (int b1 = 0; b1 < subsetSize; b1++) {
                      if (!subsets[permSquares.getget(square, b1)][permVals.getget(vals, b0)]) {
                        falseDeclared = true;
                      }
                    }
                  }
                  if (!falseDeclared) {
                    theSubset = permVals.get(vals);
                    theSquares = permSquares.get(square);
                    for (int h = 0; h < subsetSize; h++) {
                      theSubset[h] = hiddenValues.get(theSubset[h]);
                      theSquares[h] = hiddenSubsetSquares.get(theSquares[h]);
                    }
                    subsetChecked = true;
                    break outer;
                  }
                }
              }
            }
          }
          //Col Action
          if (subsetChecked) {
            ArrayList<Integer> subVoids = new ArrayList<Integer>();
            for (int h = 0; h < subsetSize; h++) {
              subVoids.add(theSubset[h]);
            }
            for (int h = 0; h < subsetSize; h++) {
              s1 = theSquares[h] % boardSize;
              s0 = (theSquares[h] - s1) / boardSize;
              for (int val = 0; val < (boardSize * boardSize); val++) {
                if (placeCanContain[s0][j0][s1][j1][val] && !subVoids.contains(val)) {
                  placeCanContain[s0][j0][s1][j1][val] = false;
                  deliminations++;
                }
              }
            }
            if (deliminations > 0) {
              System.out.println("Hidden subset in column " + (j0 * 3 + j1) + " of size " + subsetSize + ", " + Arrays.toString(theSubset));
              return true;
            } else {
              subsetChecked = false;
            }
          }
        }
      }
    }
    return false;
  }


  public void deliminatePotential(int k0, int k1, int k2, int k3, int k4) {
    numberPresentRowCol[0][k4][k2 + k0 * board.length] = true;
    numberPresentRowCol[1][k4][k3 + k1 * board.length] = true;
    numberPresentG[k4][k0][k1] = true;
    for (int j0 = 0; j0 < boardSize; j0++) {
      for (int j1 = 0; j1 < boardSize; j1++) {
        placeCanContain[k0][j0][k2][j1][k4] = false; //row assertion
        placeCanContain[j0][k1][j1][k3][k4] = false; //column assertion
        placeCanContain[k0][k1][j0][j1][k4] = false; //grid assertion
      }
    }
  }

  public int[] subsetCollisionAt(int subsetSize, int numberOfTrueSubsets, int[] subsetFoundAt, int[][][] subsets) {
    int[] collisionsAt = new int[subsetSize];
    int numberOfPerfectCollisions = 0;
    subsetCollisionAtRolling(subsetSize, numberOfTrueSubsets, subsetFoundAt, subsets);

    return collisionsAt;
  }

  public void subsetCollisionAtRolling(int subsetSize, int numberOfTrueSubsets, int[] subsetFoundAt, int[][][] subsets) {
    int q1 = 0;
    int q0 = 0;
    for (int q = 0; q < numberOfTrueSubsets; q++) {
      subsetCollisionAtRolling(subsetSize, numberOfTrueSubsets - 1, subsetFoundAt, subsets);
      q1 = subsetFoundAt[q] % boardSize;
      q0 = (subsetFoundAt[q] - q1) / boardSize;
      //if (Arrays.equals(subsets[s0j0][s0j1], subsets[s1j0][s1j1])) {
      //    subsetPair1 = q0; subsetPair2 = q1; subsetFound = true;
      //    break;
      //}
    }
  }

  public boolean placeCanContainCheckDoesCollide(int j0, int j1, int j2, int j3, int k0, int k1, int k2, int k3, int collisions) {
    int counted = 0;
    for (int q = 0; q < (boardSize * boardSize); q++) {
      if (placeCanContain[j0][j1][j2][j3][q] && placeCanContain[k0][k1][k2][k3][q]) {
        counted++;
      }
    }
    if (counted >= collisions) {
      return true;
    }
    return false;
  }

  public boolean[] placeCanContainCheckCollision(int j0, int j1, int j2, int j3, int k0, int k1, int k2, int k3) {
    boolean[] collidingValues = new boolean[(boardSize * boardSize)];
    for (int q = 0; q < (boardSize * boardSize); q++) {
      if (placeCanContain[j0][j1][j2][j3][q] && placeCanContain[k0][k1][k2][k3][q]) {
        collidingValues[q] = true;
      } else {
        collidingValues[q] = false;
      }
    }
    return collidingValues;
  }

}
