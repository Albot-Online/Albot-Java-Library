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
        jsonCommand.addProperty(Fields.board, board.serialize());
        return gson.toJson(jsonCommand);
    }

    static String createCommandSimulateMove(GridBoard board, int player, int move) {
        JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(Fields.action, Actions.simMove);
        jsonCommand.addProperty(Fields.board, board.serialize());
        jsonCommand.addProperty(Fields.player, Integer.toString(player));
        jsonCommand.addProperty(Fields.move,move);
        return gson.toJson(jsonCommand);
    }

    static String createCommandEvaluate(GridBoard board) {
        JsonObject jsonCommand = new JsonObject();
        jsonCommand.addProperty(Fields.action, Actions.evalBoard);
        jsonCommand.addProperty(Fields.board, board.serialize());
        return gson.toJson(jsonCommand);
    }

    static GridBoard parseResponseState(String response, int width, int height) {
        JsonObject jResponse = gson.fromJson(response, JsonObject.class);
        String serializedGrid = jResponse.get(Fields.board).getAsString();
        return new GridBoard(width, height, serializedGrid);
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
