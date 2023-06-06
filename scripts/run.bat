REM Run as .\scripts\run.bat from the repository root.
cd sudoku
FOR /F "delims=" %%i IN ('mvn help:evaluate -Dexpression^="project.version" -q -DforceStdout') DO set VER=%%i
cd ../sudoku-gui
call mvn clean compile exec:java -Dexec.mainClass="io.github.skenvy.SudokuGUI" -P target-adjacent-local-build -Delicious.version=%VER%
cd ..
