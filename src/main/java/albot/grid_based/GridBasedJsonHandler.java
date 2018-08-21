package albot.grid_based;

import static albot.Constants.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

class GridBasedJsonHandler {
    static Gson gson = new Gson();

    static String createCommandPossibleMoves(GridBoard board) {
        JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(Fields.action, Actions.getPossMoves);
        //jsonCommand.addProperty(Fields.board, board.serialize());
        jsonCommand.add(Fields.board, gson.toJsonTree(GridBoard.transpose(board.grid, board.WIDTH, board.HEIGHT)).getAsJsonArray());

        return gson.toJson(jsonCommand);
    }

    static String createCommandSimulateMove(GridBoard board, int player, int move) {
        JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(Fields.action, Actions.simMove);
        jsonCommand.add(Fields.board, gson.toJsonTree(GridBoard.transpose(board.grid, board.WIDTH, board.HEIGHT)).getAsJsonArray());
        jsonCommand.addProperty(Fields.player, player);
        jsonCommand.addProperty(Fields.move,move);
        //System.out.println("Sim command: \n" + gson.toJson(jsonCommand));
        return gson.toJson(jsonCommand);
    }

    static String createCommandEvaluate(GridBoard board) {
        JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(Fields.action, Actions.evalBoard);
        jsonCommand.add(Fields.board, gson.toJsonTree(GridBoard.transpose(board.grid, board.WIDTH, board.HEIGHT)).getAsJsonArray());
        return gson.toJson(jsonCommand);
    }

    static GridBoard parseResponseState(String response, int width, int height) {
        JsonObject jResponse = gson.fromJson(response, JsonObject.class);
        JsonElement jBoard = jResponse.get(Fields.board); // Get value
        Type type2dIntArr = new TypeToken<int[][]>() {}.getType(); // Required cause of generic List
        //String serializedGrid = jResponse.get(Fields.board).getAsString();
        int[][] grid = gson.fromJson(jBoard, type2dIntArr);
        grid = GridBoard.transpose(grid, height, width);
        return new GridBoard(width, height, grid);
    }

    static List<Integer> parseResponsePossibleMoves(String response) {
        JsonObject jResponse = gson.fromJson(response, JsonObject.class); // Parse
        JsonElement jPossMoves = jResponse.get(Fields.possibleMoves); // Get value
        Type typeIntegerList = new TypeToken<List<Integer>>() {}.getType(); // Required cause of generic List
        return gson.fromJson(jPossMoves, typeIntegerList);
    }

    static BoardState parseResponseEvaluate(String response) {
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        String boardState = gson.fromJson(jsonObject.get(Fields.boardState), String.class);
        return BoardState.valueOf(boardState);
    }
}
