#!/usr/bin/env bash
set -euxo pipefail

# Run as './scripts/build.sh' from the repository root.
make -C sudoku build_noninteractive
make -C sudoku-gui build_noninteractive_locally
