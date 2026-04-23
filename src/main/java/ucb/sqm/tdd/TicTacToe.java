package ucb.sqm.tdd;

/**
 * TicTacToe game implementation based on TDD kata requirements.
 * Requirement 1: A piece can be placed on a 3x3 board space.
 * Requirement 2: There must be a way to determine which player plays next.
 * Requirement 3: A player wins if they are the first to connect a line from one
 * side to another.
 */
public class TicTacToe {

    // Player representations and empty symbol
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private static final char EMPTY = '\0';

    // current turn: true = X, false = O
    private boolean turn;

    // game board
    private char[][] board;

    // last player who played
    private char lastPlayer;

    // winner of the game
    private char winner;

    /**
     * Constructor: initializes the board and sets X as the first player.
     */
    public TicTacToe() {
        this.turn = true;
        this.board = new char[3][3];
        this.lastPlayer = EMPTY;
        this.winner = EMPTY;
        initializeBoard();
    }

    /**
     * Initializes the board with the EMPTY symbol.
     */
    private void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    /**
     * Places a piece on the board at the given position.
     * Throws RuntimeException if the position is out of bounds or already occupied.
     *
     * @param x column (0-2)
     * @param y row (0-2)
     */
    public void placePiece(int x, int y) {
        // Requirement 1: validate X axis
        if (x < 0 || x > 2) {
            throw new RuntimeException("X axis out of bounds: " + x);
        }
        // Requirement 1: validate Y axis
        if (y < 0 || y > 2) {
            throw new RuntimeException("Y axis out of bounds: " + y);
        }
        // Requirement 1: validate position is not already occupied
        if (board[y][x] != EMPTY) {
            throw new RuntimeException("Position (" + x + ", " + y + ") is already occupied");
        }

        // Place the piece
        char currentPlayer = turn ? PLAYER_X : PLAYER_O;
        board[y][x] = currentPlayer;
        lastPlayer = currentPlayer;

        // Check if current player wins
        if (checkWinner(currentPlayer)) {
            winner = currentPlayer;
        }

        // Switch turn
        turn = !turn;
    }

    /**
     * Returns the player whose turn it is next.
     * Requirement 2: X always plays first; turns alternate.
     *
     * @return 'X' or 'O'
     */
    public char nextPlayer() {
        return turn ? PLAYER_X : PLAYER_O;
    }

    /**
     * Returns the last player who played.
     *
     * @return 'X', 'O', or EMPTY if no one has played yet
     */
    public char getLastPlayer() {
        return lastPlayer;
    }

    /**
     * Returns the winner of the game, or EMPTY if there is no winner yet.
     * Requirement 3: A player wins when they fill a full horizontal, vertical, or
     * diagonal line.
     *
     * @return 'X', 'O', or EMPTY
     */
    public char getWinner() {
        return winner;
    }

    /**
     * Returns the value at a board position.
     *
     * @param x column
     * @param y row
     * @return char at that position
     */
    public char getBoardValue(int x, int y) {
        return board[y][x];
    }

    /**
     * Checks if the given player has won the game.
     *
     * @param player the player symbol to check
     * @return true if the player has won
     */
    private boolean checkWinner(char player) {
        return checkRows(player) || checkColumns(player) || checkDiagonals(player);
    }

    /**
     * Checks all horizontal rows for a winning condition.
     *
     * @param player the player symbol to check
     * @return true if any row is fully occupied by the player
     */
    private boolean checkRows(char player) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == player && board[row][1] == player && board[row][2] == player) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks all vertical columns for a winning condition.
     *
     * @param player the player symbol to check
     * @return true if any column is fully occupied by the player
     */
    private boolean checkColumns(char player) {
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == player && board[1][col] == player && board[2][col] == player) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks both diagonals for a winning condition.
     *
     * @param player the player symbol to check
     * @return true if any diagonal is fully occupied by the player
     */
    private boolean checkDiagonals(char player) {
        // Main diagonal: top-left to bottom-right
        boolean mainDiagonal = (board[0][0] == player && board[1][1] == player && board[2][2] == player);
        // Anti diagonal: top-right to bottom-left
        boolean antiDiagonal = (board[0][2] == player && board[1][1] == player && board[2][0] == player);
        return mainDiagonal || antiDiagonal;
    }

    /**
     * Checks whether the board is full (draw condition).
     *
     * @return true if no empty spaces remain
     */
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether the game is over (winner found or board full).
     *
     * @return true if the game has ended
     */
    public boolean isGameOver() {
        return winner != EMPTY || isBoardFull();
    }
}