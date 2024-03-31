import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private final int boardColumns = 7;
    private final int boardRows = 6;
    private String[] board;

    private String playerName1;
    private String playerName2;
    private String symbol1;
    private String symbol2;


    public Game(){
        board = new String[boardColumns * boardRows];
        Arrays.fill(board, "O");

        Scanner input = new Scanner(System.in);
        System.out.println("Hello gamer! Please, enter player 1 name: ");
        playerName1 = input.nextLine();

        System.out.println(playerName1 + ", choose symbol R (for Red) or Y (for Yellow): ");
        boolean symbolEntered = false;
        while (!symbolEntered) {
            symbol1 = input.nextLine().toUpperCase();

            if (!symbol1.equals("R") && !symbol1.equals("Y")) {
                System.out.println("Only R (for Red) or Y (for Yellow) allowed! Again: ");
            } else {
                symbolEntered = true;
            }
        }

        System.out.println("Please, enter player 2 name: ");
        playerName2 = input.nextLine();
        if (symbol1.equals("R")) {
            symbol2 = "Y";
        } else {
            symbol2 = "R";
        }
        System.out.println(playerName2 + " your symbol is " + symbol2);
    }

    public void printBoard(String[] board) {
        System.out.println("Game board:");
        for (int i = 0; i < board.length; i++) {
            System.out.print(" " + board[i] + " ");
            if (i % 7 == 6) {
                System.out.println("");
            }
        }
    }

    public void playGame(){
        printBoard(board);
        while (true) {
            if (playerAction(playerName1, symbol1)) {
                System.out.println("Congratulations " + playerName1 + "! You win!");
                break;
            }
            if (playerAction(playerName2, symbol2)) {
                System.out.println("Congratulations " + playerName2 + "! You win!");
                break;
            }
        }
    }
    public boolean playerAction(String playerName, String symbol) {
        Scanner input = new Scanner(System.in);

        System.out.println(playerName + " pick a column");

        int position = 0;
        boolean isValid = false;
        while(!isValid) {
            try {
                position = input.nextInt() - 1;

                if (position < 0 || position > boardColumns) {
                    System.out.println("Must be a value from 1 to 7! Pick again ");
                    continue;
                }

                if (!dropDisk(board, symbol, position)) {
                    System.out.println("This column is already full!");
                } else {
                    isValid = true;
                }

            } catch (Exception e) {
                input.nextLine();
                System.out.println("Must be an integer value ");
            }
        }
        printBoard(board);
        return checkWinner(board);
    }

    public boolean dropDisk(String[] board, String symbol, int position) {
        boolean isEmpty = board[position].equals("O");
        while (isEmpty && position + boardColumns < board.length) {
            int newPosition = position + boardColumns;
            isEmpty = board[newPosition].equals("O");

            if (isEmpty) {
                position = newPosition;
            }
        }

        if (!board[position].equals("O")) {
            return false;
        }

        board[position] = symbol;
        return true;
    }

    public boolean checkWinner(String[] board){
        // horizontal
        for (int row = 0; row < boardRows; row++) {
            for (int column = 0; column < boardColumns - 3; column++) {
                int cell = row * boardColumns + column;
                if (checkLine(board, cell, 1)) {
                    return true;
                }
            }
        }

        // vertical
        for (int row = 0; row < boardRows - 3; row++) {
            for (int column = 0; column < boardColumns; column++) {
                int cell = row * boardColumns + column;
                if (checkLine(board, cell, boardColumns)) {
                    return true;
                }
            }
        }

        // diagonal ascending
        for (int row = 0; row < boardRows - 3; row++) {
            for (int column = 3; column < boardColumns; column++) {
                int cell = row * boardColumns + column;
                if (checkLine(board, cell, boardColumns - 1)) {
                    return true;
                }
            }
        }

        // diagonal descending
        for (int row = 0; row < boardRows - 3; row++) {
            for (int column = 0; column < boardColumns - 3; column++) {
                int cell = row * boardColumns + column;
                if (checkLine(board, cell, boardColumns + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLine(String[] board, int cell, int change){
        String symbol = board[cell];
        if (symbol.equals("O")) {
            return false;
        }

        return checkLine(cell+change, change, symbol, 1);
    }

    public boolean checkLine(int cell, int change, String symbol, int count){
        if (!symbol.equals(board[cell])) {
            return false;
        }

        count++;
        if (count == 4) {
            return true;
        }
        return checkLine(cell+change, change, symbol, count);
    }

    public int max(String[] board, String symbol, int column, int movesCount) {
        String[] newBoard = board.clone();

        if (dropDisk(newBoard, symbol, column)) {
            // something went wrong here
            System.out.println("Check logic, AI stooopid");
            return -100;
        }
        if (checkWinner(newBoard)) {
            return 50 - movesCount;
        }

        int maxValue = -100;
        for (int i = 0; i < boardColumns; i++) {
            if (!newBoard[i].equals("O")) {
                continue;
            }

            int score = min(newBoard, symbol, i, movesCount + 1);
            if (score > maxValue) {
                maxValue = score;
            }
        }
        return maxValue;
    }

    public int min(String[] board, String symbol, int column, int movesCount) {
        String[] newBoard = board.clone();

        if (dropDisk(newBoard, symbol, column)) {
            // something went wrong here
            System.out.println("Check logic, AI stooopid");
            return -100;
        }
        if (checkWinner(newBoard)) {
            return -50 - movesCount;
        }

        int minValue = 100;
        for (int i = 0; i < boardColumns; i++) {
            if (!newBoard[i].equals("O")) {
                continue;
            }

            int score = max(newBoard, symbol, i, movesCount + 1);
            if (score < minValue) {
                minValue = score;
            }
        }
        return minValue;
    }
}
