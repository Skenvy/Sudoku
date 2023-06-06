cd sudoku
call mvn clean package
FOR /F "delims=" %%i IN ('mvn help:evaluate -Dexpression^="project.version" -q -DforceStdout') DO set VER=%%i
cd ../sudoku-gui
call mvn clean package -Delicious.version=%VER% -P target-adjacent-local-build
cd ..
