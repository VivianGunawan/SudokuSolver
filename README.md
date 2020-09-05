# SudokuSolver

🗓 Spring 2018

## Project Description

A Java program that solves traditional 9x9 sudoku puzzles using recursive-backtracking.

Takes a .txt file as input where the puzzle is represented row by row using integers separated by a space, empty cells are represented with the 0 integer.

Sample Input provided:

- basic.txt
- minimum.txt ← 17 clues is the minimum for proper sudoku. (only one solution)
- broken.txt ← a sudoku with more than one solution.
- not.txt ← an impostor sudoku with no solution.

## How to run

```bash
javac Sudoku.java
java Sudoku
```
