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
            dropDisk(playerName1, symbol1);
            if (checkWinner()) {
                System.out.println("Congratulations " + playerName1 + "! You win!");
                break;
            }
            dropDisk(playerName2, symbol2);
            if (checkWinner()) {
                System.out.println("Congratulations " + playerName2 + "! You win!");
                break;
            }
        }
    }
    public boolean dropDisk(String playerName, String symbol) {
        Scanner input = new Scanner(System.in);

        System.out.println(playerName + " pick a column");

        // TODO: ADD TRY CATCH
        int position = input.nextInt() - 1;

        if (position < 0 || position > boardColumns) {
            System.out.println("Must be a value from 1 to 7! Pick again ");
            return false;
        }

        boolean isEmpty = board[position].equals("O");
        if (!isEmpty) {
            System.out.println("This column is already full!");
            return false;
        }

        while (isEmpty && position + boardColumns < board.length) {
            int newPosition = position + boardColumns;
            isEmpty = board[newPosition].equals("O");

            if (isEmpty) {
                position = newPosition;
            }
        }

        board[position] = symbol;
        printBoard(board);
        return true;
    }

    public boolean checkWinner(){
        // horizontal
        for (int row = 0; row < boardRows; row++) {
            for (int column = 0; column < boardColumns - 3; column++) {
                int cell = row * boardColumns + column;
                if (checkLine(cell, 1)) {
                    return true;
                }
            }
        }

        // vertical
        for (int row = 0; row < boardRows - 3; row++) {
            for (int column = 0; column < boardColumns; column++) {
                int cell = row * boardColumns + column;
                if (checkLine(cell, boardColumns)) {
                    return true;
                }
            }
        }

        // diagonal ascending
        for (int row = 0; row < boardRows - 3; row++) {
            for (int column = 3; column < boardColumns; column++) {
                int cell = row * boardColumns + column;
                if (checkLine(cell, boardColumns - 1)) {
                    return true;
                }
            }
        }

        // diagonal descending
        for (int row = 0; row < boardRows - 3; row++) {
            for (int column = 0; column < boardColumns - 3; column++) {
                int cell = row * boardColumns + column;
                if (checkLine(cell, boardColumns + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLine(int start, int direction){
        String symbol = board[start];
        if (symbol.equals("O")) {
            return false;
        }

        for (int i = 1; i < 4; i++) {
            int change = direction * i;
            if (!board[start+change].equals(symbol)) {
                return false;
            }
        }
        return true;
    }
}
