package albot.snake;

public final class SnakeConstants {
    public static class Fields {
        public static final int BOARD_WIDTH = 10;
        public static final int BOARD_HEIGHT = 10;

        public static final String RIGHT = "right";
        public static final String UP = "up";
        public static final String LEFT = "left";
        public static final String DOWN = "down";
    }

    public static class JProtocol {
        public static final String board = "board";
        public static final String posX = "x";
        public static final String posY = "y";
        public static final String dir = "dir";
        public static final String player = "player";
        public static final String enemy = "enemy";
        public static final String blocked = "blocked";

        public static final String playerMove = "playerMove";
        public static final String enemyMove = "enemyMove";
        public static final String playerMoves = "playerMoves";
        public static final String enemyMoves = "enemyMoves";

        public static final String simMoveDelta = "simulateMoveDelta";
        public static final String simPlayerMove = "simulatePlayerMove";
        public static final String simEnemyMove = "simulateEnemyMove";

    }
}
