# [What it started as](https://github.com/Skenvy/Sudoku/releases/tag/v0.0.0)
An [implementation of a Sudoku solver](https://github.com/Skenvy/Sudoku/blob/v0.0.0/Sudoku/APP/src/main/java/com/skenvy/sudoku/sudoku.java), [utilising JFrames](https://github.com/Skenvy/Sudoku/blob/v0.0.0/Sudoku/GUI/src/main/java/com/skenvy/sudokuGUI/sudokuGUI.java), that I made during uni in my second year (2013), as the first project I really _enjoyed_, because it's easier to learn programming if you're using it to build something that has a purpose, as opposed to throw-away tutorial code.

I made it with the intent of formalising how easy a particlar sudoku puzzle would be to solve by determining _what moves can be made_, deterministically, rather than just iterating guesses.

See [the code as close to as it originally was that I could find](https://github.com/Skenvy/Sudoku/tree/v0.0.0), or [the release of that version](https://github.com/Skenvy/Sudoku/releases/tag/v0.0.0).

# What I'd like to make it
The "goal" of this is to """eventually""" create something similar to [opensudoku](https://github.com/romario333/opensudoku), or the ~~[newer, current](https://github.com/ogarcia/opensudoku)~~ [newerer, currenterer](https://gitlab.com/opensudoku/opensudoku), version of it, as well as putting it on the playstore [similar to ~](https://play.google.com/store/apps/details?id=org.moire.opensudoku), with the inclusion of the originally intended feature/purpose of the JFrame app of determining available moves and how complex a particular game is. 

# Devlog
## Java Build Tools
Although as part of the eventual goal of using this project as a canvas to learn some of the components of the Android development tool chain, I'd like to eventually try and swap from Maven to Gradle, I've had to remove the old gradle files that were loosely hanging around, as GitHub's [CodeQL tool for scanning java projects](https://codeql.github.com/docs/codeql-overview/supported-languages-and-frameworks/#), [which currently includes kotlin](https://docs.github.com/en/code-security/code-scanning/automatically-scanning-your-code-for-vulnerabilities-and-errors/customizing-code-scanning#changing-the-languages-that-are-analyzed), will [autobuild using Gradle rather than Maven if it detects any Gradle files present](https://docs.github.com/en/code-security/code-scanning/automatically-scanning-your-code-for-vulnerabilities-and-errors/configuring-the-codeql-workflow-for-compiled-languages#java--and-kotlin).
## Linting
The code is pretty gross at the moment, so we'll need to add a linter. [VS Code's page on linting java](https://code.visualstudio.com/docs/java/java-linting) suggets [Checkstyle](https://checkstyle.sourceforge.io/) ([source](https://github.com/checkstyle/checkstyle)). The [Google Java Style Guide](https://checkstyle.sourceforge.io/styleguides/google-java-style-20180523/javaguide.html) seem like a good place to start.
