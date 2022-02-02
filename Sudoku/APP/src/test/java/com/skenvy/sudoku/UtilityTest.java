package com.skenvy.sudoku;

import java.util.HashSet;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class UtilityTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void isIntegerSquared()
    {
        int upToInteger = 5;
        // Set of square values
        HashSet<Integer> squares = new HashSet<Integer>(upToInteger);
        for(int k = 1; k <= upToInteger; k++){
            squares.add(k*k);
        }
        // If it's a square, isIntegerSquared should return true, otherwise false.
        for(int k = 1; k <= (upToInteger*upToInteger)+1; k++){
            assertEquals(Utility.isIntegerSquared(k), squares.contains(k));
        }
    }
}
