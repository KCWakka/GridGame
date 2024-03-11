import java.util.Scanner;

public class GridGame {
    private Space[][] board;
    private Player player;
    private Scanner scanner;

    public GridGame() {
        scanner = new Scanner(System.in);
        createPlayer();
        setupBoard();
        play();
    }

    private void createPlayer() {
        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();
        player = new Player(name);
    }

    // initialize the board instance variable to be a 8x8 board;
    // place new Space object with "_" as the symbol into each board position;
    // place the Player object at lower left corner;
    // initialize and place a Goal object with the symbol "X" in the upper right corner;
    // create several Treasure objects (up to you how many), with symbol of your choice,
    // each with a point value that you decide, and place them throughout the board
    private void setupBoard() {
        // WRITE THIS METHOD
        board = new Space[8][8];
        for (int row = 0; row < board.length; row++ ) {
            Boolean placeTreasure = true;
            for (int col = 0; col < board[row].length; col++) {
                int random2 = (int) (Math.random() * 8) + 1;
                if (row == 7 && col == 0) {
                    board[row][col] = player;
                } else {
                    if (row == 0 && col == 7) {
                        Goal goal = new Goal("X");
                        board[row][col] = goal;
                    } else {
                        if (placeTreasure && col != 0 && (col + 1) % random2 == 0) {
                            int point = (int) (Math.random() * 61) + 10;
                            Treasure treasure = new Treasure("M", point);
                            board[row][col] = treasure;
                            placeTreasure = false;
                        } else {
                            Space space = new Space("_");
                            board[row][col] = space;
                        }
                    }
                }
            }
        }
    }

    /* prints the 2D array board, showing the symbol for each Space, e.g.
       _______X
       __#_____
       _____#__
       _#______
       ________
       ______#_
       ________
       M___#___
     */
    private void printBoard() {
        // WRITE THIS METHOD
        for (Space[] row : board) {
            for (Space col : row) {
                System.out.print(col.getSymbol());
            }
            System.out.println();
        }
    }

    // plays the game;
    private void play() {
        // WRITE THIS METHOD
        // main game loop:
        // while the player has not yet reached the goal, print the board (complete can call helper method below)
        // then asks user to enter a direction: W, A, S, D (up, left, down, right).
        // if the intended direction is in bounds, move the Player to the new location and fill previous position
        // with a Space object (with "_" symbol).
        // if player moves to a position occupied by a Treasure, add its point value to the players score,
        // and replace that element with a Space object (with "_" symbol).
        // if the player reaches the goal, end the game and print their final score and the number of moves it took
        while (board[0][7] != player) {
            printBoard();
            System.out.print("Enter W, A, S, D: ");
            String choice = scanner.nextLine();
            if (processChoice(choice)) {
                int oldScore = player.getScore();
                if (choice.equals("d")) {
                    if (findPlayer()[1] + 1 < 8) {
                        Space space = new Space("_");
                        int row = findPlayer()[0];
                        int col = findPlayer()[1];
                        treasureScore(row, col + 1);
                        board[row][col] = space;
                        board[row][col + 1] = player;
                        player.move();
                        if (player.getScore() - oldScore != 0) {
                            System.out.println("You picked up a treasure valued at " + (player.getScore() - oldScore));
                        }
                    } else {
                        System.out.println("You will go out of bound!");
                    }
                }
                if (choice.equals("a")) {
                    if (findPlayer()[1] - 1 > -1) {
                        Space space = new Space("_");
                        int row = findPlayer()[0];
                        int col = findPlayer()[1];
                        treasureScore(row, col - 1);
                        board[row][col] = space;
                        board[row][col - 1] = player;
                        player.move();
                        if (player.getScore() - oldScore != 0) {
                            System.out.println("You picked up a treasure valued at " + (player.getScore() - oldScore));
                        }
                    } else {
                        System.out.println("You will go out of bound!");
                    }
                }
                if (choice.equals("w")) {
                    if (findPlayer()[0] - 1 > -1) {
                        Space space = new Space("_");
                        int row = findPlayer()[0];
                        int col = findPlayer()[1];
                        treasureScore(row - 1, col);
                        board[row][col] = space;
                        board[row - 1][col] = player;
                        player.move();
                        if (player.getScore() - oldScore != 0) {
                            System.out.println("You picked up a treasure valued at " + (player.getScore() - oldScore));
                        }
                    } else {
                        System.out.println("You will go out of bound!");
                    }
                }
                if (choice.equals("s")) {
                    if (findPlayer()[0] + 1 < 8) {
                        Space space = new Space("_");
                        int row = findPlayer()[0];
                        int col = findPlayer()[1];
                        treasureScore(row + 1, col);
                        board[row][col] = space;
                        board[row + 1][col] = player;
                        player.move();
                        if (player.getScore() - oldScore != 0) {
                            System.out.println("You picked up a treasure valued at " + (player.getScore() - oldScore));
                        }
                    } else {
                        System.out.println("You will go out of bound!");
                    }
                }
            } else {
                System.out.println("INVALID DIRECTION!");
            }
        }
        System.out.println("You win!");
        System.out.println("Number of moves: " + player.getMoves());
        System.out.println("Treasure points earned: " + player.getScore());
    }

    private boolean processChoice(String choice) {
        choice.toLowerCase();
        if (choice.equals("d") || choice.equals("w") || choice.equals("a") || choice.equals("s")) {
            return true;
        } else {
            return false;
        }
    }

    private int[] findPlayer() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] instanceof Player) {
                    int[] array = new int[2];
                    array[0] = row;
                    array[1] = col;
                    return array;
                }
            }
        }
        int[] temp = new int[1];
        return temp;
    }
    public void treasureScore(int row, int col) {
        if (board[row][col] instanceof Treasure) {
            player.addPoints(((Treasure) board[row][col]).getPointValue());
        }
    }
}