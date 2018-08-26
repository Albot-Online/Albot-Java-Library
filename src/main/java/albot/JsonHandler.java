package albot;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class JsonHandler {
    static Gson gson;

    public static <T> T tryParse(String response, Type type) {
        try {
            return gson.fromJson(response, type);
        } catch (Exception e) {
            System.out.println("Could not parse response: \n" + response + "\n" + e);
            AlbotConnection.terminate();
        }
        return null; // Unreachable
    }

}
