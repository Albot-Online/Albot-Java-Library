package albot.snake;

import java.util.LinkedList;
import java.util.List;
import static albot.snake.SnakeConstants.Fields;
import static albot.snake.SnakeBeans.*;

public class SnakeBoard {
    SnakeBeans.Placement playerPlacement, enemyPlacement;
    private boolean[][] blocked = new boolean[Fields.BOARD_WIDTH][Fields.BOARD_HEIGHT];

    public SnakeBoard(SnakeBoard board, BoardBean boardBean) {
        extractOldBoardInfo(board);
        extractResponseInfo(boardBean);
    }
    public SnakeBoard(BoardBean boardBean) {
        extractResponseInfo(boardBean);
    }

    private void extractResponseInfo(BoardBean response) {
        playerPlacement = response.player;
        enemyPlacement = response.enemy;
        if(coordsInBounds(playerPlacement.x, playerPlacement.y))
            blocked[playerPlacement.x][playerPlacement.y] = true;
        if(coordsInBounds(enemyPlacement.x, enemyPlacement.y))
            blocked[enemyPlacement.x][enemyPlacement.y] = true;

        for (Position pos : response.blocked) {
            if(coordsInBounds(pos.x, pos.y))
                blocked[pos.x][pos.y] = true;
        }
    }

    private void extractOldBoardInfo(SnakeBoard board) {
        if (board != null)
            for (int x = 0; x < Fields.BOARD_WIDTH; x++)
                for (int y = 0; y < Fields.BOARD_HEIGHT; y++)
                    blocked[x][y] = board.blocked[x][y];
    }

    public boolean cellBlocked(int x, int y) {
        if (x < 0 || y < 0 || x >= Fields.BOARD_WIDTH || y >= Fields.BOARD_HEIGHT)
            return true; // Out of bounds
        return blocked[x][y];
    }

    public Position getPlayerPosition() { return new Position(playerPlacement.x, playerPlacement.y);}
    public Position getEnemyPosition() { return new Position(enemyPlacement.x, enemyPlacement.y);}
    public String getPlayerDirection() { return playerPlacement.dir; }
    public String getEnemyDirection() { return enemyPlacement.dir; }

    public List<Position> getBlockedList() {
        List<Position> b = new LinkedList<Position>();
        for (int xb = 0; xb < Fields.BOARD_WIDTH; xb++)
            for (int yb = 0; yb < Fields.BOARD_HEIGHT; yb++)
                if (blocked[xb][yb])
        b.add(new Position(xb, yb));
        return b;
    }

    private boolean coordsInBounds(int x, int y) {
        if (x < 0 || y < 0 || x >= Fields.BOARD_WIDTH || y >= Fields.BOARD_HEIGHT)
            return false;
        return true;
    }

    // Debug, not serialize
    @Override
    public String toString() {
        String s = "";
        for (int x = 0; x < Fields.BOARD_WIDTH; x++) {
            for (int y = 0; y < Fields.BOARD_HEIGHT; y++)
                s += (blocked[y][x] ? "1" : "0") + " "; // Transposed
            s += "\n";
        }
        return s;
    }
}
