import java.io.*;  
import java.util.*;

public class Sudoku {
    private int[][] grid;
    private boolean[][] valIsFixed; // Fixed values are values filled from original puzzle
    private boolean[][][] subgridHasVal; // For checking duplicates in 3x3 subgrid region
    private boolean[][] columnHasVal; // For checking if a given column already contains a given value
    private boolean[][] rowHasVal; // For checking if a given row already contains a given value*/

    // Constructor with all empty cells
    public Sudoku() {
        this.grid = new int[9][9];
        this.valIsFixed = new boolean[9][9];
        this.subgridHasVal = new boolean[9][9][10]; //third dimension is 10, because the possible values(1-9) used as indices.
        this.columnHasVal = new boolean[9][10];
        this.rowHasVal = new boolean[9][10];
    }

    public void placeVal(int val, int row, int col) {
        this.grid[row][col] = val;
        this.subgridHasVal[row / 3][col / 3][val] = true;
        this.columnHasVal[col][val] = true;
        this.rowHasVal[row][val] = true;
    }

    public void removeVal(int val, int row, int col) {
        this.grid[row][col] = 0;
        this.subgridHasVal[row / 3][col / 3][val] = false;
        this.columnHasVal[col][val] = false;
        this.rowHasVal[row][val] = false;
    }

    // Initializes puzzles, one line for each row ,integers separated by spaces, empty cells represented with 0
    public void readConfig(Scanner input) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int val = input.nextInt();
                this.placeVal(val, r, c);
                if (val != 0) {
                    this.valIsFixed[r][c] = true;
                }
            }
            input.nextLine();
        }
    }

    public void printGrid() {
        for (int r = 0; r < 9; r++) {
            this.printRowSeparator();
            for (int c = 0; c < 9; c++) {
                System.out.print("|");
                if (this.grid[r][c] == 0)
                    System.out.print("   ");
                else
                    System.out.print(" " + this.grid[r][c] + " ");
            }
            System.out.println("|");
        }
        this.printRowSeparator();
    }

    // A private helper method used by display() to print a line separating two rows of the puzzle.
    private static void printRowSeparator() {
        for (int i = 0; i < 9; i++)
            System.out.print("----");
        System.out.println("-");
    }

    // A private helper to determine if a value is appropriate for a given cell.
    private boolean isAllowed(int val, int row, int col) {
        return (!this.subgridHasVal[row / 3][col / 3][val] &&
                !this.columnHasVal[col][val] &&
                !this.rowHasVal[row][val]);
    }

    // A private helper to search for the next 'empty' == 0 cell.
    private int[] empty(){
        int[]cell= new int[2];
        for(int r=0; r<9; r++){
            for(int c=0; c<9;c++){
                if(grid[r][c]==0){
                    cell[0]=r;
                    cell[1]=c;
                    return cell;
                }
            }
        }
        cell[0]=-1;
        cell[1]=-1;
        return cell;
    }

    // Recursive-backtraking algorithm 
    public boolean solveRB(int row, int col){
        int[]emptyCell=empty();
        row = emptyCell[0];
        col = emptyCell[1];
        if(row==-1){
            // means all the cells in the grid, return;
            return true;
        }
        else{
            // fill in the cells
            for(int val=1;val<=9;val++){
                if(isAllowed(val,row,col)){
                    //makes sure value is appropriate
                    placeVal(val,row,col);
                    // placing value in grid
                    if(solveRB(row,col)){
                        return true;
                    }
                    //some number was wrong, change it
                    else {
                        removeVal(val, row, col);
                    }
                }
            }
            return false;
            //triggers backtracking
        }
    }


    // public "wrapper" method for solveRB().
    public boolean solve(){
        boolean foundSol = this.solveRB(0,0);
        return foundSol;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Sudoku puzzle = new Sudoku();

        System.out.print("Enter the name of the puzzle file: ");
        String filename = scan.nextLine();

        try {
            Scanner input = new Scanner(new File(filename));
            puzzle.readConfig(input);
        } catch (IOException e) {
            System.out.println("error accessing file " + filename);
            System.out.println(e);
            System.exit(1);
        }

        System.out.println();
        System.out.println("Here is the initial puzzle: ");
        puzzle.printGrid();
        System.out.println();

        if (puzzle.solve()) {
            System.out.println("Here is the solution: ");
        } else {
            System.out.println("No solution could be found.");
            System.out.println("Here is the current state of the puzzle:");
        }
        puzzle.printGrid();
    }
}

