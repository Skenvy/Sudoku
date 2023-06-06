REM Run as .\scripts\build.bat from the repository root.
cd sudoku
call mvn clean package
FOR /F "delims=" %%i IN ('mvn help:evaluate -Dexpression^="project.version" -q -DforceStdout') DO set VER=%%i
cd ../sudoku-gui
call mvn clean package -P target-adjacent-local-build -Delicious.version=%VER%
cd ..
