# [Sudoku](https://github.com/Skenvy/Sudoku)
## What it is at the moment
An implementation of a Sudoku solver (currently with Java JFrame), that I made during uni, as the first project I really enjoyed, with the intent of formalising how easy a particlar sudoku puzzle would be to solve by determining what moves can be made deterministically, rather than just iterating guesses.
## What I'd like to make it
The goal of this is to eventually create something similar to [opensudoku](https://github.com/romario333/opensudoku), or the [newer, current](https://github.com/ogarcia/opensudoku), version of it, as well as putting it on the playstore [similar to ~](https://play.google.com/store/apps/details?id=org.moire.opensudoku). With the obvious inclusion of the originally intended feature/purpose of the JFrame app of determining available moves and how complex a particular game is.
## Setup
\[[JDK](https://openjdk.java.net/)\] 
\[[VS Code](https://code.visualstudio.com/docs/languages/java)\] 
## Run on Desktop
The underlying java application and its GUI can be created and run by using the `~/sudoku/_build.bat` and `~/sudoku/_gui.bat` to create the maven packages for `~/sudoku/application/` and `~/sudoku/desktop-gui/` which sits on top of the application code (such that when the application code is published, it can be consumed by the Android app build which prohibits the AWT/Swing in the desktop gui code). This is using Maven 3.6.1 currently.
