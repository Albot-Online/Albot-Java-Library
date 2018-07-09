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
        /* Does not work well with Evaluate
        if(coordsInBounds(playerPlacement.x, playerPlacement.y))
            blocked[playerPlacement.x][playerPlacement.y] = true;
        if(coordsInBounds(enemyPlacement.x, enemyPlacement.y))
            blocked[enemyPlacement.x][enemyPlacement.y] = true;
        */

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

    /**
     * Checks if cell is occupied.
     * @param x x-coordinate for cell
     * @param y y-coordinate for cell
     * @return True if position is occupied, false if square is empty.
     */
    public boolean cellBlocked(int x, int y) {
        if (x < 0 || y < 0 || x >= Fields.BOARD_WIDTH || y >= Fields.BOARD_HEIGHT)
            return true; // Out of bounds
        if ((x == playerPlacement.x && y == playerPlacement.y) || (x == enemyPlacement.x && y == enemyPlacement.y))
            return true;
        return blocked[x][y];
    }

    public Position getPlayerPosition() { return new Position(playerPlacement.x, playerPlacement.y);}
    public Position getEnemyPosition() { return new Position(enemyPlacement.x, enemyPlacement.y);}
    public String getPlayerDirection() { return playerPlacement.dir; }
    public String getEnemyDirection() { return enemyPlacement.dir; }

    /**
     * The list of blocked cells.
     * @param includePlayerPositions if true, cells containing the head of the snakes are regarded as blocked.
     * @return Linked list with blocked cells
     */
    public List<Position> getBlockedList(boolean includePlayerPositions) {
        List<Position> b = new LinkedList<Position>();
        for (int xb = 0; xb < Fields.BOARD_WIDTH; xb++)
            for (int yb = 0; yb < Fields.BOARD_HEIGHT; yb++)
                if (blocked[xb][yb])
                    b.add(new Position(xb, yb));
        if(includePlayerPositions) {
            b.add(getPlayerPosition());
            b.add(getEnemyPosition());
        }
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
        for (int y = 0; y < Fields.BOARD_HEIGHT; y++) {
            for (int x = 0; x < Fields.BOARD_WIDTH; x++)
                s += squareInfo(x, y) + " ";
            s += "\n";
        }
        return s;
    }
    private String squareInfo(int x, int y) {
        if (x == playerPlacement.x && y == playerPlacement.y)
            return "P";
        if (x == enemyPlacement.x && y == enemyPlacement.y)
            return "E";
        return blocked[x][y] ? "1" : "0";
    }
}
