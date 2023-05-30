#!/usr/bin/env bash
set -euxo pipefail

cd sudoku
mvn clean package
export VER=$(mvn help:evaluate -Dexpression="project.version" -q -DforceStdout)
cd ../sudoku-gui
mvn clean package -Delicious.version=$VER
cd ..
