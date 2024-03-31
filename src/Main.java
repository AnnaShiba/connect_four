import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int boardColumns = 7;
        final int boardRows = 6;
        String[] board = new String[boardColumns * boardRows];
        Arrays.fill(board, "O");

        Scanner input = new Scanner(System.in);
        System.out.println("Hello gamer! Please, enter player 1 name: ");
        String playerName1 = input.nextLine();
        System.out.println(playerName1 + ", choose symbol R (for Red) or Y (for Yellow): ");
        String symbol1 = input.nextLine();

        System.out.println("Please, enter player 2 name: ");
        String playerName2 = input.nextLine();
        String symbol2;
        if (symbol1.equals("R")) {
            symbol2 = "Y";
        } else {
            symbol2 = "R";
        }
        System.out.println(playerName2 + " your symbol is " + symbol2);

        printBoard(board);

        System.out.println(playerName1 + " pick a column");
        int move = input.nextInt() - 1;

        if (move < 0 || move > boardColumns) {
            System.out.println("Must be a value from 1 to 7! Pick again ");
        }

        boolean isEmpty = board[move].equals("O");
        while (isEmpty) {
            move = move + boardColumns;
            if (move >= board.length) {
                move = move - boardColumns;
                break;
            }
            isEmpty = board[move].equals("O");
        }

        board[move] = symbol1;
        printBoard(board);
    }

    static public void printBoard(String[] board) {
        System.out.println("Game board:");
        for (int i = 0; i < board.length; i++) {
            System.out.print(" " + board[i] + " ");
            if (i % 7 == 6) {
                System.out.println("");
            }
        }
    }
}