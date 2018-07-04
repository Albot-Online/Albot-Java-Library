package albot.snake;

import java.util.List;

// Not technically JavaBeans but whatever
public final class SnakeBeans {
    public static class Placement {

        public int x;
        public int y;
        public String dir;

        public Placement(int x, int y, String dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

    public static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /*
    public static class PossibleMoves {
        public List<String> player;
        public List<String> enemy;

        public PossibleMoves(List<String> player, List<String> enemy) {
            this.player = player;
            this.enemy = enemy;
        }
    }
    */

    public static class BoardBean {
        public Placement player, enemy;
        public List<Position> blocked;

        public BoardBean(Placement player, Placement enemy, List<Position> blocked) {
            this.player = player;
            this.enemy = enemy;
            this.blocked = blocked;
        }
    }

    public static class PossibleMoves {
        public List<String> playerMoves, enemyMoves;

        public PossibleMoves(List<String> playerMoves, List<String> enemyMoves) {
            this.playerMoves = playerMoves;
            this.enemyMoves = enemyMoves;
        }
    }

    public static class MovesToSimulate {
        public String playerMove, enemyMove;

        public MovesToSimulate(String playerMove, String enemyMove) {
            this.playerMove = playerMove;
            this.enemyMove = enemyMove;
        }
        public MovesToSimulate(String move, boolean player) {
            if(player)
                this.playerMove = move;
            else
                this.enemyMove = move;
        }
    }
}
