# [Sudoku](https://github.com/Skenvy/Sudoku)
## What it is at the moment
An implementation of a Sudoku solver (currently with Java JFrame), that I made during uni, as the first project I really enjoyed, with the intent of formalising how easy a particlar sudoku puzzle would be to solve by determining what moves can be made deterministically, rather than just iterating guesses.
## What I'd like to make it
The goal of this is to eventually create something similar to [opensudoku](https://github.com/romario333/opensudoku), or the [newer, current](https://github.com/ogarcia/opensudoku), version of it, as well as putting it on the playstore [similar to ~](https://play.google.com/store/apps/details?id=org.moire.opensudoku), with the inclusion of the originally intended feature/purpose of the JFrame app of determining available moves and how complex a particular game is.
## Setup
\[[JDK](https://openjdk.java.net/)\] 
\[[VS Code](https://code.visualstudio.com/docs/languages/java)\] 
## Run on Desktop
On any setup that has **make**, `make run` will be your friend. Otherwise, `mvn clean compile exec:java` will do the same job.
