package albot.snake;

import albot.Constants;
import albot.JsonHandler;
import com.google.gson.*;
import static albot.snake.SnakeConstants.JProtocol;
import static albot.snake.SnakeBeans.*;

class SnakeJsonHandler {
    static Gson gson = new Gson();

    private static JsonObject serializeBoard(SnakeBoard board) {
        JsonObject jBoard = new JsonObject();
        jBoard.add(JProtocol.player, gson.toJsonTree(board.player));
        jBoard.add(JProtocol.enemy, gson.toJsonTree(board.enemy));
        jBoard.add(JProtocol.blocked, gson.toJsonTree(board.getBlockedList(false)).getAsJsonArray());

        return jBoard;
    }

    static String createCommandPossibleMoves(SnakeBoard board) {
        JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(Constants.Fields.action, Constants.Actions.getPossMoves);
        jsonCommand.addProperty(JProtocol.player, board.getPlayerDirection());
        jsonCommand.addProperty(JProtocol.enemy, board.getEnemyDirection());
        return gson.toJson(jsonCommand);
        //return jsonCommand.toString();
    }

    // Gson seems to ignore null values by default
    static String createCommandSimulate(SnakeBoard board, MovesToSimulate simMoves) {
        JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(Constants.Fields.action, Constants.Actions.simMove);
        if(simMoves.playerMove != null)
            jsonCommand.addProperty(JProtocol.playerMove, simMoves.playerMove);
        if(simMoves.enemyMove != null)
            jsonCommand.addProperty(JProtocol.enemyMove, simMoves.enemyMove);
        jsonCommand.add(JProtocol.board, serializeBoard(board));
        //System.out.println("Create command simulate:  \n" + jsonCommand.toString() + "\n");

        return gson.toJson(jsonCommand);
    }

    static String createCommandEvaluate(SnakeBoard board) {
        JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(Constants.Fields.action, Constants.Actions.evalBoard);
        jsonCommand.add(JProtocol.board, serializeBoard(board));

        return gson.toJson(jsonCommand);
    }

    static BoardBean parseResponseState(String response) {
        //System.out.println("Response state: \n" + response + "\n");
        //return gson.fromJson(response, BoardBean.class);
        return JsonHandler.tryParse(response, BoardBean.class);
    }

    static PossibleMoves parseResponsePossibleMoves(String response) {
        return gson.fromJson(response, PossibleMoves.class);
    }

    static SnakeBoard parseResponseSimulate(String response) {
        BoardBean boardBean = gson.fromJson(response, BoardBean.class);
        return new SnakeBoard(boardBean);
    }

    static Constants.BoardState parseResponseEvaluate(String response) {
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        String boardState = gson.fromJson(jsonObject.get(Constants.Fields.boardState), String.class);
        //System.out.println(boardState);
        return Constants.BoardState.valueOf(boardState);
    }
}
