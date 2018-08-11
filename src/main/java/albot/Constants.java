package albot;

public final class Constants {

    public static class Fields {
        public static final String board = "board";
        public static final String evaluate = "evaluate";
        public static final String possibleMoves = "possMoves";
        public static final String move = "move";
        public static final String player = "player";
        public static final String action = "action";
        public static final String winner = "winner";
        public static final String gameOver = "gameOver";//\n";
        public static final String boardState = "boardState";
    }

    public static class Actions {
        public static final String restartGame = "restartGame";
        public static final String makeMove = "makeMove";
        public static final String simMove = "simulateMove";
        public static final String evalBoard = "evaluateBoard";
        public static final String getPossMoves = "getPossibleMoves";
    }

    /**
     * Whether the game is over and if so, who the winner is.
     */
    public enum BoardState { PlayerWon, EnemyWon, Draw, Ongoing }

}
