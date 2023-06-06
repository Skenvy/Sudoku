REM The below is the suggested way to invoke java with multiple jars in the classpath and target a Main
REM java -cp .\sudoku\target\sudoku-1.0-SNAPSHOT.jar;.\gui\target\sudoku-gui-1.0-SNAPSHOT.jar io.github.skenvy.SudokuGUI
REM But using http://www.mojohaus.org/exec-maven-plugin/usage.html along with the additionalClasspathElements
REM defined at http://maven.apache.org/surefire/maven-surefire-plugin/examples/configuring-classpath.html
REM it's possible to just run it with a maven command.
cd sudoku
FOR /F "delims=" %%i IN ('mvn help:evaluate -Dexpression^="project.version" -q -DforceStdout') DO set VER=%%i
cd ../sudoku-gui
call mvn exec:java -Delicious.version=%VER% -P target-adjacent-local-build
cd ..
