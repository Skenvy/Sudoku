package io.github.skenvy;

import java.util.List;
import java.util.ArrayList;

/***
 * A collection of cells ~ can be a row, column, or box|region|block.
 */
public class CellCollection {

    final private List<Cell> cells;

    /***
     * Initialise an empty collection to reference 9 cells in a row, column, or box
     */
    public CellCollection(){
        this.cells = new ArrayList<Cell>(9);
    }

    /***
     * Initialise an empty collection to reference N*N cells in a row, column, or box
     */
    public CellCollection(int boardSize){
        this.cells = new ArrayList<Cell>(boardSize*boardSize);
    }

    /***
     * Get a collection of the cells.
     */
    public List<Cell> getCells(){
        // Return new list instance to prevent removal from underlying collection.
        return new ArrayList<Cell>(this.cells);
    }

    public void addCellToCollection(Cell cell){
        this.cells.add(cell);
    }

    public int[] getValues(){
        int[] values = new int[this.cells.size()];
        for(int k = 0; k < this.cells.size(); k++){
            values[k] = this.cells.get(k).getValue();
        }
        return values;
    }

    /***
	 * Used for cell collection related exceptions.
	 */
	public class SudokuCellCollectionException extends Exception {

		public SudokuCellCollectionException(String string) {
			super(string);
		}
		
	}

}
