package com.skenvy.sudoku;

import java.lang.Math;

public class Utility {

    public static boolean isIntegerSquared(int square){
        double root = Math.sqrt(square);
        return (Math.floor(root) == Math.ceil(root));
    }

    public static int integerSquareRoot(int square){
        return (int) (Math.sqrt(square));
    }

}