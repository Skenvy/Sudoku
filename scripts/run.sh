#!/usr/bin/env bash
set -euxo pipefail

# Run as './scripts/run.sh' from the repository root.
make -C sudoku-gui run
