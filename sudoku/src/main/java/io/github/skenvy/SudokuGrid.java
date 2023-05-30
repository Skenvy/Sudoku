package io.github.skenvy;

import java.util.List;

import javax.swing.text.Utilities;

import io.github.skenvy.CellCollection;
import io.github.skenvy.Utility;

import java.util.ArrayList;

/***
 * A Sudoku Grid ~ the whole "board" with N rows, columns, and boxes.
 */
public class SudokuGrid {

    /***
     * A list of the rows ~ from top to bottom
     */
    final private List<CellCollection> rows;

    /***
     * A list of the columns ~ from left to right
     */
    final private List<CellCollection> columns;

    /***
     * A list of the boxes [or blocks|regions] ~
     * from top left across and then down to bottom right.
     */
    final private List<CellCollection> boxes;

    /***
     * A 2d array of the cells, first indexing by row, then by column
     */
    final private Cell[][][][] cells;

    /***
     * Initialise an empty sudoku grid for a standard Sudoku board
     */
    public SudokuGrid() throws SudokuCellGridInvalidGridShapeException, Cell.SudokuCellInvalidInitialValueException{
        this.cells = createCellGrid(new int[3][3][3][3]);
        this.rows = null;
        this.columns = null;
        this.boxes = null;
    }

    /***
     * Initialise an empty sudoku grid for a custom sized Sudoku board
     */
    public SudokuGrid(int boardSize) throws SudokuCellGridInvalidGridShapeException, Cell.SudokuCellInvalidInitialValueException{
        this.cells = createCellGrid(new int[3][3][3][3]);
        int collectionSize = boardSize*boardSize;
        this.rows = null;
        this.columns = null;
        this.boxes = null;
    }

    /***
     * Initialise a grid from one already established.
     */
    public SudokuGrid(SudokuGrid initialGrid, boolean exactReplica) throws SudokuCellGridInvalidGridShapeException, Cell.SudokuCellInvalidInitialValueException{
        this.cells = createCellGrid(new int[3][3][3][3]);
        this.rows = initialGrid.getRows();
        this.columns = initialGrid.getColumns();
        this.boxes = initialGrid.getBoxes();
    }

    private Cell[][][][] createCellGrid(int[][] valuesGrid) throws SudokuCellGridInvalidGridShapeException, Cell.SudokuCellInvalidInitialValueException{
        if(valuesGrid.length != valuesGrid[0].length){
            throw new SudokuCellGridInvalidGridShapeException(valuesGrid.length, valuesGrid[0].length);
        } else if (!Utility.isIntegerSquared(valuesGrid.length)) {
            throw new SudokuCellGridInvalidGridShapeException(valuesGrid.length);
        }
        int boardSize = Utility.integerSquareRoot(valuesGrid.length);
        int[][][][] newValuesGrid = new int[boardSize][boardSize][boardSize][boardSize];
        for(int bR = 0; bR < boardSize; bR++){ // iterate the boardRow ~ "000111222"
            for(int bC = 0; bC < boardSize; bC++){ // iterate the boardColumn ~ "000111222"
                for(int biR = 0; biR < boardSize; biR++){ // iterate the boxInternalRow ~ "012012012"
                    for(int biC = 0; biC < boardSize; biC++){ // iterate the boxInternalColumn ~ "012012012
                        newValuesGrid[bR][bC][biR][biC] = valuesGrid[boardSize*bR+biR][boardSize*bC+biC];
                    }
                }
            }
        }
        return createCellGrid(newValuesGrid);
    }

    private Cell[][][][] createCellGrid(int[][][][] valuesGrid) throws SudokuCellGridInvalidGridShapeException, Cell.SudokuCellInvalidInitialValueException{
        if((valuesGrid.length != valuesGrid[0].length) || (valuesGrid.length != valuesGrid[0][0].length) || (valuesGrid.length != valuesGrid[0][0][0].length)){
            throw new SudokuCellGridInvalidGridShapeException(valuesGrid.length, valuesGrid[0].length, valuesGrid[0][0].length, valuesGrid[0][0][0].length);
        }
        int boardSize = valuesGrid.length;
        Cell[][][][] tempCells = new Cell[boardSize][boardSize][boardSize][boardSize];
        List<CellCollection> rows = initialiseEmptyCellCollections(boardSize);
        List<CellCollection> columns = initialiseEmptyCellCollections(boardSize);
        List<CellCollection> boxes = initialiseEmptyCellCollections(boardSize);
        for(int bR = 0; bR < boardSize; bR++){ // iterate the boardRow ~ "000111222"
            for(int bC = 0; bC < boardSize; bC++){ // iterate the boardColumn ~ "000111222"
                for(int biR = 0; biR < boardSize; biR++){ // iterate the boxInternalRow ~ "012012012"
                    for(int biC = 0; biC < boardSize; biC++){ // iterate the boxInternalColumn ~ "012012012
                        // We must first create the cell with a reference to its row, column, and box
                        int rowIndex = boardSize*bR + biR;
                        int colIndex = boardSize*bC + biC;
                        int boxIndex = boardSize*bR + bC;
                        tempCells[bR][bC][biR][biC] = new Cell(valuesGrid[bR][bC][biR][biC], boardSize, rows.get(rowIndex), columns.get(colIndex), boxes.get(boxIndex));
                        // then add the created cell to that same row, column, and box.
                        rows.get(rowIndex).addCellToCollection(tempCells[bR][bC][biR][biC]);
                        columns.get(colIndex).addCellToCollection(tempCells[bR][bC][biR][biC]);
                        boxes.get(boxIndex).addCellToCollection(tempCells[bR][bC][biR][biC]);
                    }
                }
            }
        }
        return tempCells;
    }

    List<CellCollection> initialiseEmptyCellCollections(int boardSize){
        List<CellCollection> tempCollections = new ArrayList<CellCollection>(boardSize*boardSize);
        for(int k = 0; k < boardSize*boardSize; k++){
            tempCollections.add(new CellCollection(boardSize));
        }
        return tempCollections;
    }

    public List<CellCollection> getRows(){
        return this.rows;
    }

    public List<CellCollection> getColumns(){
        return this.columns;
    }

    public List<CellCollection> getBoxes(){
        return this.boxes;
    }

    /***
	 * Used for cell related exceptions.
	 */
	public class SudokuCellGridException extends Exception {

		public SudokuCellGridException(String string) {
			super(string);
		}
		
	}

    public class SudokuCellGridInvalidGridShapeException extends Exception {

		public SudokuCellGridInvalidGridShapeException(int macroRows, int macroCols, int microRows, int microCols) {
			super(String.format("The shape of the input grid was not a valid square, received nested array lengths of %d, %d, %d, %d", macroRows, macroCols, microRows, microCols));
		}

        public SudokuCellGridInvalidGridShapeException(int rows, int cols) {
			super(String.format("The shape of the input grid was not a valid square, received nested array lengths of %d, %d", rows, cols));
		}

        public SudokuCellGridInvalidGridShapeException(int rows) {
			super(String.format("The shape of the input grid was not a valid square, received non square value for collections count, received %d", rows));
		}
		
	}

}
