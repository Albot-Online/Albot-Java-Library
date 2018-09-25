package albot.snake;

import java.util.List;
import java.util.Objects;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Placement)) {
                return false;
            }
            Placement placement = (Placement) o;
            return x == placement.x &&
                    y == placement.y &&
                    Objects.equals(dir, placement.dir);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, dir);
        }

        @Override
        public String toString() {
            return "{'x': " + x + ", 'y': " + y + ", 'dir': " + dir + "}";
        }
    }

    public static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "{" + "'x': " + x + ", 'y': " + y + '}';
        }
    }

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
