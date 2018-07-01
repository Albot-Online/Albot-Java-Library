package albot;

public final class Constants {

    public static class Fields {
        public static final String board = "board";
        public static final String evaluate = "Evaluate";
        public static final String possibleMoves = "PossMoves";
        public static final String move = "Move";
        public static final String player = "Player";
        public static final String action = "Action";
        public static final String winner = "Winner";
        public static final String gameOver = "GameOver";//\n";
        public static final String boardState = "boardState";
    }

    public static class Actions {
        public static final String restartGame = "RestartGame";
        public static final String makeMove = "MakeMove";
        public static final String simMove = "SimulateMove";
        public static final String evalBoard = "EvaluateBoard";
        public static final String getPossMoves = "GetPossibleMoves";
    }

    public enum BoardState { PlayerWon, EnemyWon, Draw, Ongoing }

}
