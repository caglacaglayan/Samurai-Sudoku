package samurai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SudokuSolver {

    private static final int GRID_SIZE = 9;

    private File dosya = new File("sudoku.txt");
    private FileReader fr = new FileReader(dosya);
    private BufferedReader br = new BufferedReader(fr);
    private Scanner oku;
    public static int[][] sudoku = new int[21][21];
    public static int[][] boardOne = new int[9][9];
    public static int[][] boardTwo = new int[9][9];
    public static int[][] boardMiddle = new int[9][9];
    public static int[][] boardFour = new int[9][9];
    public static int[][] boardFive = new int[9][9];
    public long startTime = System.currentTimeMillis();
    public long elapsedTime = 0L;

    public SudokuSolver() throws IOException{
        openFile();
        readFile();
        closeFile();

        boardOne = createBoard(sudoku,0,0);
        boardTwo = createBoard(sudoku,0,12);
        boardMiddle = createBoard(sudoku,6,6);
        boardFour = createBoard(sudoku,12,0);
        boardFive = createBoard(sudoku,12,12);

        Thread threadOne = new Thread() {
            @Override
            public void run() {
                solvedBoard(boardOne);
            }
        };
        threadOne.start();

        Thread threadTwo =new Thread(){
            @Override
            public void run() {
                solvedBoard(boardTwo);
            }
        };
        threadTwo.start();

        Thread threadMiddle =new Thread(){
            @Override
            public void run() {
                solvedBoard(boardMiddle);
            }
        };
        threadMiddle.start();

        Thread threadFour =new Thread(){
            @Override
            public void run() {
                solvedBoard(boardFour);
            }
        };
        threadFour.start();

        Thread threadFive =new Thread(){
            @Override
            public void run() {
                solvedBoard(boardFive);
            }
        };
        threadFive.start();
    }

    public void openFile() {
        try {
            oku = new Scanner(dosya);
        } catch (Exception E){
            System.out.println("Sudoku yüklenemedi !");
        }
    }

    public void readFile() throws IOException {

        for(int i=0; i<6;i++){
            for(int j=9;j<12;j++){
                sudoku[i][j] = -1;
            }
        }
        for(int i=15; i<21;i++){
            for(int j=9;j<12;j++){
                sudoku[i][j] = -1;
            }
        }
        for(int i=9; i<12;i++){
            for(int j=0;j<6;j++){
                sudoku[i][j] = -1;
            }
        }
        for(int i=9; i<12;i++){
            for(int j=15;j<21;j++){
                sudoku[i][j] = -1;

            }
        }

        String[] eleman = new String[440];
        int c = 0;
        int d = 0;

        while((c = br.read()) != -1)
        {
            char character = (char) c;
            eleman[d] = String.valueOf(character);
            d++;

        }

        int w = 0;
        String a;
        for (int i=0 ; i<21 ; i++) {
            for (int j=0; j<21; j++) {
                if(sudoku[i][j] != -1){
                    a = eleman[w];

                    if(a.equals("\n")){
                        w++;
                        a = eleman[w];
                        if(a.equals("*")){
                            sudoku[i][j] = 0;
                        }
                        else {
                            sudoku[i][j] = Integer.parseInt(a);
                        }
                    }
                    else if(a.equals("*")){
                        sudoku[i][j] = 0;
                    }
                    else{
                        sudoku[i][j] = Integer.parseInt(a);
                    }
                    w++;
                }
            }
        }
    }

    public void closeFile(){
        oku.close();
    }

    private static void solvedBoard(int[][] board){
        if (solveBoard(board)) {
            System.out.println("Solved successfully!");
            printBoard(board);
        }
        else {
            System.out.println("Unsolvable board :(");
        }
    }

    private static int[][] createBoard(int[][] sudoku, int startRow, int startColumn){
        int[][] board=new int[9][9];
        for (int row = startRow; row < startRow+GRID_SIZE; row++) {
            for (int column = startColumn; column < startColumn+GRID_SIZE; column++) {
                board[row-startRow][column-startColumn]=sudoku[row][column];
            }
        }
        return board;
    }

    private static void printBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("-----------");
            }
            for (int column = 0; column < GRID_SIZE; column++) {
                if (column % 3 == 0 && column != 0) {
                    System.out.print("|");
                }
                System.out.print(board[row][column]);
            }
            System.out.println();
        }
    }

    private static boolean isNumberInRow(int[][] board, int number, int row) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNumberInColumn(int[][] board, int number, int column) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][column] == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNumberInBox(int[][] board, int number, int row, int column) {
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;

        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValidPlacement(int[][] board, int number, int row, int column) {
        return !isNumberInRow(board, number, row) &&
                !isNumberInColumn(board, number, column) &&
                !isNumberInBox(board, number, row, column);
    }

    private static boolean solveBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if (board[row][column] == 0) {
                    for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) {
                        if (isValidPlacement(board, numberToTry, row, column)) {
                            board[row][column] = numberToTry;

                            if (solveBoard(board)) {
                                return true;
                            }
                            else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}


