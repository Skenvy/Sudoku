#!/usr/bin/env bash
set -euxo pipefail

# The below is the suggested way to invoke java with multiple jars in the classpath and target a Main
# java -cp .\sudoku\target\sudoku-1.0-SNAPSHOT.jar;.\sudoku-gui\target\sudoku-gui-1.0-SNAPSHOT.jar io.github.skenvy.SudokuGUI
# But using http://www.mojohaus.org/exec-maven-plugin/usage.html along with the additionalClasspathElements
# defined at http://maven.apache.org/surefire/maven-surefire-plugin/examples/configuring-classpath.html
# it's possible to just run it with a maven command.
cd sudoku
export VER=$(mvn help:evaluate -Dexpression="project.version" -q -DforceStdout)
cd ../sudoku-gui
mvn exec:java -Delicious.version=$VER -P target-adjacent-local-build
cd ..
